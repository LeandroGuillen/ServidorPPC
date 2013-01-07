package um.ppc.cliente;

import java.io.DataOutputStream;
import java.io.IOException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;

public class ClienteASN1 extends Cliente{
	@Override
	public Codificacion getCodificacion() {
		return Codificacion.ASN1;
	}

	@Override
	protected void enviaMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException {
		salida.write(mensaje.toASN1());
	}
	
	@Override
	protected Mensaje recibirMensaje(String respuesta) throws IOException {
		return MensajeBuilder.desdeASN1(respuesta);
	}
}
