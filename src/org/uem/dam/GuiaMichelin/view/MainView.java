package org.uem.dam.GuiaMichelin.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.model.ComponentView;

import net.miginfocom.swing.MigLayout;

public class MainView extends JFrame implements ComponentView {

//	private Controller controller;
	private JMenuBar menuBar;
	private JMenu mantenimientoMn;
	private JMenuItem exitMntm;

	private ConsultaPanel consultaPanel;
	private JScrollPane rootPane;
	private JMenuItem mntmConsulta;
	private JMenuItem mntmRegistro;
	private JMenuItem mntmModificacion;

	public MainView() {
		initComponents();
		initAttributes();
		setSubmenuView(consultaPanel); // always want to start on login
	}

	@Override
	public void initComponents() {
		getContentPane().setLayout(new MigLayout("", "[grow,center]", "[grow,center]"));

		rootPane = new JScrollPane();
		getContentPane().add(rootPane, "cell 0 0,grow");

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

		// pack the view and set it as minimum size
		this.pack();
		this.setMinimumSize(getSize());
		this.setSize(new Dimension(336, 312));

		// set main view content
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // I want to manually manipulate the closing operation

		// update
		repaint();
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
		rootPane.setViewportView(submenu);
	}

}
