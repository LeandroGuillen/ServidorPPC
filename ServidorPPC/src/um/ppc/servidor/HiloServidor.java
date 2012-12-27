package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.enumerados.Codificacion;

public class HiloServidor extends Thread {
	private Socket socket;
	private Codificacion codificacion;

	HiloServidor(Socket socket, Codificacion codificacion) {
		this.socket = socket;
		this.codificacion = codificacion;
	}

	public void run() {

		try {

			String datosLeidos = leerDatos();
			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Datos recibidos:\n" + datosLeidos);
			// Reconstruir mensaje recibido
			
			// Si se ha recibido un CLIENTHELLO
			
			// Construir SERVERHELLO
			
			// Si la codificacion es correcta entonces todo OK.
			
			// Si la codificacion no es correcta entonces enviara un SERVERHELLO diciendo que hay un error.

			// Si se ha recibido un PEDIROBJETO
			
			// Atender la peticion
			// Respuesta para el cliente
			salidaCliente.writeBytes("He recibido el mensaje, gracias." + '\n');
			
			socket.close();			
		} catch (Exception ex) {
		}
		System.out.println("Conexión con " + socket.getInetAddress().toString() + " finalizada");
		
	}

	private String leerDatos() throws IOException {
		BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String fraseCliente = entradaCliente.readLine();
		return fraseCliente;
	}
}