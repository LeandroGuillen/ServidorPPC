package um.ppc.servidor;

import java.io.IOException;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class HiloASN1 extends Hilo {

	public HiloASN1(Socket socket) {
		super(socket);

	}

	@Override
	protected String generaMensaje(Mensaje mensaje) {
		// TODO Representacion Base64 ????
		return mensaje.toASN1().toString();
	}

	@Override
	protected Mensaje recibirMensaje(String respuesta) throws IOException {
		return MensajeBuilder.desdeASN1(respuesta);
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.ASN1;
	}

}
