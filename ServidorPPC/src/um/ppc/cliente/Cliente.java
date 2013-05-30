package um.ppc.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.DatatypeConverter;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.Servidor;

public abstract class Cliente {
	public abstract Codificacion getCodificacion();

	protected abstract void enviarMensaje(Mensaje mensaje, DataOutputStream salida) throws IOException;

//	protected abstract Mensaje recibirMensaje(BufferedReader entrada) throws IOException;
	protected abstract Mensaje recibirMensaje(DataInputStream entrada) throws IOException;

	public void realizarSolicitud(String direccion, TipoObjetoCriptografico objCripto, String textoAFirmar) throws UnknownHostException, IOException {
		if (conectarConServidor(direccion))
			pedirObjetoCriptografico(direccion, objCripto, textoAFirmar);
	}

	private boolean conectarConServidor(String direccion) throws UnknownHostException, IOException {
		boolean ok = false;
		Socket socket = new Socket(direccion, Servidor.PUERTO);
		DataOutputStream salidaServidor = new DataOutputStream(socket.getOutputStream());
		DataInputStream entradaServidor = new DataInputStream(socket.getInputStream());
//		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Define un mensaje
		Mensaje clientHello = new Mensaje(TipoMensaje.CLIENTHELLO);
		// Construye el contenido
		clientHello.setCodificacion(getCodificacion());

		// Envia al servidor
		enviarMensaje(clientHello, salidaServidor);

		// Espera una respuesta
		Mensaje mensaje = recibirMensaje(entradaServidor);

		// Si el mensaje SERVERHELLO se recibe y todo va bien seguir adelante
		if (mensaje != null && mensaje.getTipoMensaje() == TipoMensaje.SERVERHELLO) {
			ok = true;
		}

		socket.close();
		return ok;
	}

	private void pedirObjetoCriptografico(String direccion, TipoObjetoCriptografico objCripto, String textoAFirmar) throws UnknownHostException, IOException {
		Socket socket = new Socket(direccion, Servidor.PUERTO);
		DataOutputStream salidaServidor;
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		DataInputStream entradaServidor = new DataInputStream(socket.getInputStream());
//		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// El cliente indica que quiere pedir un objeto tipo PKCS#1
		Mensaje pedirObjeto = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObjeto.setTipo(objCripto);
		pedirObjeto.setContenido(textoAFirmar);

		// Envia al servidor un mensaje 'pedirObjeto'
		enviarMensaje(pedirObjeto, salidaServidor);

		// Espera una respuesta
		Mensaje mensaje = recibirMensaje(entradaServidor);

		// Si el mensaje DAROBJETO se recibe y todo va bien seguir adelante
		if (mensaje != null && mensaje.getTipoMensaje() == TipoMensaje.DAROBJETO) {
			// Almacenar objeto criptografico
			System.out.println("Servidor:\n" + mensaje.getContenido());

			// Guardar la firma en disco
			String nombreArchivo = "";
			if (mensaje.getTipoObjCriptografico() == TipoObjetoCriptografico.PKCS1) {
				nombreArchivo = "cli_firmaPKCS1";
			} else if (mensaje.getTipoObjCriptografico() == TipoObjetoCriptografico.PKCS7) {
				nombreArchivo = "cli_firmaPKCS7";
			}
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			byte[] bytes = DatatypeConverter.parseBase64Binary(mensaje.getContenido());
			fos.write(bytes);
			fos.close();
		}
		socket.close();
	}
}
