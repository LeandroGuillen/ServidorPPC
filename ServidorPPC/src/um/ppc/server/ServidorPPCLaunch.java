package um.ppc.server;

import java.awt.EventQueue;

import um.ppc.server.gui.VentanaPrincipal;

public class ServidorPPCLaunch {

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
