package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

	private static Connection conn = null;

	public Database() {
		try {
			String url = "jdbc:sqlite:database.db";
			conn = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
	}

	public synchronized Item getItemById(String id) {
		try {
			String sql = "SELECT * FROM v_items WHERE id=? LIMIT 1;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null; // no item found in DB
			}
			return new Item(
					rs.getString("id"),
					rs.getString("title"),
					rs.getString("buy_url"),
					rs.getString("sell_url")
			);
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

}
