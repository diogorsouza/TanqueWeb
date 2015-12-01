#include <EtherCard.h>
#include "Ultrasonic.h"
const double l=216;  //L = altura do cilindro
const double r=103; //r = raio do cilindro
const double p=3.145;
double volume=0;    
double h=0,auxiliar,altu; //h = altura do líquido
///#include <LiquidCrystal.h>
  Ultrasonic ultrasonic(7,6);
//   LiquidCrystal lcd(9, 7, 6, 5, 4, 3);

// ethernet interface mac address, must be unique on the LAN
#define STATIC 0  // set to 1 to disable DHCP (adjust myip/gwip values below)

#if STATIC
// ethernet interface ip address
static byte myip[] = { 192,168,1,203 };
// gateway ip address
static byte gwip[] = { 192,168,1,1 };
#endif

// ethernet mac address - must be unique on your network
static byte mymac[] = { 0x74,0x69,0x69,0x2D,0x30,0x31 };

byte Ethernet::buffer[500]; // tcp/ip send and receive buffer

BufferFiller bfill;
 
void setup () {
  //lcd.begin(16, 2);
  // Print a message to the LCD.
  //lcd.print("Tanque Produto:");
  Serial.begin(57600);
  if (ether.begin(sizeof Ethernet::buffer, mymac,8) == 0) 
    Serial.println( "Failed to access Ethernet controller");
#if STATIC
  ether.staticSetup(myip, gwip);
#else
  if (!ether.dhcpSetup())
    Serial.println("DHCP failed");
#endif

  ether.printIp("IP:  ", ether.myip);
  ether.printIp("GW:  ", ether.gwip);  
  ether.printIp("DNS: ", ether.dnsip);  
  }
    
static word homePage(double alt) {

double volts = alt;
char tmp[25] = "Pot Voltage = ";
dtostrf(volts,1,2, tmp);

  bfill = ether.tcpOffset();
  bfill.emit_p(PSTR(
    "HTTP/1.0 200 OK\r\n"
    "Content-Type: text/html\r\n"
    "Pragma: no-cache\r\n"
    "\r\n"
    "<meta http-equiv='refresh' content='1'/>"
    "<title>CALCULO TANQUE</title>"
    "<h1>Volume Tanque</h1>"
    "<h1>$S</h1>"),
    tmp);
    return bfill.position();
}

static double altura(){
   auxiliar=0;
   for (int i=0;i<100;i++){
      auxiliar+=ultrasonic.Ranging(CM);
    delay(200);
     checkTCP();
    }
    auxiliar=auxiliar/99;

     h=l-(auxiliar-30);
     volume=((pow(r,2))*3.14)*h;
     volume=volume/1000;
    Serial.print("Altura:  ");
    Serial.println(auxiliar);
    Serial.print("Volume:  ");
    Serial.println(volume);
    return volume;
}
void checkTCP(){
if (ether.packetLoop(ether.packetReceive())){  // check if valid tcp data is received
    ether.httpServerReply(homePage(altu)); // send web page data
  }
}

void loop () {
   altu=altura();
   //lcd.setCursor(0, 1);
   //lcd.print(altu);
   //lcd.print("L");
  checkTCP();
}


