package um.ppc.protocolo;

import org.bouncycastle.asn1.ASN1Primitive;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

public class Mensaje {
	private TipoMensaje tipoMensaje;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;
	private ASN1Primitive obj;

	public Mensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
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

}
