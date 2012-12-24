package um.ppc.protocolo;

import org.bouncycastle.asn1.util.ASN1Dump;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

/**
 * Example for ASN1Dump using MyStructure.
 */
public class ASN1DumpExample {
	public static void main(String[] args) throws Exception {
		// byte[] baseData = new byte[5];
		// Date created = new Date(0); // 1/1/1970
		// MyStructure structure = new MyStructure(0, created, baseData, "hello", "world");
		// System.out.println(ASN1Dump.dumpAsString(structure));
		// structure = new MyStructure(1, created, baseData, "hello", "world");
		// System.out.println(ASN1Dump.dumpAsString(structure));

		MensajeASN1 m1 = new MensajeASN1(TipoMensaje.CLIENTHELLO, Codificacion.ASN1) ;
		MensajeASN1 m2 = new MensajeASN1(TipoMensaje.SERVERHELLO, Codificacion.ASN1);
		MensajeASN1 m3 = new MensajeASN1(TipoMensaje.PEDIROBJETO, TipoObjetoCriptografico.PKCS1);
		MensajeASN1 m4 = new MensajeASN1(TipoMensaje.DAROBJETO, "hola hamigo");
		
		System.out.println(ASN1Dump.dumpAsString(m1));
		System.out.println(ASN1Dump.dumpAsString(m2));
		System.out.println(ASN1Dump.dumpAsString(m3));
		System.out.println(ASN1Dump.dumpAsString(m4));
		

		// ASN1InputStream aIn = new ASN1InputStream(m.getEncoded());
		// System.out.println(ASN1Dump.dumpAsString(aIn.readObject()));
	}
}
