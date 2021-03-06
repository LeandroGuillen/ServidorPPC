package um.ppc.cliente.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.MensajeBuilder;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoMensaje;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.Servidor;

public class ClienteTest1 {
	public static void main(String argv[]) throws Exception {
		String frase;
		String fraseModificada;
		BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));
		Socket socketCliente = new Socket("localhost", Servidor.PUERTO);
		DataOutputStream salidaServidor = new DataOutputStream(socketCliente.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
		System.out.println("[CLIENTE] Introduce un contenido del mensaje:");
		frase = entradaUsuario.readLine();

		Mensaje m = new Mensaje(TipoMensaje.CLIENTHELLO);
		m.setCodificacion(Codificacion.JSON);
		m.setTipo(TipoObjetoCriptografico.PKCS1);
		m.setContenido(frase);

		// salidaServidor.writeBytes(frase + '\n');
		salidaServidor.writeBytes(MensajeBuilder.toXML(m) + '\n');
		
		fraseModificada = entradaServidor.readLine();
		System.out.println("DEL SERVIDOR: " + fraseModificada);
		socketCliente.close();
	}
}