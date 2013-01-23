package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteASN1 extends Cliente {
	@Override
	public Codificacion getCodificacion() {
		return Codificacion.ASN1;
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
	// byte b = (byte) entrada.read();
	//
	// // Leer bytes de la entrada
	// do {
	// bytes.add(b);
	// b = (byte) entrada.read();
	// } while (b != -1);
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
		if (longitud < 0)
			return null;
		byte[] bytes = new byte[longitud];
		for (int i = 0; i < longitud; i++) {
			bytes[i] = (byte) entrada.read();
		}
		Mensaje mensaje = MensajeBuilder.desdeASN1(bytes);
		return mensaje;
	}
}
