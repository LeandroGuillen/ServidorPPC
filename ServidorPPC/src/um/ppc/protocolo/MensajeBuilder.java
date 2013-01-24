package um.ppc.protocolo;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;

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

	public static String toXML(Mensaje m) {
		XStream xstream = new XStream();
		xstream.alias("mensaje", Mensaje.class);
		return xstream.toXML(m);
	}

	public static Mensaje desdeJSON(String jsonData) {
		try {
			return new Gson().fromJson(jsonData, Mensaje.class);
		} catch (JsonSyntaxException e) {
			System.out.println("El mensaje no esta en formato JSON valido");
			return null;
		}
	}

	public static String toJSON(Mensaje m) {
		return new Gson().toJson(m);
	}

	public static Mensaje desdeASN1(byte[] respuesta) throws IOException {
		byte[] respuestaLimpia = limpiarBytes(respuesta);
		ASN1InputStream ais = new ASN1InputStream(respuestaLimpia);
		// TODO Aqui casca el ASN1
		ASN1Sequence secuencia = (ASN1Sequence) ais.readObject();
		ais.close();
		return new Mensaje(secuencia);
	}

	public static byte[] toASN1(Mensaje mensaje) throws IOException {
		return mensaje.getEncoded("DER");
	}

	private static byte[] limpiarBytes(byte[] b) {

		int j = 0;
		while (b[b.length - 1 - j] == 0 && j < 10) {
			j++;
		}

		if (j == 10) {
			j = b.length - 1 - j;
			while (b[j] == 0) {
				j--;
			}

			byte[] salida = new byte[j + 1];
			for (int i = 0; i < j + 1; i++)
				salida[i] = b[i];
			return salida;
		} else
			return b;
	}

}
