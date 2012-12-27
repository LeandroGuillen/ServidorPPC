package um.ppc.clienteXML;

import java.awt.EventQueue;

import um.ppc.clienteXML.gui.VentanaPrincipal;

public class LauncherClienteXML {

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
