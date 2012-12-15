package um.ppc.protocolo;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERInteger;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

import com.google.gson.Gson;


/**
 * ProgramacionParaLasComunicaciones DEFINITIONS AUTOMATIC TAGS ::=
 BEGIN
 Mensaje ::= SEQUENCE {
 id			INTEGER,
 tipo		TipoObjetoCriptografico,
 codif		Codificacion,
 contenido	UTF8String
 }
 TipoObjetoCriptografico ::= ENUMERATED {PKCS1, PKCS7}
 Codificacion ::= ENUMERATED {ASN1, XML, JSON}
 END
 */

public class MensajeASN1 implements ASN1Encodable {
	private DERInteger id;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;

	public MensajeASN1() {

	}

	@Override
	public ASN1Primitive toASN1Primitive() {
		// TODO Auto-generated method stub
		return null;
	}

}
