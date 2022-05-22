package org.uem.dam.GuiaMichelin.view;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.except.EmptyStringFieldException;
import org.uem.dam.GuiaMichelin.except.IllegalIntegerRangeException;
import org.uem.dam.GuiaMichelin.inter.ComponentView;
import org.uem.dam.GuiaMichelin.model.Restaurante;
import org.uem.dam.GuiaMichelin.utils.WindowActionUtils;

import net.miginfocom.swing.MigLayout;

public class RegistroPanel extends JPanel implements ComponentView {
	private JTextField nomTxt;
	private JTextField ciudadTxt;
	private JTextField dirTxt;
	private JTextField precMinTxt;
	private JTextField precMaxTxt;
	private JTextField telTxt;
	private JTextField webTxt;
	private JButton btnGuardarDatos;
	private JButton btnLimpiarDatos;
	private JSpinner distinSpn;
	private JComboBox regionCmbx;
	private JComboBox cocinaCmbx;

	public RegistroPanel() {
		initComponents();
		initAttributes();
	}

	@Override
	public void updateListeners(Controller controller) {
		btnGuardarDatos.addActionListener(controller);
		btnLimpiarDatos.addActionListener(controller);

	}

	@Override
	public void initComponents() {
		setLayout(new MigLayout("", "[grow]", "[grow]"));

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Registrar Restaurante", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[right][grow][][grow,left]", "[][][][][][][grow,bottom]"));

		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre, "cell 0 0,alignx trailing");

		nomTxt = new JTextField();
		panel.add(nomTxt, "flowx,cell 1 0,growx");
		nomTxt.setColumns(10);

		JLabel lblCocina = new JLabel("Cocina");
		panel.add(lblCocina, "cell 2 0");

		cocinaCmbx = new JComboBox();
		cocinaCmbx.setModel(
				new DefaultComboBoxModel(new String[] { "Creativa", "Moderna", "Tradicional", "Regional", "Fusión" }));
		panel.add(cocinaCmbx, "cell 3 0");

		JLabel lblRegin = new JLabel("Región");
		panel.add(lblRegin, "cell 0 1,alignx trailing");

		regionCmbx = new JComboBox();
		regionCmbx.setModel(new DefaultComboBoxModel(new String[] { "Andalucía", "Aragón", "Asturias", "Islas Baleares",
				"Cantabria", "Islas Canarias", "Castilla - La Mancha", "Castilla y León", "Cataluña", "Galicia",
				"Extremadura", "Madrid", "Murcia", "Navarra", "País Vasco", "La Rioja", "Comunidad Valenciana" }));
		panel.add(regionCmbx, "cell 1 1,growx");

		JLabel lblCiudad = new JLabel("Ciudad");
		panel.add(lblCiudad, "cell 2 1,alignx trailing");

		ciudadTxt = new JTextField();
		panel.add(ciudadTxt, "cell 3 1,growx");
		ciudadTxt.setColumns(10);

		JLabel lblDireccin = new JLabel("Dirección");
		panel.add(lblDireccin, "cell 0 2,alignx trailing");

		dirTxt = new JTextField();
		panel.add(dirTxt, "cell 1 2 3 1,growx");
		dirTxt.setColumns(10);

		JLabel lblDistincin = new JLabel("Distinción");
		panel.add(lblDistincin, "cell 0 3");

		distinSpn = new JSpinner();
		distinSpn.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		panel.add(distinSpn, "cell 1 3");

		JPanel precPnl = new JPanel();
		precPnl.setBorder(new TitledBorder(null, "Precio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(precPnl, "cell 0 4 4 1,grow");
		precPnl.setLayout(new MigLayout("", "[][grow][][grow]", "[]"));

		JLabel lblMnimo = new JLabel("Mínimo");
		precPnl.add(lblMnimo, "cell 0 0,alignx trailing");

		precMinTxt = new JTextField();
		precPnl.add(precMinTxt, "cell 1 0,growx");
		precMinTxt.setColumns(10);

		JLabel lblMximo = new JLabel("Máximo");
		precPnl.add(lblMximo, "cell 2 0,alignx trailing");

		precMaxTxt = new JTextField();
		precPnl.add(precMaxTxt, "cell 3 0,growx");
		precMaxTxt.setColumns(10);

		JLabel lblTelfono = new JLabel("Teléfono");
		panel.add(lblTelfono, "cell 0 5,alignx trailing");

		telTxt = new JTextField();
		panel.add(telTxt, "cell 1 5,growx");
		telTxt.setColumns(10);

		JLabel lblWeb = new JLabel("Web");
		panel.add(lblWeb, "cell 2 5,alignx trailing");

		webTxt = new JTextField();
		panel.add(webTxt, "cell 3 5,growx");
		webTxt.setColumns(10);

		JPanel btnPnl = new JPanel();
		btnPnl.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione una opci\u00F3n",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.add(btnPnl, "cell 0 6 4 1,growx,aligny bottom");
		btnPnl.setLayout(new MigLayout("", "[grow][grow,fill]", "[]"));

		btnGuardarDatos = new JButton("Guardar Datos");
		btnPnl.add(btnGuardarDatos, "cell 0 0,growx");

		btnLimpiarDatos = new JButton("Limpiar Datos");
		btnPnl.add(btnLimpiarDatos, "cell 1 0");

	}

	@Override
	public void initAttributes() {
		btnGuardarDatos.putClientProperty("CallerID", "registroPanel");
		btnLimpiarDatos.putClientProperty("CallerID", "registroPanel");

	}

	public Restaurante genRestaurante() {
		Restaurante restaurante = null;
		try {
			validateFields();
			restaurante = new Restaurante(-1, // SQL query will assign the proper next value to the register
					(int) distinSpn.getValue(),
					(!precMinTxt.getText().isEmpty() || !precMinTxt.getText().isBlank())
					? Integer.parseInt(precMinTxt.getText())
							: 0,
							(!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank())
							? Integer.parseInt(precMaxTxt.getText())
									: 0,
									nomTxt.getText(), (String) regionCmbx.getModel().getSelectedItem(), ciudadTxt.getText(),
									dirTxt.getText(), (String) cocinaCmbx.getModel().getSelectedItem(), telTxt.getText(),
									webTxt.getText());
		} catch (EmptyStringFieldException strExcept) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					String.format("El campo %s no ha sido introducido", strExcept.getMessage().toLowerCase()),
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException numExcept) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					"El precio minimo y máximo deben ser valores numéricos", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalIntegerRangeException invalidExcept) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					String.format("El valor máximo %s no puede ser menor que el valor mínimo %s",
							invalidExcept.getMaxVal(), invalidExcept.getMinVal()),
					JOptionPane.ERROR_MESSAGE);
		}

		return restaurante;
	}

	public void clearFields() {
		regionCmbx.setSelectedIndex(0);
		cocinaCmbx.setSelectedIndex(0);
		distinSpn.setValue(((SpinnerNumberModel) distinSpn.getModel()).getMinimum());
		nomTxt.setText("");
		ciudadTxt.setText("");
		dirTxt.setText("");
		precMinTxt.setText("");
		precMaxTxt.setText("");
		telTxt.setText("");
		webTxt.setText("");
	}

	private void validateFields() throws EmptyStringFieldException, IllegalIntegerRangeException {
		if (nomTxt.getText().isEmpty() || nomTxt.getText().isBlank()) {
			throw new EmptyStringFieldException("Nombre");
		}
		if (ciudadTxt.getText().isEmpty() || ciudadTxt.getText().isBlank()) {
			throw new EmptyStringFieldException("Ciudad");
		}

		if (!precMinTxt.getText().isEmpty() || !precMinTxt.getText().isBlank()) {
			int precMin = Integer.parseInt(precMinTxt.getText());
			if (!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank()) {
				int precMax = Integer.parseInt(precMaxTxt.getText());
				if (precMax < precMin) {
					throw new IllegalIntegerRangeException(precMin, precMax);
				}
			}
		} else if (!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank()) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					"El campo precio máximo ha sido introducido,\npero el mínimo no.\nPara registrar un precio máximo, por favor,\nasigne un valor al precio mínimo",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
