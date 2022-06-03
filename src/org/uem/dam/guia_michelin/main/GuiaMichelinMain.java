package org.uem.dam.guia_michelin.main;

import org.uem.dam.guia_michelin.control.Controller;
import org.uem.dam.guia_michelin.view.MainView;

public class GuiaMichelinMain {

	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(() -> {
			MainView mainView = new MainView();
			Controller mainController = new Controller(mainView);
			mainView.updateListeners(mainController);
			mainView.setSubmenuView(mainView.getConsultaPanel()); // always want to start on login
			mainView.setVisible(true);
		});

	}

}
