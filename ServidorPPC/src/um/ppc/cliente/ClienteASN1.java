package um.ppc.cliente;

import java.io.DataInputStream;
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
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida)
			throws IOException {
		byte[] bytes = MensajeBuilder.toASN1(mensaje);
		salida.writeInt(bytes.length);
		salida.write(bytes);
	}

	@Override
	protected Mensaje recibirMensaje(DataInputStream entrada) throws IOException {
		int longitud = entrada.readInt();
		if (longitud < 0)
			return null;

		// Leer bytes de la entrada
		byte[] bytes = new byte[longitud];
		for (int i = 0; i < longitud; i++) {
			bytes[i] = entrada.readByte();
		}

		// Devolver el mensaje construido
		Mensaje mensaje = MensajeBuilder.desdeASN1(bytes);
		return mensaje;
	}
}
