package um.ppc.servidor;

import java.io.IOException;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class HiloJSON extends Hilo {

	public HiloJSON(Socket socket) {
		super(socket);

	}

	@Override
	protected String generaMensaje(Mensaje mensaje) {
		return mensaje.toJSON().replace('\n', ' ') + '\n';
	}

	@Override
	protected Mensaje recibirMensaje(String respuesta) throws IOException {
		return MensajeBuilder.desdeJSON(respuesta);
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.JSON;
	}

}
