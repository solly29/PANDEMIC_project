
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lobby  {

   

   public static void main(String[] args) {
      LobbyFrame frame = new LobbyFrame();
   }
}
class LobbyFrame extends JFrame{
   JScrollPane scrollPane;
   ImageIcon icon;

   public LobbyFrame() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1920, 1080);
      setVisible(true);
      icon = new ImageIcon("C:\\Users\\WIN10\\Desktop\\img\\Lobby.png");
      // 배경 Panel 생성후 컨텐츠페인으로 지정
      JPanel background = new JPanel() {
         public void paintComponent(Graphics g) {
            g.drawImage(icon.getImage(), 0, 0, null);
            setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
            super.paintComponent(g);
         }         
      };
      scrollPane = new JScrollPane(background);
      setContentPane(scrollPane);
      JPanel Profile = new JPanel(); // 내정보 패널
      JPanel RoomList = new JPanel(); // 방목록 패널
      JPanel Chat = new JPanel(); // 채팅창 패널
      JLabel label1 = new JLabel("내정보"); // 내정보 창 제목?
      JLabel ChatList = new JLabel("채팅창"); // 채팅창 제목
      Profile.add(label1); // 내정보 제목 추가
      Chat.add(ChatList); // 채팅창 제목 추가      
      Font RLfont,ChatFont,PFfont;  //폰트 추가 차례대로 방목록,채팅,내정보
      RLfont = new Font("Serif",Font.BOLD,50);
      JButton RoomMake = new JButton(new ImageIcon("C:\\\\Users\\\\WIN10\\\\Desktop\\\\img\\\\MkRoom.png")); // 방만들기 버튼 추가
      JButton RoomSearch = new JButton(new ImageIcon("C:\\\\Users\\\\WIN10\\\\Desktop\\\\img\\\\SerRoom.png")); // 방찾기 버튼 추가
      JButton Refresh = new JButton(new ImageIcon("C:\\\\Users\\\\WIN10\\\\Desktop\\\\img\\\\Re.png")); //새로고침버튼 추가
      
      RoomMake.setContentAreaFilled(false); //버튼 내용영역 채우지않기,이미지로 해놨으니깐
      RoomSearch.setContentAreaFilled(false);
      Refresh.setContentAreaFilled(false);
      RoomMake.setBorderPainted(false); //버튼 테두리 없애기
      RoomSearch.setBorderPainted(false);
      Refresh.setBorderPainted(false);
      RoomMake.setFocusPainted(false); //눌렀을때 테두리 안뜨게
      RoomSearch.setFocusPainted(false);
      Refresh.setFocusPainted(false);
      JTextArea ChatTest = new JTextArea("chat test"); //채팅창

      /*JLabel RL = new JLabel("방목록"); // 실제로 방목록이 표시되는 부분
      RL.setFont(RLfont);
      RoomList.add(RL); // 방목록 추가*/
      
      RoomList.add(RoomMake); // 방만들기 버튼 적용
      RoomList.add(RoomSearch); // 방찾기 버튼 적용
      RoomList.add(Refresh); //새로고침 버튼 적용

      //ChatTest.setSize(500, 500);
      Chat.add(ChatTest);

      setLayout(null);
      RoomList.setBounds(600, 200, 800, 800);
      Profile.setBounds(200, 750, 100, 100);
      Chat.setBounds(800, 800, 100, 100);
      background.setBounds(0,0,1920,1080);
      Profile.setOpaque(false); //ㅈ같은 판넬 안보이게하기
      RoomList.setOpaque(false); //ㅈ같은 판넬 안보이게하기
      Chat.setOpaque(false); //ㅈ같은 판넬 안보이게하기
      add(Profile);
      add(RoomList);
      add(Chat);
      add(background);

      setVisible(true); // 해결과제 채팅창 크기조절, 배경사진넣기(전부)

      
      
   }
}