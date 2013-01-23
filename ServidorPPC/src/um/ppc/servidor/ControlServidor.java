package um.ppc.servidor;

import java.io.IOException;

import um.ppc.protocolo.enumerados.Codificacion;

/**
 * Sirve de nexo de unión entre la GUI y el servidor en si mismo (controlador).
 * Proporcional la funcionalidad de parar e iniciar el servidor.
 * 
 * @author leandro
 * 
 */

public class ControlServidor {
	private Servidor servidor = null;

	public void iniciarServidor(Codificacion codificacion) {
		if (servidor == null) {
			servidor = new Servidor(codificacion);
			servidor.start();
		} else {
			System.out.println("El servidor ya está iniciado.");
		}
	}

	public void pararServidor() {
		if (servidor != null) {
			try {
				servidor.parar();
			} catch (IOException e) {
				System.out.println("Ha ocurrido un error leyendo del socket.");
			}
		} else {
			System.out.println("El servidor ya está parado.");
		}
		servidor = null;
	}
}
