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
	public DOA(Socket socket) { //소켓을 넣은 이유는 ID, PWD 찾을때 바로 클라로 넘겨주기 위함.
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
		// 회원가입이 성공적으로 이루어 지면 DB에 해당 정보 추가하는 메소드
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

	public boolean MatchID(String ID) {// 회원가입 할 때 아이디 중복체크 하는 메소드
		try {

			pstmt = conn.prepareStatement("SELECT PWD from user_information where ID = ?");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				String id = rs.getString("ID");
				
				if (id.equals(ID)) {
				
					return false;
				}
			}

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public boolean MatchPWD(String ID, String PWD) { //로그인할때 사용하는 메소드  ID, PWD 맞는지 확인
		try {

			pstmt = conn.prepareStatement("SELECT PWD from user_information where ID = ? ");
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				String pwd = rs.getString("PWD");
				
				if (pwd.equals(PWD)) {

					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	
	public boolean FindID(String NAME, String NUMBER) { // ID, PWD찾을때 사용하는 메소드
		
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
