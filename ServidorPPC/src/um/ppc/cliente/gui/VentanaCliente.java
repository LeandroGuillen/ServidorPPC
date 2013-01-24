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

import um.ppc.cliente.Cliente;
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
	private Cliente cliente;
	private JTextField textFieldTextoAFirmar;

	/**
	 * Create the application.
	 */
	public VentanaCliente(Cliente cliente) {
		this.cliente = cliente;
		initialize();
		frmClienteXML.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClienteXML = new JFrame();
		frmClienteXML.setTitle("Cliente " + cliente.getCodificacion().toString());
		frmClienteXML.setResizable(false);
		frmClienteXML.setBounds(100, 100, 406, 354);
		frmClienteXML.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClienteXML.getContentPane().setLayout(new BoxLayout(frmClienteXML.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		frmClienteXML.getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("164px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("32px"),
				ColumnSpec.decode("57px"),
				ColumnSpec.decode("97px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("32px"),
				RowSpec.decode("77px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:pref"),}));
		
				btnSolicitar = new JButton("Solicitar");
				btnSolicitar.addActionListener(this);
				
				JLabel lblTextoAFirmar = new JLabel("Texto a firmar:");
				panel.add(lblTextoAFirmar, "2, 6, left, default");
				
				textFieldTextoAFirmar = new JTextField();
				textFieldTextoAFirmar.setText("hola");
				panel.add(textFieldTextoAFirmar, "3, 6, 4, 1, fill, default");
				textFieldTextoAFirmar.setColumns(10);
				panel.add(btnSolicitar, "6, 12, fill, center");

		JLabel lblSalida = new JLabel("Salida:");
		panel.add(lblSalida, "2, 15, fill, bottom");

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 16, 5, 1, fill, fill");

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
		textFieldServidor.setText("192.168.1.129");
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
		panel.add(btnCerrar, "6, 18, fill, center");
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
			String textoAFirmar = textFieldTextoAFirmar.getText();
			System.out.println("Enviando solicitud de firma " + objCripto + " a " + direccionServidor + " ...");
			try {
				cliente.realizarSolicitud(direccionServidor, objCripto, textoAFirmar);
			} catch (UnknownHostException e1) {
				System.out.println("Error: Host desconocido.");
			} catch (IOException e1) {
				System.out.println("Error: No se puede conectar con el servidor.");
			}
		}
	}
}
