package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteJSON extends Cliente {
	@Override
	public Codificacion getCodificacion() {
		return Codificacion.JSON;
	}

	@Override
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		salida.writeBytes(MensajeBuilder.toJSON(mensaje).replace('\n', ' ') + '\n');
	}

	@Override
	protected Mensaje recibirMensaje(BufferedReader entrada) throws IOException {
		return MensajeBuilder.desdeJSON(entrada.readLine());
	}
}
