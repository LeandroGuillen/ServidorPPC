package um.ppc.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import um.ppc.protocolo.enumerados.Codificacion;

public class ServidorTCP extends Thread {
	public static int PUERTO = 6789;
	private Codificacion codificacion;
	private boolean running;
	ServerSocket socketPrincipal;

	public ServidorTCP(Codificacion codificacion) {
		super(ServidorTCP.class.getSimpleName());
		this.codificacion = codificacion;
	}

	public static void main(String argv[]) {
		Codificacion codificacion;

		if (argv.length == 0)
			codificacion = Codificacion.XML;
		else {
			codificacion = Codificacion.valueOf(argv[0].toUpperCase());
		}

		new ServidorTCP(codificacion).run();
	}

	public void run() {
		try {
			running = true;
			socketPrincipal = new ServerSocket(PUERTO);
			System.out.println("Iniciado en modo " + codificacion + ".");
			while (isRunning()) {
				Socket socket = socketPrincipal.accept();
//				System.out.println("Nueva conexión de " + socket.getInetAddress().toString());
				HiloServidor hilo = new HiloServidor(socket, codificacion);
				hilo.start();
			}
		} catch (Exception ex) {
			System.out.println("Servidor parado.");
		}
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void parar() throws IOException {
		socketPrincipal.close();
		running = false;
	}
}