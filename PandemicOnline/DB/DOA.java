package DB;

import java.sql.*;

public class DOA {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "SELECT PWD from user_information where ID=? ";
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

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) throws SQLException {

		new DOA();

	}

	public boolean MatchPWD(String ID, String PWD) {
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				String pwd = rs.getString("PWD");

				if (pwd.equals(PWD)) {

					System.out.println("마자연!");
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}

		return true;

	}

}
