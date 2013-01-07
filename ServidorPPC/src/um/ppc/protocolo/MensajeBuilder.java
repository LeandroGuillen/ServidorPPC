package um.ppc.protocolo;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

public class MensajeBuilder {

	public static Mensaje desdeXML(String xmlData) {
		Mensaje mensaje = null;

		// Transformar datos en XML a Mensaje
		XStream xstream = new XStream();
		xstream.alias("mensaje", Mensaje.class);
		try {
			mensaje = (Mensaje) xstream.fromXML(xmlData);
		} catch (XStreamException e) {
			System.out.println("El mensaje no esta en formato XML valido");
		}

		return mensaje;
	}

	public static Mensaje desdeJSON(String jsonData) {
		try {
			return new Gson().fromJson(jsonData, Mensaje.class);
		} catch (JsonSyntaxException e) {
			System.out.println("El mensaje no esta en formato JSON valido");
			return null;
		}
	}

	public static Mensaje desdeASN1(String respuesta) {
		return null;
	}
}
