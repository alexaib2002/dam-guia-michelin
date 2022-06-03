package org.uem.dam.guia_michelin.view;

import javax.swing.border.TitledBorder;

import org.uem.dam.guia_michelin.inter.ComponentView;

public class RegistroPanel extends RestaurantePanel implements ComponentView {

	public RegistroPanel() {
		super();
	}

	@Override
	public void initAttributes() {
		panel.setBorder(
				new TitledBorder(null, "Registrar Restaurante", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnAcceptData.setText("Guardar datos");
		btnAcceptData.putClientProperty("CallerID", "registroPanel");
		btnCancelData.setText("Limpiar campos");
		btnCancelData.putClientProperty("CallerID", "registroPanel");
	}

}
