package org.uem.dam.GuiaMichelin.view;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.inter.ComponentView;
import org.uem.dam.GuiaMichelin.model.Restaurante;

public class ModificarPanel extends RestaurantePanel implements ComponentView {
	private JButton btnSearchRest;
	private int focusedId;
	private boolean editable;

	public ModificarPanel() {
		setEditable(false);
	}

	@Override
	public void initComponents() {
		super.initComponents();
		btnSearchRest = new JButton("Buscar restaurante");
		panel.add(btnSearchRest, "cell 2 0 2 1,growx");
	}

	@Override
	public void initAttributes() {
		panel.setBorder(
				new TitledBorder(null, "Modificar Restaurante", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnAcceptData.setText("Actualizar datos");
		btnAcceptData.putClientProperty("CallerID", "modificarPanel");
		btnCancelData.setText("Cancelar");
		btnCancelData.putClientProperty("CallerID", "modificarPanel");
		btnSearchRest.putClientProperty("CallerID", "modificarPanel");
	}

	@Override
	public void updateListeners(Controller controller) {
		super.updateListeners(controller);
		btnSearchRest.addActionListener(controller);
	}

	public int getFocusedId() {
		return focusedId;
	}

	public void setEditable(boolean value) {
		editable = value;
		cocinaCmbx.setEnabled(value);
		regionCmbx.setEnabled(value);
		ciudadTxt.setEnabled(value);
		dirTxt.setEnabled(value);
		distinSpn.setEnabled(value);
		precMinTxt.setEnabled(value);
		precMaxTxt.setEnabled(value);
		telTxt.setEnabled(value);
		webTxt.setEnabled(value);
	}

	public boolean getEditable() {
		return editable;
	}

	public void setValues(Restaurante restaurante) {
		// maintain reference to id owning the original properties
		focusedId = restaurante.id();
		nomTxt.setText(restaurante.nombre());
		cocinaCmbx.getModel().setSelectedItem(restaurante.cocina());
		regionCmbx.getModel().setSelectedItem(restaurante.region());
		ciudadTxt.setText(restaurante.ciudad());
		dirTxt.setText(restaurante.direccion());
		distinSpn.setValue(restaurante.distincion());
		precMinTxt.setText(Float.toString(restaurante.precioMin()));
		precMaxTxt.setText(Float.toString(restaurante.precioMax()));
		telTxt.setText(restaurante.telefono());
		webTxt.setText(restaurante.web());
	}

	public String getSearchRestaurante() {
		return nomTxt.getText();
	}
}
