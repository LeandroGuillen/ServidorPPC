package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteXML extends Cliente {

	@Override
	public Codificacion getCodificacion() {
		return Codificacion.XML;
	}

	@Override
	protected void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		salida.writeBytes(MensajeBuilder.toXML(mensaje).replace('\n', ' ') + '\n');
	}

	@Override
	protected Mensaje recibirMensaje(DataInputStream entrada) throws IOException {
		BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(entrada));
		return MensajeBuilder.desdeXML(bufferEntrada.readLine());
	}
}
