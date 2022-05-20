package org.uem.dam.GuiaMichelin.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.uem.dam.GuiaMichelin.persist.DBPersistence;
import org.uem.dam.GuiaMichelin.view.MainView;

public class Controller implements ActionListener {

	private MainView mainView;
	private DBPersistence persistence;

	public Controller(MainView mainView) {
		this.mainView = mainView;
		this.persistence = new DBPersistence();
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

	/* General action parsers */

	private void parseCallerIDAction(String callerID, String action) {
		switch (callerID) {
		case "": {

			break;
		}
		default:
			throw new IllegalArgumentException("Unnasinged ID action: " + action);
		}
	}

	private void parseGenericAction(String action) {
		switch (action.toLowerCase()) {
		case "exit": {
			mainView.requestExitAction();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	/* Special action parsers */


}
