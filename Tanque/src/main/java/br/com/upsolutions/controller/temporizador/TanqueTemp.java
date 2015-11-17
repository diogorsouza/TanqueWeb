package br.com.upsolutions.controller.temporizador;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import br.com.upsolutions.business.dao.CalculoTanqueDao;
import br.com.upsolutions.business.entity.CalculoTanque;

@Singleton
@Lock // Use (LockType.READ) allows timers to execute in parallel
@Startup
public class TanqueTemp implements Serializable {

	@Resource
	private TimerService timerService;
	@EJB
	private CalculoTanqueDao calculoTanqueDao;

	@PostConstruct
	public void construct() {
		System.out.println("construct");
		stopTimers();
		timerService.createTimer(6000, 6000, "TempoDeLeitura");
	}

	@Timeout
	public void timeout(Timer timer) throws Exception {
		System.out.println("timeout");
		try {
			if (timer.getInfo().equals("TempoDeLeitura")) {
				final StringBuffer buf = new StringBuffer(1000);
				HTMLDocument doc = new HTMLDocument() {
					public HTMLEditorKit.ParserCallback getReader(int pos) {
						return new HTMLEditorKit.ParserCallback() {
							public void handleText(char[] data, int pos) {
								buf.append(data);
								buf.append('\n');
							}
						};
					}
				};
				URL url = new URI("http://localhost:6060/Tanque/calculo.html").toURL();
				URLConnection conn = url.openConnection();
				Reader rd = new InputStreamReader(conn.getInputStream());
				EditorKit kit = new HTMLEditorKit();
				kit.read(rd, doc, 0);
				// Retorna todo o texto encontrado
				String a = "";
				for (int i = 38; i <(buf.length()); i++) {
					a+=buf.charAt(i);
					System.out.println(i + " -- " + a);
				}

				double b = Double.parseDouble(a);

				CalculoTanque calculo = new CalculoTanque();
				calculo.setCliente("TRATASUL");
				calculo.setVolume(b);
				calculo.setData(new Date());
				calculoTanqueDao.insertBean(calculo);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopTimers() {
		if (timerService.getTimers() != null)
			for (Object obj : timerService.getTimers())
				((Timer) obj).cancel();
	}
}