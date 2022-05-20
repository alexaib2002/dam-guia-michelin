package org.uem.dam.GuiaMichelin.main;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.view.MainView;

public class GuiaMichelinMain {

	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(() -> {
			MainView mainView = new MainView();
			Controller mainController = new Controller(mainView);
			mainView.updateListeners(mainController);
			mainView.setSubmenuView(mainView.getConsultaPanel()); // always want to start on login
		});

	}

}
