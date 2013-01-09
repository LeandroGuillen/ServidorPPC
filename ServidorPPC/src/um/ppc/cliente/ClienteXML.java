package um.ppc.cliente;

import java.io.DataOutputStream;
import java.io.IOException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteXML extends Cliente {

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.XML;
	}

	@Override
	protected void enviaMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		salida.writeBytes(MensajeBuilder.toXML(mensaje).replace('\n', ' ') + '\n');
	}

	@Override
	protected Mensaje recibirMensaje(String respuesta) throws IOException {
		return MensajeBuilder.desdeXML(respuesta);
	}

}
