package org.uem.dam.guia_michelin.inter;

import org.uem.dam.guia_michelin.control.Controller;

public interface ComponentView {

	public void updateListeners(Controller controller);

	public void initComponents();

	public void initAttributes();

}
