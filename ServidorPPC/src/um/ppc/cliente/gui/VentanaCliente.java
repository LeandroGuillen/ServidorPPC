package um.ppc.cliente.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
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
import javax.swing.JTextField;

import um.ppc.cliente.Control;
import um.ppc.protocolo.enumerados.Codificacion;
import um.ppc.protocolo.enumerados.TipoObjetoCriptografico;
import um.ppc.util.MessageConsole;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class VentanaCliente implements ActionListener {

	private JFrame frmClienteXML;
	private JTextArea textAreaSalida;
	private JTextField textFieldServidor;
	private JButton btnSolicitar;
	private JComboBox comboBoxObjCripto;

	/**
	 * Create the application.
	 */
	public VentanaCliente() {
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
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("164px"), FormFactory.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("32px"),
				ColumnSpec.decode("57px"), ColumnSpec.decode("97px"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("36px"), RowSpec.decode("70px"), RowSpec.decode("25px"),
						RowSpec.decode("32px"), RowSpec.decode("77px"), FormFactory.UNRELATED_GAP_ROWSPEC, RowSpec.decode("fill:pref"), }));

		btnSolicitar = new JButton("Solicitar");
		btnSolicitar.addActionListener(this);
		panel.add(btnSolicitar, "3, 5, 4, 1, fill, center");

		JLabel lblSalida = new JLabel("Salida:");
		panel.add(lblSalida, "2, 7, fill, bottom");

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 8, 5, 1, fill, fill");

		textAreaSalida = new JTextArea();
		scrollPane.setViewportView(textAreaSalida);
		textAreaSalida.setLineWrap(true);
		textAreaSalida.setEditable(false);

		// Redirige la salida estandar al area de texto
		MessageConsole mc = new MessageConsole(textAreaSalida);
		List<String> codificaciones = new ArrayList<String>();
		for (Codificacion c : Codificacion.values()) {
			codificaciones.add(c.toString());
		}

		textFieldServidor = new JTextField();
		textFieldServidor.setText("127.0.0.1");
		panel.add(textFieldServidor, "3, 2, 4, 1, fill, center");
		textFieldServidor.setColumns(10);

		JLabel lblServidor = new JLabel("Servidor:");
		panel.add(lblServidor, "2, 2, left, center");

		comboBoxObjCripto = new JComboBox();

		List<String> objetosCripto = new ArrayList<String>();
		for (TipoObjetoCriptografico t : TipoObjetoCriptografico.values()) {
			objetosCripto.add(t.toString());
		}
		comboBoxObjCripto.setModel(new DefaultComboBoxModel(objetosCripto.toArray()));
		panel.add(comboBoxObjCripto, "3, 4, 4, 1, fill, center");

		JLabel lblObjetoCriptogrfico = new JLabel("Objeto criptográfico:");
		panel.add(lblObjetoCriptogrfico, "2, 4, fill, center");

		JButton btnCerrar = new JButton("Salir");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btnCerrar, "6, 10, fill, center");
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Si la accion la origino el boton solicitar
		if (e.getSource() == btnSolicitar) {
			String direccionServidor = textFieldServidor.getText();
			TipoObjetoCriptografico objCripto = TipoObjetoCriptografico.valueOf((String) comboBoxObjCripto.getSelectedItem());
			System.out.println("Enviando solicitud de objeto " + objCripto + "a " + direccionServidor + " ...");
			try {
				Control.realizarSolicitud(direccionServidor, objCripto);
			} catch (UnknownHostException e1) {
				System.out.println("Error: Host desconocido.");
			} catch (IOException e1) {
				System.out.println("Error: Excepción de entrada/salida.");
			}
		}
	}
}
