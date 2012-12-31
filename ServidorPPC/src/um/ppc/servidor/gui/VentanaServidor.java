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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.servidor.Control;
import um.ppc.util.MessageConsole;

public class VentanaServidor {

	private JFrame frmServidorPpc;
	private Control control;
	private JComboBox<String> comboBoxModo;
	private JTextArea textAreaSalida;
	private JButton btnIniciar;
	private JButton btnParar;

	/**
	 * Create the application.
	 */
	public VentanaServidor() {
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

		btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(12, 7, 112, 25);
		btnIniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Codificacion codificacion = Codificacion.valueOf((String) comboBoxModo.getSelectedItem());
				control.iniciarServidor(codificacion);
				btnIniciar.setEnabled(false);
				btnParar.setEnabled(true);
				comboBoxModo.setEnabled(false);
			}
		});
		panel.setLayout(null);
		panel.add(btnIniciar);

		btnParar = new JButton("Parar");
		btnParar.setEnabled(false);
		btnParar.setBounds(136, 7, 106, 25);
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.pararServidor();
				btnIniciar.setEnabled(true);
				btnParar.setEnabled(false);
				comboBoxModo.setEnabled(true);
			}
		});
		panel.add(btnParar);

		JButton btnCerrar = new JButton("Salir");
		btnCerrar.setBounds(289, 7, 91, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btnCerrar);

		JLabel lblModo = new JLabel("Modo:");
		lblModo.setBounds(12, 47, 112, 15);
		panel.add(lblModo);

		comboBoxModo = new JComboBox<String>();
		comboBoxModo.setBounds(146, 44, 234, 24);
		List<String> codificaciones = new ArrayList<String>();
		for (Codificacion c : Codificacion.values()) {
			codificaciones.add(c.toString());
		}
		comboBoxModo.setModel(new DefaultComboBoxModel(codificaciones.toArray()));
		panel.add(comboBoxModo);

		JLabel lblSalida = new JLabel("Salida:");
		lblSalida.setBounds(12, 80, 112, 15);
		panel.add(lblSalida);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 107, 368, 208);
		panel.add(scrollPane);

		textAreaSalida = new JTextArea();
		scrollPane.setViewportView(textAreaSalida);
		textAreaSalida.setLineWrap(true);
		textAreaSalida.setEditable(false);

		// Redirige la salida estandar al area de texto
		MessageConsole mc = new MessageConsole(textAreaSalida);
		
		JButton btnLimpiarSalida = new JButton("Limpiar salida");
		btnLimpiarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaSalida.setText(null);
			}
		});
		btnLimpiarSalida.setBounds(220, 75, 160, 25);
		panel.add(btnLimpiarSalida);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
	}
}
