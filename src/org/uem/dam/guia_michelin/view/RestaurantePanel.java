package org.uem.dam.guia_michelin.view;

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

import org.uem.dam.guia_michelin.control.Controller;
import org.uem.dam.guia_michelin.except.EmptyStringFieldException;
import org.uem.dam.guia_michelin.except.IllegalFloatRangeException;
import org.uem.dam.guia_michelin.inter.ComponentView;
import org.uem.dam.guia_michelin.model.Restaurante;
import org.uem.dam.guia_michelin.utils.WindowActionUtils;

import net.miginfocom.swing.MigLayout;

public class RestaurantePanel extends JPanel implements ComponentView {
	protected JPanel panel;
	protected JTextField nomTxt;
	protected JTextField ciudadTxt;
	protected JTextField dirTxt;
	protected JTextField precMinTxt;
	protected JTextField precMaxTxt;
	protected JTextField telTxt;
	protected JTextField webTxt;
	protected JSpinner distinSpn;
	protected JComboBox regionCmbx;
	protected JComboBox cocinaCmbx;

	protected JButton btnAcceptData;
	protected JButton btnCancelData;
	private JButton btnSearchRest;

	public RestaurantePanel() {
		initComponents();
		initAttributes();
	}

	@Override
	public void updateListeners(Controller controller) {
		btnAcceptData.addActionListener(controller);
		btnCancelData.addActionListener(controller);
	}

	@Override
	public void initComponents() {
		setLayout(new MigLayout("", "[grow]", "[grow]"));

		panel = new JPanel();
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[right][grow][][grow,left]", "[][][][][][][][grow,bottom]"));

		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre, "cell 0 0,alignx trailing");

		nomTxt = new JTextField();
		panel.add(nomTxt, "flowx,cell 1 0,growx");
		nomTxt.setColumns(10);

		JLabel lblCocina = new JLabel("Cocina");
		panel.add(lblCocina, "cell 0 1");

		cocinaCmbx = new JComboBox();
		cocinaCmbx.setModel(
				new DefaultComboBoxModel(new String[] { "Creativa", "Moderna", "Tradicional", "Regional", "Fusi??n" }));
		panel.add(cocinaCmbx, "cell 1 1");

		JLabel lblRegin = new JLabel("Regi??n");
		panel.add(lblRegin, "cell 0 2,alignx trailing");

		regionCmbx = new JComboBox();
		regionCmbx.setModel(new DefaultComboBoxModel(new String[] { "Andaluc??a", "Arag??n", "Asturias", "Islas Baleares",
				"Cantabria", "Islas Canarias", "Castilla - La Mancha", "Castilla y Le??n", "Catalu??a", "Galicia",
				"Extremadura", "Madrid", "Murcia", "Navarra", "Pa??s Vasco", "La Rioja", "Comunidad Valenciana" }));
		panel.add(regionCmbx, "cell 1 2,growx");

		JLabel lblCiudad = new JLabel("Ciudad");
		panel.add(lblCiudad, "cell 2 2,alignx trailing");

		ciudadTxt = new JTextField();
		panel.add(ciudadTxt, "cell 3 2,growx");
		ciudadTxt.setColumns(10);

		JLabel lblDireccin = new JLabel("Direcci??n");
		panel.add(lblDireccin, "cell 0 3,alignx trailing");

		dirTxt = new JTextField();
		panel.add(dirTxt, "cell 1 3 3 1,growx");
		dirTxt.setColumns(10);

		JLabel lblDistincin = new JLabel("Distinci??n");
		panel.add(lblDistincin, "cell 0 4");

		distinSpn = new JSpinner();
		distinSpn.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		panel.add(distinSpn, "cell 1 4");

		JPanel precPnl = new JPanel();
		precPnl.setBorder(new TitledBorder(null, "Precio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(precPnl, "cell 0 5 4 1,grow");
		precPnl.setLayout(new MigLayout("", "[][grow][][grow]", "[]"));

		JLabel lblMnimo = new JLabel("M??nimo");
		precPnl.add(lblMnimo, "cell 0 0,alignx trailing");

		precMinTxt = new JTextField();
		precPnl.add(precMinTxt, "cell 1 0,growx");
		precMinTxt.setColumns(10);

		JLabel lblMximo = new JLabel("M??ximo");
		precPnl.add(lblMximo, "cell 2 0,alignx trailing");

		precMaxTxt = new JTextField();
		precPnl.add(precMaxTxt, "cell 3 0,growx");
		precMaxTxt.setColumns(10);

		JLabel lblTelfono = new JLabel("Tel??fono");
		panel.add(lblTelfono, "cell 0 6,alignx trailing");

		telTxt = new JTextField();
		panel.add(telTxt, "cell 1 6,growx");
		telTxt.setColumns(10);

		JLabel lblWeb = new JLabel("Web");
		panel.add(lblWeb, "cell 2 6,alignx trailing");

		webTxt = new JTextField();
		panel.add(webTxt, "cell 3 6,growx");
		webTxt.setColumns(10);

		JPanel btnPnl = new JPanel();
		btnPnl.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Seleccione una opci\u00F3n",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.add(btnPnl, "cell 0 7 4 1,growx,aligny bottom");
		btnPnl.setLayout(new MigLayout("", "[grow][grow,fill]", "[]"));

		btnAcceptData = new JButton();
		btnPnl.add(btnAcceptData, "cell 0 0,growx");

		btnCancelData = new JButton();
		btnPnl.add(btnCancelData, "cell 1 0");

	}

	@Override
	public void initAttributes() {
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

	public Restaurante genRestaurante() {
		Restaurante restaurante = null;
		try {
			validateFields();
			restaurante = new Restaurante(-1, // SQL query will assign the proper next value to the register
					(int) distinSpn.getValue(),
					(!precMinTxt.getText().isEmpty() || !precMinTxt.getText().isBlank())
							? Float.parseFloat(precMinTxt.getText())
							: 0,
					(!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank())
							? Float.parseFloat(precMaxTxt.getText())
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
					"El precio minimo y m??ximo deben ser valores num??ricos", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalFloatRangeException invalidExcept) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					String.format("El valor m??ximo %s no puede ser menor que el valor m??nimo %s",
							invalidExcept.getMaxVal(), invalidExcept.getMinVal()),
					JOptionPane.ERROR_MESSAGE);
		}

		return restaurante;
	}

	private void validateFields() throws EmptyStringFieldException, IllegalFloatRangeException {
		if (nomTxt.getText().isEmpty() || nomTxt.getText().isBlank()) {
			throw new EmptyStringFieldException("Nombre");
		}
		if (ciudadTxt.getText().isEmpty() || ciudadTxt.getText().isBlank()) {
			throw new EmptyStringFieldException("Ciudad");
		}

		if (!precMinTxt.getText().isEmpty() || !precMinTxt.getText().isBlank()) {
			float precMin = Float.parseFloat(precMinTxt.getText());
			if (!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank()) {
				float precMax = Float.parseFloat(precMaxTxt.getText());
				if (precMax < precMin) {
					throw new IllegalFloatRangeException(precMin, precMax);
				}
			}
		} else if (!precMaxTxt.getText().isEmpty() || !precMaxTxt.getText().isBlank()) {
			WindowActionUtils.promptInfoDialog(SwingUtilities.getWindowAncestor(this),
					"El campo precio m??ximo ha sido introducido,\npero el m??nimo no.\nPara registrar un precio m??ximo, por favor,\nasigne un valor al precio m??nimo",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
