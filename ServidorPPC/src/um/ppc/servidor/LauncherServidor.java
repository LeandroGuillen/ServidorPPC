package um.ppc.servidor;

import java.awt.EventQueue;

import um.ppc.servidor.gui.VentanaServidor;

public class LauncherServidor {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaServidor();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
