package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class HiloXML extends Hilo {

	public HiloXML(Socket socket) {
		super(socket);
	}

	@Override
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		String cadena = MensajeBuilder.toXML(mensaje).replace('\n', ' ') + '\n';
		salida.writeBytes(cadena + '\n');
	}

	@Override
	protected Mensaje recibirMensaje(BufferedReader entrada) throws IOException {
		return MensajeBuilder.desdeXML(entrada.readLine());
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.XML;
	}

}
