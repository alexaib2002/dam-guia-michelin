package org.uem.dam.guia_michelin.inter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface UpdateExpression {

	public PreparedStatement executeUpdateSQL(Connection con, PreparedStatement ptsmt) throws SQLException;

}
