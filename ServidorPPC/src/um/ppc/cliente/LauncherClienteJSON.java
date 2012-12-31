package um.ppc.cliente;

import java.awt.EventQueue;

import um.ppc.cliente.gui.VentanaCliente;
import um.ppc.protocolo.enumerados.Codificacion;

public class LauncherClienteJSON {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaCliente(Codificacion.JSON);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
