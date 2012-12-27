package um.ppc.clienteXML.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.servidor.gui.util.MessageConsole;

public class VentanaPrincipal {

	private JFrame frmClienteXML;
	private JTextArea textAreaSalida;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
		frmClienteXML.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClienteXML = new JFrame();
		frmClienteXML.setTitle("Cliente XML");
		frmClienteXML.setResizable(false);
		frmClienteXML.setBounds(100, 100, 406, 354);
		frmClienteXML.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClienteXML.getContentPane().setLayout(new BoxLayout(frmClienteXML.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		frmClienteXML.getContentPane().add(panel);

		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(12, 43, 112, 25);
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel.setLayout(null);
		panel.add(btnConectar);

		JButton btnCerrar = new JButton("Salir");
		btnCerrar.setBounds(293, 43, 91, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btnCerrar);
		List<String> codificaciones = new ArrayList<String>();
		for (Codificacion c : Codificacion.values()) {
			codificaciones.add(c.toString());
		}

		JLabel lblSalida = new JLabel("Salida:");
		lblSalida.setBounds(12, 80, 112, 15);
		panel.add(lblSalida);

		textAreaSalida = new JTextArea();
		textAreaSalida.setBounds(12, 107, 368, 208);
		textAreaSalida.setLineWrap(true);
		textAreaSalida.setEditable(false);
		panel.add(textAreaSalida);

//		final JScrollPane scroll = new JScrollPane(textAreaSalida);

	

		// Redirige la salida estandar al area de texto
		MessageConsole mc = new MessageConsole(textAreaSalida);
		
		textField = new JTextField();
		textField.setBounds(85, 6, 289, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblServidor = new JLabel("Servidor:");
		lblServidor.setBounds(12, 11, 112, 15);
		panel.add(lblServidor);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
	}
}
