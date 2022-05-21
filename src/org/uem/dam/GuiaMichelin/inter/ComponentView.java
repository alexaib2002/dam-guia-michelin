package org.uem.dam.GuiaMichelin.inter;

import org.uem.dam.GuiaMichelin.control.Controller;

public interface ComponentView {

	public void updateListeners(Controller controller);

	public void initComponents();

	public void initAttributes();

}
