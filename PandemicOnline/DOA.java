package DB;

import java.sql.*;

public class DOA {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "SELECT * FROM user_information";
	String url = "jdbc:mysql://106.10.40.27:3306/pandemic";
	
	public DOA() {
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "rladudrms");

			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				String id = rs.getString("ID");
				int password = rs.getInt("PWD");
				String name = rs.getString("NAME");
				int number = rs.getInt("NUMBER");

				System.out.format("%s,%d,%s,%d\n", id, password, name, number);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		
	}

	public static void main(String[] args) throws SQLException {

	new DOA();
	
	}

}
