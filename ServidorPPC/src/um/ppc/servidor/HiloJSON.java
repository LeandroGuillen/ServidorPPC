package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class HiloJSON extends Hilo {

	public HiloJSON(Socket socket) {
		super(socket);

	}

	@Override
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		String cadena = MensajeBuilder.toJSON(mensaje).replace('\n', ' ') + '\n';
		salida.writeBytes(cadena + '\n');
	}

	@Override
	protected Mensaje recibirMensaje(DataInputStream entrada) throws IOException {
		BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(entrada));
		return MensajeBuilder.desdeJSON(bufferEntrada.readLine());
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.JSON;
	}

}
