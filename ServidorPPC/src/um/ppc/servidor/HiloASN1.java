package um.ppc.servidor;

import java.io.DataInputStream;
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
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida)
			throws IOException {
		byte[] bytes = MensajeBuilder.toASN1(mensaje);
		int longitud = bytes.length;
		salida.writeInt(longitud);
		salida.write(bytes);
	}

	@Override
	protected Mensaje recibirMensaje(DataInputStream entrada) throws IOException {
		int longitud = entrada.readInt();

		byte[] bytes = new byte[longitud];
		for (int i = 0; i < longitud; i++) {
			bytes[i] = entrada.readByte();
		}
		return MensajeBuilder.desdeASN1(bytes);
	}

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.ASN1;
	}

}
