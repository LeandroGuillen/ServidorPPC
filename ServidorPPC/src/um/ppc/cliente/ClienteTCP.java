package um.ppc.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import um.ppc.protocolo.Mensaje;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.servidor.ServidorTCP;

public class ClienteTCP {
	public static void main(String argv[]) throws Exception {
		String frase;
		String fraseModificada;
		BufferedReader entradaUsuario = new BufferedReader(
				new InputStreamReader(System.in));
		Socket socketCliente = new Socket("localhost", ServidorTCP.PUERTO);
		DataOutputStream salidaServidor = new DataOutputStream(
				socketCliente.getOutputStream());
		BufferedReader entradaServidor = new BufferedReader(
				new InputStreamReader(socketCliente.getInputStream()));
		System.out.println("[CLIENTE] Introduce un contenido del mensaje:");
		frase = entradaUsuario.readLine();
		
		Mensaje m=new Mensaje();
		m.setCodificacion(Codificacion.JSON);
		m.setTipo(TipoObjetoCriptografico.PKCS1);
		m.setId(3);
		m.setContenido(frase);
		
//		salidaServidor.writeBytes(frase + '\n');
		salidaServidor.writeBytes(m.toJSON()+m.toXML()+ '\n');
		
		fraseModificada = entradaServidor.readLine();
		System.out.println("DEL SERVIDOR: " + fraseModificada);
		socketCliente.close();
	}
}