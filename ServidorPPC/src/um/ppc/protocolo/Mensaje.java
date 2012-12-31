package um.ppc.protocolo;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Mensaje {
	private TipoMensaje tipoMensaje;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;
	
	public Mensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String toJSON() {
		return new Gson().toJson(this);
	}
	
	public String toXML(){
		XStream xstream=new XStream();
		xstream.alias("mensaje", Mensaje.class);
		return xstream.toXML(this);
	}

	public byte[] toASN1() {
		return null;
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
