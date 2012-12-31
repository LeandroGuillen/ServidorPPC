package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.ServidorTCP;

public class Control {

	public static void realizarSolicitud(String direccion, TipoObjetoCriptografico objCripto) throws UnknownHostException, IOException {
		if (conectarConServidor(direccion))
			pedirObjetoCriptografico(direccion, objCripto);
	}

	public static boolean conectarConServidor(String direccion) throws UnknownHostException, IOException {
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
		clientHello.setCodificacion(Codificacion.XML);
		// clientHello.setContenido("contenido del mensaje");
		// Envia al servidor
		salidaServidor.writeBytes(clientHello.toXML().replace('\n', ' ') + '\n');
		// Espera una respuesta
		respuesta = entradaServidor.readLine();

		// Si el mensaje SERVERHELLO se recibe y todo va bien seguir adelante
		Mensaje mensaje = desdeXML(respuesta);
		if (mensaje != null)
			if (mensaje.getTipoMensaje() == TipoMensaje.SERVERHELLO && mensaje.getContenido().equalsIgnoreCase("ok"))
				ok = true;

		socket.close();
		return ok;
	}

	public static void pedirObjetoCriptografico(String direccion, TipoObjetoCriptografico objCripto) throws UnknownHostException, IOException {
		String respuesta;
		Socket socket = new Socket(direccion, ServidorTCP.PUERTO);
		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// El cliente indica que quiere pedir un objeto tipo PKCS#1
		Mensaje pedirObjeto = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObjeto.setTipo(objCripto);
		salidaServidor.writeBytes(pedirObjeto.toXML().replace('\n', ' ') + '\n');
		respuesta = entradaServidor.readLine();

		// Si el mensaje DAROBJETO se recibe y todo va bien seguir adelante
		Mensaje mensaje = desdeXML(respuesta);
		if (mensaje != null)
			if (mensaje.getTipoMensaje() == TipoMensaje.DAROBJETO) {
				// Almacenar objeto criptografico
				System.out.println("Servidor: " + mensaje.getContenido());
			}
		socket.close();
	}

	public static Mensaje desdeXML(String datosLeidos) {
		Mensaje mensaje = null;

		// Transformar datos en XML a Mensaje
		XStream xstream = new XStream();
		xstream.alias("mensaje", Mensaje.class);
		try {
			mensaje = (Mensaje) xstream.fromXML(datosLeidos);
		} catch (XStreamException e) {
			// El mensaje no esta en formato XML valido
			System.out.println("El mensaje no esta en formato XML valido");
		}

		return mensaje;
	}
}
