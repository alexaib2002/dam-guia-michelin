package org.uem.dam.GuiaMichelin.utils;

import java.awt.Window;

import javax.swing.JOptionPane;

public class WindowActionUtils {
	public static boolean promptWindowExit(Window window) {
		return (JOptionPane.showConfirmDialog(window, "Se va a cerrar el programa, ¿confirmar?", "Confirmar cierre",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION);
	}

	public static void onExitEvent(Window window) {
		if (WindowActionUtils.promptWindowExit(window)) {
			window.dispose();
		}
	}

	public static void promptInfoDialog(Window window, String mssg, int icon) {
		JOptionPane.showMessageDialog(window, mssg, "Información del sistema gestor", icon);
	}
}
