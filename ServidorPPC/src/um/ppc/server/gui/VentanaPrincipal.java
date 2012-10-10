package um.ppc.server.gui;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import um.ppc.server.Servidor;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal {

	private JFrame frmServidorPpc;

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
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
		frmServidorPpc.getContentPane()
				.setLayout(
						new BoxLayout(frmServidorPpc.getContentPane(),
								BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		frmServidorPpc.getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("52px:grow"), ColumnSpec.decode("52px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("49px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("41px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("41px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("1px"), }, new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("30px"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Servidor.iniciar();
			}
		});
		panel.add(btnIniciar, "3, 2, 2, 1, fill, center");

		JButton btnParar = new JButton("Parar");
		panel.add(btnParar, "6, 2, 3, 1, fill, center");

		JButton btnCerrar = new JButton("Salir");
		panel.add(btnCerrar, "12, 2, 3, 1, fill, top");

		JLabel lblModo = new JLabel("Modo:");
		panel.add(lblModo, "3, 4, left, center");

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "ASN1",
				"XML", "JSON" }));
		panel.add(comboBox, "4, 4, 5, 1, fill, center");

		JLabel lblSalida = new JLabel("Salida:");
		panel.add(lblSalida, "3, 6, left, center");

		JTextArea textArea = new JTextArea();
		panel.add(textArea, "3, 8, 12, 17, fill, fill");
	}
}
