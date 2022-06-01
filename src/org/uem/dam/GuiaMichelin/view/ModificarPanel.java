package org.uem.dam.GuiaMichelin.view;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.inter.ComponentView;

public class ModificarPanel extends RestaurantePanel implements ComponentView {
	private JButton btnSearchRest;

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

	public void setEditable(boolean value) {
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

}
