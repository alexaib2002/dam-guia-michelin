package org.uem.dam.GuiaMichelin.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.uem.dam.GuiaMichelin.control.Controller;
import org.uem.dam.GuiaMichelin.model.ComponentView;
import org.uem.dam.GuiaMichelin.model.Restaurante;
import org.uem.dam.GuiaMichelin.persist.DBPersistence;
import org.uem.dam.GuiaMichelin.utils.Utils;

import net.miginfocom.swing.MigLayout;

public class ConsultaPanel extends JPanel implements ComponentView {

	private JTable listTable;
	private JButton consultBtn;
	private JComboBox regionCmbx;
	private JComboBox distinCmbx;
	private JButton remBtn;

	public ConsultaPanel() {
		initComponents();
		initAttributes();
	}

	@Override
	public void updateListeners(Controller controller) {
		consultBtn.addActionListener(controller);
		remBtn.addActionListener(controller);

		DBPersistence persistence = controller.getPersistence();
		DefaultComboBoxModel<String> cmbxModelRegion = (DefaultComboBoxModel<String>) regionCmbx.getModel();
		for (String region : persistence.getAvailableRegions()) {
			cmbxModelRegion.addElement(region);
		}

		DefaultTableModel tableModel = new DefaultTableModel(persistence.getAvailableColumns().toArray(), 0);
		listTable.setModel(tableModel);

	}

	@Override
	public void initComponents() {
		setLayout(new MigLayout("", "[186px,grow][]", "[15px][][grow,fill][]"));

		JLabel lblConsultaDeRestaurantes = new JLabel("Consulta de Restaurantes");
		add(lblConsultaDeRestaurantes, "cell 0 0,alignx left,aligny top");

		JPanel filtroPnl = new JPanel();
		filtroPnl.setBorder(new TitledBorder(null, "Filtro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		filtroPnl.setToolTipText("");
		add(filtroPnl, "cell 0 1,grow");
		filtroPnl.setLayout(new MigLayout("", "[][grow][][grow]", "[][]"));

		JLabel lblRegin = new JLabel("Región");
		filtroPnl.add(lblRegin, "cell 0 0,alignx trailing");

		regionCmbx = new JComboBox();
		regionCmbx.setModel(new DefaultComboBoxModel(new String[] { "TODAS" }));
		filtroPnl.add(regionCmbx, "cell 1 0,growx");

		JLabel lblDistincin = new JLabel("Distinción");
		filtroPnl.add(lblDistincin, "cell 2 0,alignx trailing");

		distinCmbx = new JComboBox();
		distinCmbx.setModel(
				new DefaultComboBoxModel(new String[] { "TODAS", "1 estrella", "2 estrellas", "3 estrellas" }));
		filtroPnl.add(distinCmbx, "cell 3 0,growx");

		consultBtn = new JButton("Consultar");
		filtroPnl.add(consultBtn, "cell 0 1 4 1,growx,aligny center");

		JPanel tablaPnl = new JPanel();
		tablaPnl.setBorder(
				new TitledBorder(null, "Listado de restaurantes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tablaPnl, "cell 0 2,grow");
		tablaPnl.setLayout(new MigLayout("", "[grow]", "[][]"));

		JScrollPane scrollPane = new JScrollPane();
		tablaPnl.add(scrollPane, "cell 0 0,grow");

		listTable = new JTable() {
			// disable editable cells
			@Override
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}
		};
		listTable.setFillsViewportHeight(true);
		listTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listTable.setCellSelectionEnabled(true);
		scrollPane.setViewportView(listTable);

		remBtn = new JButton("Eliminar");
		remBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		tablaPnl.add(remBtn, "cell 0 1,alignx right");
	}

	@Override
	public void initAttributes() {
		consultBtn.putClientProperty("CallerID", "consultaPanel");
		remBtn.putClientProperty("CallerID", "consultaPanel");
	}

	public JComboBox getRegionCmbx() {
		return regionCmbx;
	}

	public JComboBox getDistinCmbx() {
		return distinCmbx;
	}

	public JButton getConsultBtn() {
		return consultBtn;
	}

	public void updateTable(ArrayList<Restaurante> restaurantes) {
		DefaultTableModel tableModel = (DefaultTableModel) listTable.getModel();
		Object[] restauranteProps;
		int distinIdx = Restaurante.PROPS.DISTINCION.ordinal();
		for (Restaurante restaurante : restaurantes) {
			restauranteProps = restaurante.translateToStringArray().toArray();
			Utils.parseDistinIntToString(restauranteProps, distinIdx);
			tableModel.addRow(restauranteProps);
		}
	}

	public void clearTable() {
		DefaultTableModel tableModel = (DefaultTableModel) listTable.getModel();
		tableModel.setNumRows(0);
	}
}
