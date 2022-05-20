package org.uem.dam.GuiaMichelin.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.model.ComponentView;

import net.miginfocom.swing.MigLayout;

public class MainView extends JFrame implements ComponentView {

	private Controller controller;
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
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

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
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// update
		repaint();

	}

	@Override
	public void updateListeners(Controller controller) {
		// TODO Auto-generated method stub

	}

	public void setController(Controller controller) {
		this.controller = controller;
		exitMntm.addActionListener(controller);
		// initialize controller dependent elements
		consultaPanel.initAttributes();
	}

	public void setSubmenuView(JPanel submenu) {
		rootPane.setViewportView(submenu);
	}

	public Controller getController() {
		return controller;
	}

	public void requestExitAction() {
		if (JOptionPane.showConfirmDialog(this, "Se va a cerrar el programa, ¿confirmar?", "Confirmar cierre",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(NORMAL);
		}
	}

}
