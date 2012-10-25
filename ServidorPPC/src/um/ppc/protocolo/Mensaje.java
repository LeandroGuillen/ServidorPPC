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

	/**
	 * Devuelve el mensaje formateado en JSON utilizando la libreria Gson.
	 * 
	 * @return Una cadena que representa el mensaje en formato JSON.
	 */
	public String toJSON() {
		return new Gson().toJson(this);
	}

	public String toXML() throws UnsupportedEncodingException,
			FileNotFoundException, XMLStreamException,
			FactoryConfigurationError {
		StringWriter stringbuffer = new StringWriter();
		XMLStreamWriter out = XMLOutputFactory.newInstance()
				.createXMLStreamWriter(stringbuffer);

		// Inicia elemento mensaje con su atributo "id"
		out.writeStartDocument();
		out.writeStartElement("mensaje");
		out.writeAttribute("id", getId().toString());

		// Sub-elemento "tipo" con su contenido
		out.writeStartElement("tipo");
		out.writeCharacters(getTipo().toString());
		out.writeEndElement();

		// Sub-elemento "codificacion" con su contenido
		out.writeStartElement("codificacion");
		out.writeCharacters(getCodificacion().toString());
		out.writeEndElement();

		// Escribe el contenido y cierra el mensaje
		out.writeCharacters(getContenido());
		out.writeEndElement();
		out.writeEndDocument();

		out.close();

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
