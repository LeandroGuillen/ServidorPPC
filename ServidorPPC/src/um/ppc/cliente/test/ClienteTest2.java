package um.ppc.cliente.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.ServidorTCP;

public class ClienteTest2 {
	public static void main(String argv[]) throws Exception {
		String respuesta;
		Socket socket = new Socket("localhost", ServidorTCP.PUERTO);
		DataOutputStream salidaServidor = new DataOutputStream(socket.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("ClienteXML iniciado.");

//		CLIENTHELLO, SERVERHELLO, PEDIROBJETO, DAROBJETO
		
		System.out.println("Enviar mensaje ClientHello.");
		
		// Define un mensaje
		Mensaje clientHello = new Mensaje(TipoMensaje.CLIENTHELLO);
		// Construye el contenido
		clientHello.setCodificacion(Codificacion.XML);
		// Envia al servidor
		salidaServidor.writeBytes(clientHello.toXML() + '\n');
		// Espera una respuesta
		respuesta = entradaServidor.readLine();
		
		System.out.println("Respuesta:\n"+respuesta);
		
		
		// Aqui se supone que el servidor envio un SERVERHELLO y que todo fue correcto.
		// ...
		// ...
		
		socket.close();
		
		// Volver a crear un socket nuevo
		socket = new Socket("localhost", ServidorTCP.PUERTO);
		salidaServidor = new DataOutputStream(socket.getOutputStream());
		entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("Enviar mensaje PedirObjeto.");
		
		// El cliente indica que quiere pedir un objeto tipo PKCS#1
		Mensaje pedirObjeto = new Mensaje(TipoMensaje.PEDIROBJETO);
		pedirObjeto.setTipo(TipoObjetoCriptografico.PKCS1);
		salidaServidor.writeBytes(pedirObjeto.toXML() + '\n');
		respuesta = entradaServidor.readLine();
		System.out.println("Respuesta:\n"+respuesta);
		
		// Suponemos que el servidor se lo ha enviado aqui
		// almacenar en local el objeto y comprobar su validez
		// ...
		// ...
		
		
		
		socket.close();
	}
}