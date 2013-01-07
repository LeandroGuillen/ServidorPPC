package um.ppc.cliente;

import java.awt.EventQueue;

import um.ppc.cliente.gui.VentanaCliente;

public class LauncherClienteASN1 {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaCliente(new ClienteASN1());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
