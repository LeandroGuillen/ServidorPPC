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

public abstract class Hilo extends Thread {
	private Socket socket;

	public Hilo(Socket socket) {
		super(Hilo.class.getSimpleName());
		this.socket = socket;
	}

	public abstract Codificacion getCodificacion();

	protected abstract String generaMensaje(Mensaje mensaje);

	protected abstract Mensaje recibirMensaje(String respuesta) throws IOException;

	public void run() {
		try {
			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			String datosLeidos = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
			String cadena = "";

			Mensaje mensaje = recibirMensaje(datosLeidos);

			if (mensaje == null) {
				cadena = "El servidor solo acepta mensajes " + getCodificacion() + ".";
			} else {

				// Estos mensajes sirven para comprobar que el cliente y el
				// servidor estan hablando con la misma version del protocolo.
				if (mensaje.getTipoMensaje() == TipoMensaje.CLIENTHELLO) {
					Mensaje serverHello = new Mensaje(TipoMensaje.SERVERHELLO);
					serverHello.setCodificacion(getCodificacion());
					serverHello.setContenido("OK"); // no utilizado en ningun
													// sitio
					cadena = generaMensaje(serverHello);
					System.out.println("Cliente " + socket.getInetAddress().toString() + "(" + getCodificacion() + ") autorizado con exito.");
				}

				// Ofrecer el servicio demandado al cliente
				else if (mensaje.getTipoMensaje() == TipoMensaje.PEDIROBJETO) {

					// Obtener parametros necesarios del mensaje del cliente
					TipoObjetoCriptografico toc = mensaje.getTipoObjCriptografico();
					String contenidoAFirmar = mensaje.getContenido();

					// Realizar la firma (PKCS#1) ???

					// Realizar firma y cifrado (PKCS#7) ???

					// Construir respuesta
					Mensaje darObjeto = new Mensaje(TipoMensaje.DAROBJETO);
					darObjeto.setCodificacion(getCodificacion());
					darObjeto.setTipo(toc);
					darObjeto.setContenido("CERTIFICADO");
					cadena = generaMensaje(darObjeto);
					System.out.println("Cliente " + socket.getInetAddress().toString() + " se le envio objeto " + mensaje.getTipoObjCriptografico());
				}

				else {
					cadena = "Se ha recibido un tipo de mensaje no esperado";
				}

			}

			// Enviar el mensaje al cliente
			salidaCliente.writeBytes(cadena + '\n');
			salidaCliente.flush();
			socket.close();

		} catch (IOException ex) {
			System.out.println("Error en la conexion.");
		}
	}
}