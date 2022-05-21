package org.uem.dam.GuiaMichelin.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.uem.dam.GuiaMichelin.model.Restaurante;
import org.uem.dam.GuiaMichelin.persist.DBPersistence;
import org.uem.dam.GuiaMichelin.utils.WindowActionUtils;
import org.uem.dam.GuiaMichelin.view.ConsultaPanel;
import org.uem.dam.GuiaMichelin.view.MainView;

public class Controller implements ActionListener {

	private final MainView mainView;
	private final DBPersistence persistence;
	private final WindowAdapter winAdapter;

	public Controller(MainView mainView) {
		this.mainView = mainView;
		this.persistence = new DBPersistence();
		this.winAdapter = new WindowAdapter() {
			// reference:
			// https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html#windowClosing(java.awt.event.WindowEvent)
			@Override
			public void windowClosing(WindowEvent e) {
				WindowActionUtils.onExitEvent(mainView);
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		String callerID = ((String) ((JComponent) event.getSource()).getClientProperty("CallerID"));
		if (callerID != null) { // there's a source type attached, so we can filter the caller
			parseCallerIDAction(callerID, action);
		} else { // recognized as generic source, as it wasn't named on its constructor
			parseGenericAction(action);
		}
	}

	public DBPersistence getPersistence() {
		return persistence;
	}

	public WindowAdapter getWinAdapter() {
		return winAdapter;
	}

	/* General action parsers */

	private void parseCallerIDAction(String callerID, String action) {
		switch (callerID) {
		case "mainView": {
			switchMainAction(action);
			break;
		}
		case "consultaPanel": {
			switchConsultaAction(action);
			break;
		}
		case "registroPanel": {
			switchRegistroAction(action);
			break;
		}
		default:
			throw new IllegalArgumentException("Unnasinged ID action: " + action);
		}
	}

	private void parseGenericAction(String action) {
		switch (action.toLowerCase()) {
		case "exit": {
			WindowActionUtils.onExitEvent(mainView);
			break;
		}
		default:
			throw new IllegalArgumentException("Valor inesperado: " + action);
		}
	}

	/* Nested switch cases */

	private void switchMainAction(String action) {
		switch (action.toLowerCase()) {
		case "consulta de restaurantes": {
			mainView.setSubmenuView(mainView.getConsultaPanel());
		}
			break;
		case "registro de restaurante": {
			mainView.setSubmenuView(mainView.getRegistroPanel());
		}
			break;
		case "modificación de restaurante": {
			mainView.setSubmenuView(null);
		}
			break;
		}
	}

	private void switchConsultaAction(String action) {
		switch (action.toLowerCase()) {
		case "consultar": {
			// TODO refactor into own method
			ConsultaPanel consultaPanel = mainView.getConsultaPanel();
			consultaPanel.clearTable();
			ArrayList<Restaurante> restaurantes = persistence.getRestaurantes();
			String regionFilter = (String) consultaPanel.getRegionCmbx().getSelectedItem();
			// TODO create test case to ensure distinFilter always matches selected distin
			int distinFilter = consultaPanel.getDistinCmbx().getSelectedIndex();
			// filter table results based on chosen options
			if (!regionFilter.equals("TODAS")) {
				restaurantes.removeIf(restaurante -> (!restaurante.region().equals(regionFilter)));
			}
			if (distinFilter != 0) {
				restaurantes.removeIf(restaurante -> (restaurante.distincion() != distinFilter));
			}
			// send the final array
			consultaPanel.updateTable(restaurantes);
			if (restaurantes.isEmpty()) {
				WindowActionUtils.promptInfoDialog(mainView, "No se han encontrado datos",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		}
		case "eliminar": {
			System.err.println("Eliminación no implementada");
			break;
		}
		}
	}

	private void switchRegistroAction(String action) {
		switch (action.toLowerCase()) {
		case "guardar datos": {
			Restaurante restaurante = mainView.getRegistroPanel().genRestaurante();
			persistence.addRestaurante(restaurante);
			break;
		}
		case "limpiar datos": {
			mainView.getRegistroPanel().clearFields();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	/* Special action parsers */

}
