package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		byte[] bytes = MensajeBuilder.toASN1(mensaje);
		salida.write(bytes.length);
		salida.write(bytes);
	}

	// @Override
	// protected Mensaje recibirMensaje(BufferedReader entrada) throws
	// IOException {
	// int longitud = entrada.read();
	//
	// Vector<Byte> bytes = new Vector<Byte>();
	// byte b;
	// // Leer bytes de la entrada
	// while ((b = (byte) entrada.read()) != -1) {
	// bytes.add(b);
	// }
	//
	// // Transformar en array de bytes
	// byte[] arrayBytes = new byte[bytes.size()];
	// for (int i = 0; i < bytes.size(); i++) {
	// arrayBytes[i] = bytes.get(i);
	// }
	//
	// // Devolver el mensaje construido
	// Mensaje mensaje = MensajeBuilder.desdeASN1(arrayBytes);
	// return mensaje;
	// }

	@Override
	protected Mensaje recibirMensaje(BufferedReader entrada) throws IOException {
		int longitud = entrada.read();

		byte[] bytes = new byte[longitud];
		for (int i = 0; i < longitud; i++) {
			bytes[i] = (byte) entrada.read();
		}
		return MensajeBuilder.desdeASN1(bytes);
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.ASN1;
	}

}
