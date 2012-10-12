package um.ppc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {

	@Override
	public void run() {
		
		ServerSocket socketPrincipal;
		try {
			socketPrincipal = new ServerSocket(6789);
			System.out.println("Esperando conexiones");
			while (true) {
				Socket socketConexion = socketPrincipal.accept();
				System.out.println("Conexion recibida"); // TODO
				HiloCliente hilo = new HiloCliente(socketConexion);
				hilo.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Servidor() {
		super("HiloServidor");
	}
}