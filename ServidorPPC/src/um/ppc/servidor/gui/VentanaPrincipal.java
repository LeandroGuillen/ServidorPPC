package um.ppc.servidor.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.servidor.Control;
import um.ppc.servidor.gui.util.MessageConsole;

public class VentanaPrincipal {

	private JFrame frmServidorPpc;
	private Control control;
	private JComboBox comboBoxModo;
	private JTextArea textAreaSalida;

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		control = new Control();
		initialize();
		frmServidorPpc.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServidorPpc = new JFrame();
		frmServidorPpc.setTitle("Servidor PPC");
		frmServidorPpc.setResizable(false);
		frmServidorPpc.setBounds(100, 100, 406, 354);
		frmServidorPpc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServidorPpc.getContentPane().setLayout(new BoxLayout(frmServidorPpc.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		frmServidorPpc.getContentPane().add(panel);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(12, 7, 112, 25);
		btnIniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Codificacion codificacion = Codificacion.valueOf((String) comboBoxModo.getSelectedItem());
				control.iniciarServidor(codificacion);
			}
		});
		panel.setLayout(null);
		panel.add(btnIniciar);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(136, 7, 106, 25);
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.pararServidor();
			}
		});
		panel.add(btnParar);

		JButton btnCerrar = new JButton("Salir");
		btnCerrar.setBounds(299, 7, 91, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btnCerrar);

		JLabel lblModo = new JLabel("Modo:");
		lblModo.setBounds(12, 47, 38, 15);
		panel.add(lblModo);

		comboBoxModo = new JComboBox();
		comboBoxModo.setBounds(68, 42, 164, 24);
		List<String> codificaciones = new ArrayList<String>();
		for (Codificacion c : Codificacion.values()) {
			codificaciones.add(c.toString());
		}
		comboBoxModo.setModel(new DefaultComboBoxModel(codificaciones.toArray()));
		panel.add(comboBoxModo);

		JLabel lblSalida = new JLabel("Salida:");
		lblSalida.setBounds(12, 80, 40, 15);
		panel.add(lblSalida);

		textAreaSalida = new JTextArea();
		textAreaSalida.setBounds(12, 107, 368, 208);
		textAreaSalida.setLineWrap(true);
		textAreaSalida.setEditable(false);
		panel.add(textAreaSalida);

//		final JScrollPane scroll = new JScrollPane(textAreaSalida);

	

		// Redirige la salida estandar al area de texto
		MessageConsole mc = new MessageConsole(textAreaSalida);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
	}
}
