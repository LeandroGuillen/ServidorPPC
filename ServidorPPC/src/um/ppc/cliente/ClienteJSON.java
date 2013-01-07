package um.ppc.cliente;

import java.io.DataOutputStream;
import java.io.IOException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteJSON extends Cliente{
	@Override
	public Codificacion getCodificacion() {
		return Codificacion.JSON;
	}

	@Override
	protected void enviaMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		salida.writeBytes(mensaje.toJSON().replace('\n', ' ') + '\n');
	}
	
	@Override
	protected Mensaje recibirMensaje(String respuesta) throws IOException {
		return MensajeBuilder.desdeJSON(respuesta);
	}
}
