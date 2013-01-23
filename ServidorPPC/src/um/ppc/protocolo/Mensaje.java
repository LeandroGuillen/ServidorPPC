package um.ppc.protocolo;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

public class Mensaje extends ASN1Object {
	private TipoMensaje tipoMensaje;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;

	public Mensaje(TipoMensaje tipoMensaje) {
		setTipoMensaje(tipoMensaje);
	}

	public Mensaje(ASN1Sequence seq) throws IOException {
		int index = 0;

		// Campo fijo "tipoMensaje"
		DERInteger di = (DERInteger) seq.getObjectAt(index);
		TipoMensaje tm = TipoMensaje.values()[di.getValue().intValue()];
		setTipoMensaje(tm);
		index++;

		// Campo "tipoObjetoCriptografico"
		di = (DERInteger) seq.getObjectAt(index);
		int valor = di.getValue().intValue();
		if (valor >= 0 && valor < TipoObjetoCriptografico.values().length) {
			TipoObjetoCriptografico toc = TipoObjetoCriptografico.values()[valor];
			setTipo(toc);
		}
		index++;

		// Campo "codificacion"
		di = (DERInteger) seq.getObjectAt(index);
		valor = di.getValue().intValue();
		if (valor >= 0 && valor < Codificacion.values().length) {
			Codificacion cod = Codificacion.values()[valor];
			setCodificacion(cod);
		}
		index++;

		// Campo "contenido" opcional
		if (index != seq.size()) {
			String cont = ((DERUTF8String) seq.getObjectAt(index)).getString();
			setContenido(cont);
		}
	}

	public TipoMensaje getTipoMensaje() {
		return tipoMensaje;
	}

	public TipoObjetoCriptografico getTipoObjCriptografico() {
		return tipo;
	}

	public void setTipo(TipoObjetoCriptografico tipo) {
		this.tipo = tipo;
	}

	private void setTipoMensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Codificacion getCodificacion() {
		return codificacion;
	}

	public void setCodificacion(Codificacion codificacion) {
		this.codificacion = codificacion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public ASN1Primitive toASN1Primitive() {
		ASN1EncodableVector v = new ASN1EncodableVector();

		DERInteger tm, toc, codif;
		DERUTF8String cont;

		tm = new DERInteger(this.tipoMensaje.ordinal());
		v.add(tm);

		if (tipo != null)
			toc = new DERInteger(this.tipo.ordinal());
		else
			toc = new DERInteger(100);

		if (codificacion != null)
			codif = new DERInteger(codificacion.ordinal());
		else
			codif = new DERInteger(100);

		v.add(toc);
		v.add(codif);

		// Unico parametro opcional
		if (contenido != null) {
			cont = new DERUTF8String(contenido);
			v.add(cont);
		}

		return new DERSequence(v);
	}

	@Override
	public String toString() {
		String t = "Tipo: ";
		t += tipoMensaje.toString();
		if (tipo != null)
			t += ", TObjCrip:" + tipo.toString();
		if (codificacion != null)
			t += ", COD:" + codificacion.toString();
		if (contenido != null)
			t += ", Contenido: " + contenido;
		return t;
	}

}
