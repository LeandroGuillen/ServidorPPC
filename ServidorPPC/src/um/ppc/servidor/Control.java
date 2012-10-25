package um.ppc.servidor;

public class Control {
	ServidorTCP servidor;

	public Control() {
		servidor = new ServidorTCP();
	}

	public void iniciarServidor() {
		// TODO Si se ejecuta esta funcion 2 veces casca todo
		servidor.start();
	}

	public void pararServidor() {
	}
}
