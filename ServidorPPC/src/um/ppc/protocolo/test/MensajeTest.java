package um.ppc.protocolo.test;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.util.ASN1Dump;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

public class MensajeTest {
	public static void main(String[] args) throws Exception {

		Mensaje clientHello = new Mensaje(TipoMensaje.CLIENTHELLO);
		clientHello.setCodificacion(Codificacion.ASN1);

		Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
		serverHello.setCodificacion(Codificacion.ASN1);

		Mensaje pedirObj = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObj.setTipo(TipoObjetoCriptografico.PKCS1);

		Mensaje darObj = new Mensaje(TipoMensaje.DAROBJETO);
		darObj.setContenido("objetoCriptografico");

		// System.out.println(ASN1Dump.dumpAsString(clientHello));
		// System.out.println(ASN1Dump.dumpAsString(serverHello));
		// System.out.println(ASN1Dump.dumpAsString(pedirObj));
		// System.out.println(ASN1Dump.dumpAsString(darObj));

//		ASN1InputStream aIn = new ASN1InputStream(clientHello.getEncoded());
//		System.out.println(ASN1Dump.dumpAsString(aIn.readObject()));

		byte[] bb = MensajeBuilder.toASN1(clientHello);
		Mensaje m = MensajeBuilder.desdeASN1(bb);
		System.out.println(ASN1Dump.dumpAsString(m));

	}
}
