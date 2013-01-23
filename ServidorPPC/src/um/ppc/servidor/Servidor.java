package um.ppc.servidor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;

import um.ppc.protocolo.enumerados.Codificacion;

/**
 * La clase Servidor está encargada de escuchar en el puerto especificado a
 * conexiones entrantes y crear un hilo para cada una.
 * 
 * @author leandro
 * 
 */
public class Servidor extends Thread {
	public static int PUERTO = 6789;
	private Codificacion codificacion;
	private boolean running;
	ServerSocket socketPrincipal;

	public Servidor(Codificacion codificacion) {
		super(Servidor.class.getSimpleName());
		this.codificacion = codificacion;
	}

	public static void main(String argv[]) {
		Codificacion codificacion;

		if (argv.length == 0)
			codificacion = Codificacion.XML;
		else {
			codificacion = Codificacion.valueOf(argv[0].toUpperCase());
		}

		new Servidor(codificacion).run();
	}

	public void run() {
		try {
			running = true;
			socketPrincipal = new ServerSocket(PUERTO);
			System.out.println("Iniciado en modo " + codificacion + ".");

			Class<?> claseCodificacion = Class.forName(Hilo.class.getName() + codificacion.toString());

			while (isRunning()) {
				Socket socket = socketPrincipal.accept();

				// Crea un hilo del tipo concreto y llama al constructor
				// pasandole el socket
				Constructor<?> userConstructor = claseCodificacion.getConstructor(new Class[] { Socket.class });
				Hilo hilo = (Hilo) userConstructor.newInstance(socket);

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