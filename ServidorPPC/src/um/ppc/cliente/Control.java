package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.ServidorTCP;

public class Control {
	// public static
	public static void realizarSolicitud(Codificacion codif, String direccion, TipoObjetoCriptografico objCripto) throws UnknownHostException, IOException {
		if (conectarConServidor(codif, direccion))
			pedirObjetoCriptografico(codif, direccion, objCripto);
	}

	public static boolean conectarConServidor(Codificacion codif, String direccion) throws UnknownHostException, IOException {
		boolean ok = false;
		String respuesta;
		Socket socket;
		socket = new Socket(direccion, ServidorTCP.PUERTO);

		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Define un mensaje
		Mensaje clientHello = new Mensaje(TipoMensaje.CLIENTHELLO);
		// Construye el contenido
		clientHello.setCodificacion(codif);

		// Envia al servidor
		switch (codif) {
		case XML:
			salidaServidor.writeBytes(clientHello.toXML().replace('\n', ' ') + '\n');
			break;
		case JSON:
			salidaServidor.writeBytes(clientHello.toJSON().replace('\n', ' ') + '\n');
			break;
		case ASN1:
			salidaServidor.write(clientHello.toASN1());
			break;
		}

		// Espera una respuesta
		respuesta = entradaServidor.readLine();

		// Si el mensaje SERVERHELLO se recibe y todo va bien seguir adelante
		Mensaje mensaje = null;

		switch (codif) {
		case XML:
			mensaje = MensajeBuilder.desdeXML(respuesta);
			break;
		case JSON:
			mensaje = MensajeBuilder.desdeJSON(respuesta);
			break;
		case ASN1:
//			mensaje = desdeASN1(respuesta);
			break;
		}

		if (mensaje != null)
			if (mensaje.getTipoMensaje() == TipoMensaje.SERVERHELLO && mensaje.getContenido().equalsIgnoreCase("ok"))
				ok = true;

		socket.close();
		return ok;
	}

	public static void pedirObjetoCriptografico(Codificacion codif, String direccion, TipoObjetoCriptografico objCripto) throws UnknownHostException, IOException {
		String respuesta;
		Socket socket = new Socket(direccion, ServidorTCP.PUERTO);
		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// El cliente indica que quiere pedir un objeto tipo PKCS#1
		Mensaje pedirObjeto = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObjeto.setTipo(objCripto);

		// Envia al servidor
		switch (codif) {
		case XML:
			salidaServidor.writeBytes(pedirObjeto.toXML().replace('\n', ' ') + '\n');
			break;
		case JSON:
			salidaServidor.writeBytes(pedirObjeto.toJSON().replace('\n', ' ') + '\n');
			break;
		case ASN1:
			salidaServidor.write(pedirObjeto.toASN1());
			break;
		}
		respuesta = entradaServidor.readLine();

		// Si el mensaje DAROBJETO se recibe y todo va bien seguir adelante
		Mensaje mensaje = null;

		switch (codif) {
		case XML:
			mensaje = MensajeBuilder.desdeXML(respuesta);
			break;
		case JSON:
			mensaje = MensajeBuilder.desdeJSON(respuesta);
			break;
		case ASN1:
//			mensaje = desdeASN1(respuesta);
			break;
		}
		
		if (mensaje != null)
			if (mensaje.getTipoMensaje() == TipoMensaje.DAROBJETO) {
				// Almacenar objeto criptografico
				System.out.println("Servidor: " + mensaje.getContenido());
			}
		socket.close();
	}


}
