package org.uem.dam.guia_michelin.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.uem.dam.guia_michelin.contract.RestauranteContract;
import org.uem.dam.guia_michelin.contract.TableContract;
import org.uem.dam.guia_michelin.model.Restaurante;
import org.uem.dam.guia_michelin.persist.DBPersistence;
import org.uem.dam.guia_michelin.utils.SQLQueryBuilder;
import org.uem.dam.guia_michelin.utils.WindowActionUtils;
import org.uem.dam.guia_michelin.view.ConsultaPanel;
import org.uem.dam.guia_michelin.view.MainView;
import org.uem.dam.guia_michelin.view.ModificarPanel;

public class Controller implements ActionListener {
	private final MainView mainView;
	private final DBPersistence persistence;
	private final WindowAdapter winAdapter;

	private ArrayList<Restaurante> restaurantes;

	public Controller(MainView mainView) {
		this.mainView = mainView;
		this.persistence = new DBPersistence();
		this.winAdapter = new WindowAdapter() {
			// reference:
			// https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html#windowClosing(java.awt.event.WindowEvent)
			@Override
			public void windowClosing(WindowEvent e) {
				WindowActionUtils.onExitEvent(mainView);
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		String callerID = ((String) ((JComponent) event.getSource()).getClientProperty("CallerID"));
		if (callerID != null) { // there's a source type attached, so we can filter the caller
			parseCallerIDAction(callerID, action);
		} else { // recognized as generic source, as it wasn't named on its constructor
			parseGenericAction(action);
		}
	}

	public DBPersistence getPersistence() {
		return persistence;
	}

	public WindowAdapter getWinAdapter() {
		return winAdapter;
	}

	/* General action parsers */

	private void parseCallerIDAction(String callerID, String action) {
		switch (callerID) {
		case "mainView": {
			switchMainAction(action);
			break;
		}
		case "consultaPanel": {
			switchConsultaAction(action);
			break;
		}
		case "registroPanel": {
			switchRegistroAction(action);
			break;
		}
		case "modificarPanel": {
			switchModificarAction(action);
			break;
		}
		default:
			throw new IllegalArgumentException("Unnasinged caller ID: " + callerID);
		}
	}

	private void parseGenericAction(String action) {
		switch (action.toLowerCase()) {
		case "exit": {
			WindowActionUtils.onExitEvent(mainView);
			break;
		}
		default:
			throw new IllegalArgumentException("Valor inesperado: " + action);
		}
	}

	/* Nested switch cases */

	private void switchMainAction(String action) {
		switch (action.toLowerCase()) {
		case "consulta de restaurantes": {
			mainView.setSubmenuView(mainView.getConsultaPanel());
		}
			break;
		case "registro de restaurante": {
			mainView.setSubmenuView(mainView.getRegistroPanel());
		}
			break;
		case "modificaci??n de restaurante": {
			mainView.setSubmenuView(mainView.getModificarPanel());
		}
			break;
		}
	}

	private void switchConsultaAction(String action) {
		switch (action.toLowerCase()) {
		case "consultar": {
			// TODO refactor into own method
			ConsultaPanel consultaPanel = mainView.getConsultaPanel();
			consultaPanel.clearTable();
			updateRestaurantes();
			String regionFilter = (String) consultaPanel.getRegionCmbx().getSelectedItem();
			// TODO create test case to ensure distinFilter always matches selected distin
			int distinFilter = consultaPanel.getDistinCmbx().getSelectedIndex();
			// filter table results based on chosen options
			if (!regionFilter.equals("TODAS")) {
				restaurantes.removeIf(restaurante -> (!restaurante.region().equals(regionFilter)));
			}
			if (distinFilter != 0) {
				restaurantes.removeIf(restaurante -> (restaurante.distincion() != distinFilter));
			}
			// send the final array
			consultaPanel.updateTable(restaurantes);
			if (restaurantes.isEmpty()) {
				WindowActionUtils.promptInfoDialog(mainView, "No se han encontrado datos",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		}
		case "eliminar": {
			ConsultaPanel consultaPanel = mainView.getConsultaPanel();
			int[] rowsSelected = mainView.getConsultaPanel().getSelectedIndexes();
			try {
				for (int i = rowsSelected[rowsSelected.length - 1]; i >= rowsSelected[0]; i--) {
					updateRestaurantes();
					consultaPanel.removeTableIndex(i);
					// https://stackoverflow.com/questions/34865383/variable-used-in-lambda-expression-should-be-final-or-effectively-final
					Restaurante restaurante = restaurantes.get(i); // lambda NO acepta variables mutables en el interior
																	// de la implementaci??n, deben ser finales
					if (persistence.updateRestaurante(restaurante, (con, pstmt) -> {
						String query = SQLQueryBuilder.buildDeleteQuery(TableContract.RESTAURANTES.toString(),
								RestauranteContract.ID.toString());
						pstmt = con.prepareStatement(query);
						pstmt.setInt(1, restaurante.id());
						return pstmt;
					}) != 1) {
						throw new Exception(
								"La sentencia de actualizaci??n ejecutada ha retornado un n??mero de filas an??malo");
					}
					restaurantes.remove(i);
				}
				WindowActionUtils.promptInfoDialog(mainView, "Eliminaci??n realizada con ??xito",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (ArrayIndexOutOfBoundsException exception) {
				System.err.println("Ningun dato seleccionado, no se puede proceder a eliminaci??n");
				WindowActionUtils.promptInfoDialog(mainView,
						"No se ha seleccionado ning??n dato, no se puede eliminar ninguna fila",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception exception) {
				System.err.println(exception.getMessage());
				System.err.println(exception.getStackTrace());
			}
			break;
		}
		}
	}

	private void switchRegistroAction(String action) {
		switch (action.toLowerCase()) {
		case "guardar datos": {
			try {
				Restaurante restaurante = mainView.getRegistroPanel().genRestaurante();
				if (restaurante == null) {
					throw new NullPointerException();
				}
				if (!persistence.hasRestaurante(restaurante)) {
					if (persistence.updateRestaurante(restaurante, (con, pstmt) -> {
						String query = SQLQueryBuilder.buildInsertQuery(TableContract.RESTAURANTES.toString(),
								RestauranteContract.getAllAttributes());
						pstmt = con.prepareStatement(query);
						pstmt.setString(1, restaurante.nombre());
						pstmt.setString(2, restaurante.region());
						pstmt.setString(3, restaurante.ciudad());
						pstmt.setInt(4, restaurante.distincion());
						pstmt.setString(5, restaurante.direccion());
						pstmt.setFloat(6, restaurante.precioMin());
						pstmt.setFloat(7, restaurante.precioMax());
						pstmt.setString(8, restaurante.cocina());
						pstmt.setString(9, restaurante.telefono());
						pstmt.setString(10, restaurante.web());
						return pstmt;
					}) == 1) {
						WindowActionUtils.promptInfoDialog(mainView, "Restaurante introducido correctamente",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						throw new Exception(
								"La sentencia de actualizaci??n ejecutada ha retornado un n??mero de filas an??malo");
					}

				} else {
					WindowActionUtils.promptInfoDialog(mainView,
							"El nombre introducido ya esta asignado a un restaurante", JOptionPane.ERROR_MESSAGE);
				}
			} catch (NullPointerException e) {
				System.out.println("El restaurante no se pudo generar correctamente");
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println(e.getStackTrace());
			}

			break;
		}
		case "limpiar datos": {
			mainView.getRegistroPanel().clearFields();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void switchModificarAction(String action) {
		ModificarPanel modPanel = mainView.getModificarPanel();
		switch (action.toLowerCase()) {
		case "buscar restaurante": {
			String searchRestauranteStr = modPanel.getSearchRestaurante();
			updateRestaurantes();
			Restaurante searchRestaurante = null;
			for (Restaurante restaurante : restaurantes) {
				if (restaurante.nombre().toLowerCase().contains(searchRestauranteStr.toLowerCase())) {
					searchRestaurante = restaurante;
					break;
				}
			}
			if (searchRestaurante != null) {
				modPanel.setEditable(true);
				modPanel.setValues(searchRestaurante);
			} else {
				WindowActionUtils.promptInfoDialog(mainView,
						"No se ha encontrado ning??n restaurante que coincida con el nombre introducido",
						JOptionPane.WARNING_MESSAGE);
			}
			break;
		}
		case "actualizar datos":
			if (modPanel.getEditable()) {
				try {
					Restaurante restaurante = modPanel.genRestaurante();
					if (restaurante == null) {
						throw new NullPointerException();
					}

					if (persistence.updateRestaurante(restaurante, (con, pstmt) -> {
						String query = SQLQueryBuilder.buildUpdateQuery(TableContract.RESTAURANTES.toString(),
								RestauranteContract.getAllAttributes());
						pstmt = con.prepareStatement(query);
						pstmt.setString(1, restaurante.nombre());
						pstmt.setString(2, restaurante.region());
						pstmt.setString(3, restaurante.ciudad());
						pstmt.setInt(4, restaurante.distincion());
						pstmt.setString(5, restaurante.direccion());
						pstmt.setFloat(6, restaurante.precioMin());
						pstmt.setFloat(7, restaurante.precioMax());
						pstmt.setString(8, restaurante.cocina());
						pstmt.setString(9, restaurante.telefono());
						pstmt.setString(10, restaurante.web());
						pstmt.setInt(11, modPanel.getFocusedId());
						return pstmt;
					}) == 1) {
						WindowActionUtils.promptInfoDialog(mainView, "Restaurante actualizar correctamente",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						throw new Exception(
								"La sentencia de actualizaci??n ejecutada ha retornado un n??mero de filas an??malo");
					}
				} catch (NullPointerException e) {
					System.out.println("El restaurante no se pudo actualizar correctamente");
				} catch (Exception e) {
					System.err.println(e.getMessage());
					System.err.println(e.getStackTrace());
				}
			}
		case "cancelar": {
			modPanel.setEditable(false);
			modPanel.clearFields();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void updateRestaurantes() {
		restaurantes = persistence.getRestaurantes();
	}

}
