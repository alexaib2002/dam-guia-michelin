package org.uem.dam.GuiaMichelin.view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.uem.dam.GuiaMichelin.except.EmptyStringFieldException;
import org.uem.dam.GuiaMichelin.except.IllegalIntegerRangeException;
import org.uem.dam.GuiaMichelin.inter.ComponentView;
import org.uem.dam.GuiaMichelin.model.Restaurante;
import org.uem.dam.GuiaMichelin.utils.WindowActionUtils;

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

}
