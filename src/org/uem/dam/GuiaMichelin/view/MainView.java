package org.uem.dam.GuiaMichelin.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.model.ComponentView;

import net.miginfocom.swing.MigLayout;

public class MainView extends JFrame implements ComponentView {

//	private Controller controller;
	private JMenuBar menuBar;
	private JMenu mantenimientoMn;
	private JMenuItem exitMntm;

	private ConsultaPanel consultaPanel;
	private JMenuItem mntmConsulta;
	private JMenuItem mntmRegistro;
	private JMenuItem mntmModificacion;

	public MainView() {
		initComponents();
		initAttributes();
	}

	@Override
	public void initComponents() {
		getContentPane().setLayout(new MigLayout("", "[grow,center]", "[grow,center]"));

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mantenimientoMn = new JMenu("Mantenimiento Restaurantes");
		menuBar.add(mantenimientoMn);

		mntmConsulta = new JMenuItem("Consulta de Restaurantes");
		mantenimientoMn.add(mntmConsulta);

		mntmRegistro = new JMenuItem("Registro de Restaurante");
		mantenimientoMn.add(mntmRegistro);

		mntmModificacion = new JMenuItem("Modificación de Restaurante");
		mantenimientoMn.add(mntmModificacion);

		exitMntm = new JMenuItem("Exit");
		menuBar.add(exitMntm);

		consultaPanel = new ConsultaPanel();
		rootPane.add(consultaPanel);
	}

	@Override
	public void initAttributes() {
		this.setVisible(true);
		this.setTitle("Gestor de Base de Datos");
		this.setSize(new Dimension(336, 312));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // I want to manually manipulate the closing operation
	}

	@Override
	public void updateListeners(Controller controller) {
		exitMntm.addActionListener(controller);
		// initialize controller dependent elements
		consultaPanel.updateListeners(controller);
		consultaPanel.initAttributes();
		this.addWindowListener(controller.getWinAdapter());
	}

	public ConsultaPanel getConsultaPanel() {
		return consultaPanel;
	}

	public void setSubmenuView(JPanel submenu) {
		this.setContentPane(submenu);
		this.repaint();
	}

}
