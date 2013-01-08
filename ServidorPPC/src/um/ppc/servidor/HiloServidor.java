package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;

public class HiloServidor extends Thread {
	private Socket socket;
	private Codificacion codificacion;

	public HiloServidor(Socket socket, Codificacion codificacion) {
		this.socket = socket;

		this.codificacion = codificacion;
	}

	public void run() {
		try {
			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			String datosLeidos = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
			String cadena = "";
			Mensaje mensaje = null;

			switch (codificacion) {
			case ASN1:
				break;
			case JSON:
				mensaje = MensajeBuilder.desdeJSON(datosLeidos);
				cadena = tratarPeticionJSON(mensaje, salidaCliente);
				break;
			case XML:
				mensaje = MensajeBuilder.desdeXML(datosLeidos);
				cadena = tratarPeticionXML(mensaje, salidaCliente);
				break;
			default:
				System.out.println("Codificacion desconocida");
				cadena = "Codificacion desconocida." + '\n';
			}

			// Enviar el mensaje al cliente
//			salidaCliente.writeInt(cadena.length());
			salidaCliente.writeChars(cadena);
			salidaCliente.flush();
			
			socket.close();

		} catch (Exception ex) {
			System.out.println("Error en la conexion.");
		}
	}

	private String tratarPeticionJSON(Mensaje mensaje, DataOutputStream salidaCliente) throws IOException {
		String cadena = "";
		if (mensaje == null) {
			cadena = "El servidor solo acepta mensajes JSON." + '\n';
		} else {
			if (mensaje.getTipoMensaje() == TipoMensaje.CLIENTHELLO) {
				Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
				serverHello.setCodificacion(Codificacion.JSON);
				serverHello.setContenido("OK");
				cadena = serverHello.toJSON().replace('\n', ' ') + '\n';
				System.out.println("Cliente " + socket.getInetAddress().toString() + "(JSON) autorizado con exito.");
			} else if (mensaje.getTipoMensaje() == TipoMensaje.PEDIROBJETO) {
				Mensaje darObjeto = new Mensaje(TipoMensaje.DAROBJETO);
				darObjeto.setCodificacion(Codificacion.JSON);
				darObjeto.setTipo(mensaje.getTipoObjCriptografico());
				darObjeto.setContenido("CERTIFICADO");
				cadena = darObjeto.toJSON().replace('\n', ' ') + '\n';
				System.out.println("Cliente " + socket.getInetAddress().toString() + " se le envio objeto " + mensaje.getTipoObjCriptografico());
			} else {
				cadena = "Se ha recibido un tipo de mensaje no esperado" + '\n';
			}

		}
		return cadena;
	}

	private String tratarPeticionXML(Mensaje mensaje, DataOutputStream salidaCliente) throws IOException {
		String cadena = "";
		if (mensaje == null) {
			cadena = "El servidor solo acepta mensajes XML." + '\n';
		} else {
			if (mensaje.getTipoMensaje() == TipoMensaje.CLIENTHELLO) {
				Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
				serverHello.setCodificacion(Codificacion.XML);
				serverHello.setContenido("OK");
				cadena = serverHello.toXML().replace('\n', ' ') + '\n';
				System.out.println("Cliente " + socket.getInetAddress().toString() + "(XML) autorizado con exito.");
			} else if (mensaje.getTipoMensaje() == TipoMensaje.PEDIROBJETO) {
				Mensaje darObjeto = new Mensaje(TipoMensaje.DAROBJETO);
				darObjeto.setCodificacion(Codificacion.XML);
				darObjeto.setTipo(mensaje.getTipoObjCriptografico());
				darObjeto.setContenido("CERTIFICADO");
				cadena = darObjeto.toXML().replace('\n', ' ') + '\n';
				System.out.println("Cliente " + socket.getInetAddress().toString() + " se le envio objeto " + mensaje.getTipoObjCriptografico());
			} else {
				cadena = "Se ha recibido un tipo de mensaje no esperado" + '\n';
			}
		}
		return cadena;
	}
}