package um.ppc.servidor;

import java.awt.EventQueue;

import um.ppc.servidor.gui.VentanaPrincipal;

public class GUILauncher {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaPrincipal();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
