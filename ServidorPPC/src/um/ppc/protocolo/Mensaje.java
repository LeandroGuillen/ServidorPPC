package um.ppc.protocolo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

import com.google.gson.Gson;

public class Mensaje {
	private TipoMensaje tipoMensaje;
	private TipoObjetoCriptografico tipo;
	private Codificacion codificacion;
	private String contenido;
	
	public Mensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Mensaje(String string){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();

            // Use String reader
            Document document = builder.parse( new InputSource(new StringReader(string)));

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            Source src = new DOMSource( document );
            Result dest = new StreamResult( new File( "xmlFileName.xml" ) );
            aTransformer.transform( src, dest );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	public String toJSON() {
		return new Gson().toJson(this);
	}

	public String toXML() throws UnsupportedEncodingException, FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		StringWriter stringbuffer = new StringWriter();
		XMLStreamWriter xml = XMLOutputFactory.newInstance().createXMLStreamWriter(stringbuffer);

		// Inicia elemento mensaje con su atributo "id", siempre estara presente en el mensaje
		xml.writeStartDocument();
		xml.writeStartElement("mensaje");
		xml.writeAttribute("id", getId().toString());

		// Sub-elemento "tipo" con su contenido
		if(getTipo()!=null){
			xml.writeStartElement("tipo");
			xml.writeCharacters(getTipo().toString());
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

	public Integer getId() {
		return tipoMensaje.ordinal();
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
