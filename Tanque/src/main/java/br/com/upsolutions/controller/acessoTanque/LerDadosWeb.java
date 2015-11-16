package br.com.upsolutions.controller.acessoTanque;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class LerDadosWeb {

	public double LerDadosWeb() {

		final StringBuffer buf = new StringBuffer(1000);
		try {
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
			URL url = new URI("file:///D://Luiz%20H//TanqueWeb2//TanqueWeb//src//main//webapp//calculo.html").toURL();
			URLConnection conn = url.openConnection();
			Reader rd = new InputStreamReader(conn.getInputStream());
			EditorKit kit = new HTMLEditorKit();
			kit.read(rd, doc, 0);
		} catch (MalformedURLException e) {
		} catch (URISyntaxException e) {
		} catch (BadLocationException e) {
		} catch (IOException e) {
		}
		// Retorna todo o texto encontrado
		String a = "";
		for (int i = 38; i < (buf.length()); i++) {
			a += buf.charAt(i);
		}
		System.out.println(a);
		double b = Double.parseDouble(a);
		;
		return b;
	}

}