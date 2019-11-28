package DB;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DOA {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "SELECT PWD from user_information where ID=? ";
	String url = "jdbc:mysql://13.125.178.148:3306/pandemic";
	Socket socket;
	DataOutputStream output;
	public DOA(Socket socket) {
		try {
			this.socket = socket;
			output = new DataOutputStream(socket.getOutputStream());
			
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

			pstmt = conn.prepareStatement("SELECT PWD from user_information where ID = ?");
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

			pstmt = conn.prepareStatement("SELECT PWD, NAME, NUMBER from user_information where ID = ? ");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				String pwd = rs.getString("PWD");
				String name = rs.getString("NAME");
				String number = rs.getString("NUMBER");
				System.out.println(pwd + name + number);

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

	
	public boolean FindID(String NAME, String NUMBER) {
		
		try {

			pstmt = conn.prepareStatement("SELECT ID, PWD from user_information where NAME = ? AND NUMBER = ?");
			pstmt.setString(1, NAME);
			pstmt.setString(2, NUMBER);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				
				String id = rs.getString("ID");
				String pwd = rs.getString("PWD");
				
				output.writeUTF("true");
				output.writeUTF("ID : " + id +" \n "+"Password : " + pwd);
				return true;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}
}
