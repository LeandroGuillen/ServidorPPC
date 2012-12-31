package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

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
			String datosLeidos = leerLineaDelSocket();

			Mensaje mensaje = null;

			switch (codificacion) {
			case ASN1:
				break;
			case JSON:
				break;
			case XML:
				mensaje = desdeXML(datosLeidos);
				if (mensaje == null) {
					salidaCliente.writeBytes("Error al interpretar el objeto XML." + '\n');
				} else {
					if (mensaje.getTipoMensaje() == TipoMensaje.CLIENTHELLO) {
						Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
						serverHello.setCodificacion(Codificacion.XML);
						serverHello.setContenido("OK");
						salidaCliente.writeBytes(serverHello.toXML().replace('\n', ' ') + '\n');
					} else if (mensaje.getTipoMensaje() == TipoMensaje.PEDIROBJETO) {
						Mensaje darObjeto = new Mensaje(TipoMensaje.DAROBJETO);
						darObjeto.setCodificacion(Codificacion.XML);
						darObjeto.setTipo(mensaje.getTipoObjCriptografico());
						darObjeto.setContenido("CERTIFICADO");
						salidaCliente.writeBytes(darObjeto.toXML().replace('\n', ' ') + '\n');
					} else {
						// Se ha recibido un tipo de mensaje no esperado
						salidaCliente.writeBytes("Se ha recibido un tipo de mensaje no esperado" + '\n');
					}
				}
				break;
			default:
				System.out.println("Codificacion desconocida");
				salidaCliente.writeBytes("Codificacion desconocida." + '\n');
			}
			socket.close();

		} catch (Exception ex) {
			System.out.println("Error en la conexion.");
		}
		// System.out.println("Conexión con " +
		// socket.getInetAddress().toString() + " finalizada");

	}

	private Mensaje desdeXML(String datosLeidos) {
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

	@SuppressWarnings("unused")
	private String leerDatos() throws IOException {
		BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String datosLeidos = "";
		String linea = "";

		while ((linea = entradaCliente.readLine()) != "") {
			datosLeidos += linea + '\n';
		}

		return datosLeidos;
	}

	private String leerLineaDelSocket() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
	}
}