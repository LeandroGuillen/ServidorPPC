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

	HiloServidor(Socket socketConexion, Codificacion codificacion) {
		this.socket = socketConexion;
		this.codificacion = codificacion;
	}

	public void run() {

		try {

			String datosLeidos = leerDatos();

			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			// fraseEnMayusculas = fraseCliente.toUpperCase() + '\n';
			salidaCliente.writeBytes("Recibido, gracias." + '\n');
		} catch (Exception ex) {
		}
		System.out.println("SERVIDOR: Conexión con " + socket.getInetAddress().toString() + " finalizada");
	}

	private String leerDatos() throws IOException {
		BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String fraseCliente = entradaCliente.readLine();
		return fraseCliente;
	}
}