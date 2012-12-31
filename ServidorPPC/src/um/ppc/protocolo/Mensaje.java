package um.ppc.protocolo;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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

	public String toXML2() throws UnsupportedEncodingException, FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		StringWriter stringbuffer = new StringWriter();
		XMLStreamWriter xml = XMLOutputFactory.newInstance().createXMLStreamWriter(stringbuffer);

		// Inicia elemento mensaje con su atributo "id", siempre estara presente en el mensaje
		xml.writeStartDocument();
		xml.writeStartElement("mensaje");
		xml.writeAttribute("id", getTipoMensaje().toString());

		// Sub-elemento "tipo" con su contenido
		if(getTipoObjCriptografico()!=null){
			xml.writeStartElement("tipo");
			xml.writeCharacters(getTipoObjCriptografico().toString());
			xml.writeEndElement();
		}

		// Sub-elemento "codificacion" con su contenido
		if(getCodificacion()!=null){
			xml.writeStartElement("codificacion");
			xml.writeCharacters(getCodificacion().toString());
			xml.writeEndElement();
		}

		// Escribe el contenido
		if(getContenido()!=null){
			xml.writeCharacters(getContenido());
		}
		
		// Cierra el mensaje
		xml.writeEndElement();
		xml.writeEndDocument();
		xml.close();

		return stringbuffer.toString();
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
