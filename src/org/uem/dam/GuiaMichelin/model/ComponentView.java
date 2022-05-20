package org.uem.dam.GuiaMichelin.model;

import org.uem.dam.GuiaMichelin.control.Controller;

public interface ComponentView {

	public void updateListeners(Controller controller);

	public void initComponents();

	public void initAttributes();

}
