package um.ppc.servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP extends Thread {
	public static int PUERTO = 6789;

	public static void main(String argv[]) {
		new ServidorTCP().run();
	}
	
	public void run() {
		try {
			ServerSocket socketPrincipal = new ServerSocket(PUERTO);

			while (true) {
				Socket socketConexion = socketPrincipal.accept();
				System.out.println("Conexion recibida");
				HiloServidor hilo = new HiloServidor(socketConexion);
				hilo.start();
			}
		} catch (Exception ex) {
		}
	}
}