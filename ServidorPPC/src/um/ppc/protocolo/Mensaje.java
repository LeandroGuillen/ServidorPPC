package um.ppc.protocolo;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

import com.google.gson.Gson;

public class Mensaje {
	private Integer id;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;

	public Mensaje() {

	}

	public String toJSON() {
		return new Gson().toJson(this);
	}

	public String toXML() throws UnsupportedEncodingException, FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		StringWriter stringbuffer = new StringWriter();
		XMLStreamWriter xml = XMLOutputFactory.newInstance().createXMLStreamWriter(stringbuffer);

		// Inicia elemento mensaje con su atributo "id"
		xml.writeStartDocument();
		xml.writeStartElement("mensaje");
		xml.writeAttribute("id", getId().toString());

		// Sub-elemento "tipo" con su contenido
		xml.writeStartElement("tipo");
		xml.writeCharacters(getTipo().toString());
		xml.writeEndElement();

		// Sub-elemento "codificacion" con su contenido
		xml.writeStartElement("codificacion");
		xml.writeCharacters(getCodificacion().toString());
		xml.writeEndElement();

		// Escribe el contenido y cierra el mensaje
		xml.writeCharacters(getContenido());
		xml.writeEndElement();
		xml.writeEndDocument();

		xml.close();

		return stringbuffer.toString();
	}

	public byte[] toASN1() {
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoObjetoCriptografico getTipo() {
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
