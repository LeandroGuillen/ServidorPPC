package um.ppc.cliente;

import java.awt.EventQueue;

import um.ppc.cliente.gui.VentanaCliente;

public class LauncherClienteXML {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaCliente(new ClienteXML());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
