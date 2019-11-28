package DB;

import java.sql.*;

public class DOA {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "SELECT PWD from user_information where ID=? ";
	String url = "jdbc:mysql://13.125.178.148:3306/pandemic";

	public DOA() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "Rladudrms1234!");

			if (conn != null) {
				System.out.println("db성공");
			} else {
				System.out.println("db실패");
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

	public void Insert(String NAME,  String NUMBER, String ID, String PWD) {
		
		StringBuilder sb = new StringBuilder();
		
        String st = sb.append("INSERT INTO user_information (NAME,NUMBER, ID, PWD) VALUES('")
        		.append(NAME + "',")
        		.append("'" + NUMBER + "',")
        		.append("'" + ID + "',")
                .append("'" + PWD + "'")
                .append(");")
                .toString();
        
        System.out.println(st);
        
        try {
            pstmt.executeUpdate(st);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}

	public boolean MatchID(String ID) {
		try {

			pstmt = conn.prepareStatement("SELECT PWD from user_information ID = ?");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				String id = rs.getString("ID");
				
				if (id.equals(ID)) {
					System.out.println("아이디 중복");
					return false;
				}
			}

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public boolean MatchPWD(String ID, String PWD) {
		try {

			pstmt = conn.prepareStatement("SELECT PWD from user_information where ID = ? ");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				String pwd = rs.getString("PWD");
				System.out.println(pwd);

				if (pwd.equals(PWD)) {

					System.out.println("마자연!");
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

}
