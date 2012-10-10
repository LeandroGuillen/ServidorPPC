package um.ppc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class HiloServidor extends Thread {
	Socket socketConexion;

	HiloServidor(Socket socketConexion) {
		this.socketConexion = socketConexion;
	}

	public void run() {
		String fraseCliente;
		String fraseEnMayusculas;
		System.out.println("Iniciando hilo");
		try {
			BufferedReader entradaCliente = new BufferedReader(
					new InputStreamReader(socketConexion.getInputStream()));
			DataOutputStream salidaCliente = new DataOutputStream(
					socketConexion.getOutputStream());
			fraseCliente = entradaCliente.readLine();
			fraseEnMayusculas = fraseCliente.toUpperCase() + '\n';
			salidaCliente.writeBytes(fraseEnMayusculas);
		} catch (Exception ex) {
		}
		System.out.println("Finalizando hilo");
	}
}
