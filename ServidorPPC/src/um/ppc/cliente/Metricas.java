package um.ppc.cliente;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;

public class Metricas {

	public static void main(String[] args) {

		if (args.length == 5) {
			String direccion = args[0];
			TipoObjetoCriptografico objCripto = TipoObjetoCriptografico.valueOf(args[1]);
			String textoAFirmar = args[2];
			Codificacion codificacion = Codificacion.valueOf(args[3].toUpperCase());
			int numMensajes = Integer.parseInt(args[4]);

			try {
				Class<?> claseCodificacion = Class.forName(Cliente.class.getName() + codificacion.toString());
				Cliente cliente = (Cliente) claseCodificacion.newInstance();

				Vector<Long> medidas = new Vector<Long>();
				long media = 0;
				// Repite la solicitud "numMensajes" veces
				for (int i = 0; i < numMensajes; i++) {
					long inicio = System.nanoTime();
					cliente.realizarSolicitud(direccion, objCripto, textoAFirmar);
					long diferencia = System.nanoTime() - inicio;

					// Guarda la medida de tiempo
					medidas.add(diferencia);
					media += diferencia;
				}
				
				// Calcula la media
				media /= numMensajes;

				System.out.println("Tiempo medio: " + media / 1000000 + " ms");

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Uso: metricas <servidor> PKCS[1|7] <texto> <codificacion> <iteraciones>");
			System.out.println("\nEjemplo:\n metricas 192.168.1.128 PKCS1 hola ASN1 100");
		}
	}
}
