package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.ServidorTCP;

public abstract class Cliente {
	public abstract Codificacion getCodificacion();

	protected abstract void enviaMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException;

	protected abstract Mensaje recibirMensaje(String respuesta) throws IOException;

	public void realizarSolicitud(String direccion, TipoObjetoCriptografico objCripto, String textoAFirmar) throws UnknownHostException, IOException {
		if (conectarConServidor(direccion))
			pedirObjetoCriptografico(direccion, objCripto, textoAFirmar);
	}

	private boolean conectarConServidor(String direccion) throws UnknownHostException, IOException {
		boolean ok = false;
		Socket socket = new Socket(direccion, ServidorTCP.PUERTO);
		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Define un mensaje
		Mensaje clientHello = new Mensaje(TipoMensaje.CLIENTHELLO);
		// Construye el contenido
		clientHello.setCodificacion(getCodificacion());

		// Envia al servidor
		enviaMensaje(clientHello, salidaServidor);

		// Espera una respuesta
		String respuesta = entradaServidor.readLine();

		// Si el mensaje SERVERHELLO se recibe y todo va bien seguir adelante
		Mensaje mensaje = recibirMensaje(respuesta);

		if (mensaje != null && mensaje.getTipoMensaje() == TipoMensaje.SERVERHELLO) {
			ok = true;
		}

		socket.close();
		return ok;
	}

	private void pedirObjetoCriptografico(String direccion, TipoObjetoCriptografico objCripto, String textoAFirmar) throws UnknownHostException, IOException {
		Socket socket = new Socket(direccion, ServidorTCP.PUERTO);
		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// El cliente indica que quiere pedir un objeto tipo PKCS#1
		Mensaje pedirObjeto = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObjeto.setTipo(objCripto);
		pedirObjeto.setContenido(textoAFirmar);

		// Envia al servidor un mensaje 'pedirObjeto'
		enviaMensaje(pedirObjeto, salidaServidor);

		String respuesta = entradaServidor.readLine();

		// Si el mensaje DAROBJETO se recibe y todo va bien seguir adelante
		Mensaje mensaje = recibirMensaje(respuesta);

		if (mensaje != null && mensaje.getTipoMensaje() == TipoMensaje.DAROBJETO) {
			// Almacenar objeto criptografico
			System.out.println("Servidor: " + mensaje.getContenido());
		}
		socket.close();
	}
}
