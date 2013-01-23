package um.ppc.protocolo.test;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

/**
 * ProgramacionParaLasComunicaciones DEFINITIONS AUTOMATIC TAGS ::= BEGIN
 * Mensaje ::= SEQUENCE { id INTEGER, tipo TipoObjetoCriptografico, codif
 * Codificacion, contenido UTF8String } TipoObjetoCriptografico ::= ENUMERATED
 * {PKCS1, PKCS7} Codificacion ::= ENUMERATED {ASN1, XML, JSON} END
 */

public class MensajeASN1 implements ASN1Encodable {
	private DERUTF8String idOperacion;
	private DERUTF8String tipo; // TipoObjetoCriptografico
	private DERUTF8String codificacion; // Codificacion
	private DERUTF8String contenido;

	public MensajeASN1(TipoMensaje id, Codificacion codificacion) {
		this(id, null, codificacion, null);
	}

	public MensajeASN1(TipoMensaje id, TipoObjetoCriptografico tipo) {
		this(id, tipo, null, null);
	}

	public MensajeASN1(TipoMensaje id, String contenido) {
		this(id, null, null, contenido);
	}

	public MensajeASN1(TipoMensaje id, TipoObjetoCriptografico tipo, Codificacion codificacion, String contenido) {
		this.idOperacion = new DERUTF8String(id.toString());
		if (tipo != null)
			this.tipo = new DERUTF8String(tipo.toString());
		if (codificacion != null)
			this.codificacion = new DERUTF8String(codificacion.toString());
		if (contenido != null)
			this.contenido = new DERUTF8String(contenido);
	}

	@Override
	public ASN1Primitive toASN1Primitive() {
		ASN1EncodableVector v = new ASN1EncodableVector();

		v.add(idOperacion);

		if (tipo != null)
			v.add(new DERTaggedObject(false, 0, tipo));
		if (codificacion != null)
			v.add(new DERTaggedObject(false, 1, codificacion));
		if (contenido != null)
			v.add(new DERTaggedObject(false, 2, contenido));
		return new DERSequence(v);
	}

}
