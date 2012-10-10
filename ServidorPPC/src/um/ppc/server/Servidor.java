package um.ppc.server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private boolean running;
	private static Servidor instancia;

	public static void iniciar() {
		get().setRunning(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ServerSocket socketPrincipal;
				try {
					socketPrincipal = new ServerSocket(6789);
					System.out.println("Esperando conexiones");
					while (get().isRunning()) {
						Socket socketConexion = socketPrincipal.accept();
						System.out.println("Conexion recibida"); // TODO
						HiloServidor hilo = new HiloServidor(socketConexion);
						hilo.start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void parar() {
		get().setRunning(false);
	}

	private Servidor() {
		running = false;
	}

	private static Servidor get() {
		if (instancia == null)
			instancia = new Servidor();
		return instancia;
	}

	private synchronized boolean isRunning() {
		return running;
	}

	private synchronized void setRunning(boolean funcionando) {
		running = funcionando;
	}
}