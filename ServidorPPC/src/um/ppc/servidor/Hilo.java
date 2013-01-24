package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

/**
 * La clase Hilo realiza el trabajo pesado del servidor. Interpreta los mensajes
 * de los clientes y envía respuestas apropiadas.
 * 
 * @author leandro
 * 
 */
public abstract class Hilo extends Thread {
	private Socket socket;

	public Hilo(Socket socket) {
		super(Hilo.class.getSimpleName());
		this.socket = socket;
	}

	public abstract Codificacion getCodificacion();

	protected abstract void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException;

	protected abstract Mensaje recibirMensaje(BufferedReader entrada) throws IOException;

	public void run() {
		try {
			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Mensaje mensaje = recibirMensaje(entradaCliente);

			// Se ha recibido un mensaje correctamente
			if (mensaje != null) {
				// Estos mensajes sirven para comprobar que el cliente y el
				// servidor estan hablando con la misma version del protocolo.
				if (mensaje.getTipoMensaje() == TipoMensaje.CLIENTHELLO)
					tratarConexionNueva(salidaCliente);

				// Ofrecer el servicio demandado al cliente
				else if (mensaje.getTipoMensaje() == TipoMensaje.PEDIROBJETO)
					tratarPeticion(salidaCliente, mensaje);

				// Si entra aqui hemos recibido un tipo de mensaje raro
				else
					System.out.println("Se ha recibido un tipo de mensaje no esperado");
			} else {
				// Algo raro paso al leer el mensaje
				System.out.println("El servidor solo acepta mensajes " + getCodificacion() + ".");
			}

			salidaCliente.flush();

		} catch (IOException ex) {
			System.out.println("Error en la conexion:\n" + ex);
		}
	}

	private void tratarConexionNueva(DataOutputStream salidaCliente) throws IOException {
		Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
		serverHello.setCodificacion(getCodificacion());
		// Enviar el mensaje al cliente
		enviarMensaje(serverHello, salidaCliente);

		System.out.println("- Nueva conexión de" + socket.getInetAddress().toString() + " (" + getCodificacion() + ").");
	}

	private void tratarPeticion(DataOutputStream salidaCliente, Mensaje mensaje) throws IOException {
		// Obtener parametros necesarios del mensaje del cliente
		TipoObjetoCriptografico toc = mensaje.getTipoObjCriptografico();
		String contenidoAFirmar = mensaje.getContenido();
		String objetoRespuesta = "";

		try {
			switch (toc) {

			case PKCS1:
				// Realizar la firma PKCS#1
				objetoRespuesta = ServicioCriptografico.firmarPKCS1(contenidoAFirmar);
				break;

			case PKCS7:
				// Realizar firma PKCS#7
				objetoRespuesta = ServicioCriptografico.firmarPKCS7(contenidoAFirmar);
				break;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		// Construir respuesta
		Mensaje darObjeto = new Mensaje(TipoMensaje.DAROBJETO);

		darObjeto.setCodificacion(getCodificacion());
		darObjeto.setTipo(toc);
		darObjeto.setContenido(objetoRespuesta);

		// Enviar el mensaje al cliente
		enviarMensaje(darObjeto, salidaCliente);

		System.out.println("  Enviado objeto " + mensaje.getTipoObjCriptografico() +" a "+ socket.getInetAddress().toString());
	}

}