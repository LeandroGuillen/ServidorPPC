package um.ppc.cliente;

import java.awt.EventQueue;

import um.ppc.cliente.gui.VentanaCliente;
import um.ppc.protocolo.enumerados.Codificacion;

public class LauncherClienteASN1 {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaCliente(Codificacion.ASN1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
