package pandemic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Lobby extends JPanel {

   Image background = new ImageIcon(Client.class.getResource("../Lobby_Image/background.png")).getImage();
   Socket gsocket, csocket;
   DataInputStream input;
   DataOutputStream output;
   JFrame top;
   ClientReceiverThread ChatClass = null;
   JTextArea ChatList;

   public Lobby(Socket gsocket, Socket csocket) {
      this.gsocket = gsocket;
      this.csocket = csocket;
      ChatList = new JTextArea(20, 20); // 채팅이 표시되는 영역
      ChatClass = new ClientReceiverThread(csocket, ChatList);
      top = Login.getTop();
      System.out.println(top + "1");
      try {
         input = new DataInputStream(gsocket.getInputStream());
         output = new DataOutputStream(gsocket.getOutputStream());
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      setSize(1920, 1080);
      setLayout(null);
      add(new inFo()).setBounds(190, 730, 310, 320);
      add(new RoomList(gsocket, csocket, top, ChatClass)).setBounds(475, 170, 1000, 465);
      add(new Chat(csocket, ChatClass, ChatList)).setBounds(510, 730, 1230, 370);

      add(new logOut(gsocket, csocket, top, ChatClass)).setBounds(1800, 10, 100, 100);
      

      setVisible(true);

   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(background, 0, 0, null);
   }
}

class logOut extends JButton { 
   // 로그아웃하기, 나가기를 위한 버튼 클래스
   
   JFrame top;
   Socket gsocket, csocket;
   ClientReceiverThread ChatClass;
   DataInputStream input;
   DataOutputStream output;
   ImageIcon exit = new ImageIcon(Client.class.getResource("../Lobby_Image/logoutButton.png"));
   ImageIcon exitPush = new ImageIcon(Client.class.getResource("../Lobby_Image/logoutPush.png"));
   

   public logOut(Socket gsocket, Socket csocket, JFrame top, ClientReceiverThread ChatClass) {
      this.top = top;
      this.gsocket = gsocket;
      this.csocket = csocket;
            
      this.setIcon(exit);
      
      this.setContentAreaFilled(false); // 버튼 내용영역 채우지않기,이미지로 해놨으니깐
      this.setBorderPainted(false); // 버튼 테두리 없애기
      this.setFocusPainted(false); // 눌렀을때 테두리 안뜨게
      
      this.addMouseListener(new MouseAdapter() { // 방만들기버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            setIcon(exitPush);// 버튼 도달했을때 모양이 바뀐다
         }

         

         public void mouseExited(MouseEvent e) {            
            setIcon(exit);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            try {
               //System.out.println("로그아웃시도(클)");
               output = new DataOutputStream(gsocket.getOutputStream());
               output.writeUTF("logout");
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            top.getContentPane().removeAll();
            top.getContentPane().add(new Login(gsocket, csocket));
            top.revalidate();
            top.repaint();

         }

      });


   }
   
}

class inFo extends JPanel {
   // 빈공간  활용을 위한 재미난 라벨들을 추가하였다.
   JFrame top;
   Socket gsocket, csocket;
   ClientReceiverThread ChatClass;
   DataInputStream input;
   DataOutputStream output;
   Font f1 = new Font("HY헤드라인M",Font.PLAIN,25);
   
   public inFo() {
      
      
      setLayout(null);
     
      // this.setBounds(190, 730, 310, 320);//식별용
            
      JLabel nameLabel = new JLabel("Pandemic Online");
      nameLabel.setBounds(10,20,310,30);
      nameLabel.setFont(f1);
      nameLabel.setForeground(Color.white);
      
      JLabel teamName = new JLabel("팀 명 : Jeans");
      teamName.setBounds(10,55,310,30);
      teamName.setFont(f1);
      teamName.setForeground(Color.white);
      
      JLabel supportTeam = new JLabel("도움주신분 : draw 팀");
      supportTeam.setBounds(10, 90, 310, 30);
      supportTeam.setFont(f1);
      supportTeam.setForeground(Color.WHITE);
      
      JLabel label1 = new JLabel("영진인이 뽑은 ");
      label1.setBounds(10, 125, 310, 30);
      label1.setFont(f1);
      label1.setForeground(Color.WHITE);
      
      JLabel label2 = new JLabel("2019년 가장  ");
      label2.setBounds(10, 160, 310, 30);
      label2.setFont(f1);
      label2.setForeground(Color.WHITE);
      
      JLabel label3 = new JLabel("주목할만한 게임 1위");
      label3.setBounds(10, 195, 310, 30);
      label3.setFont(f1);
      label3.setForeground(Color.WHITE);
      
      JLabel label4 = new JLabel("'어렵다. 그래서 재밌다.'");
      label4.setBounds(10, 230, 310, 30);
      label4.setFont(f1);
      label4.setForeground(Color.WHITE);

      JLabel label5 = new JLabel("                 -김정호  ");
      label5.setBounds(10, 265, 310, 30);
      label5.setFont(f1);
      label5.setForeground(Color.WHITE);
      
      
      
      
      
      this.add(nameLabel);
      this.add(teamName);
      this.add(supportTeam);
      this.add(label1);
      this.add(label2);
      this.add(label3);
      this.add(label4);
      this.add(label5);
      
      this.setOpaque(false); // 판넬 안보이게하기
            
      setVisible(true);
   }
}

class RoomList extends JPanel {

   //방목록이 뜨는 패널 , 방만들기및 리프레쉬 버튼도 같이 있다.
   ImageIcon roomMakeIcon = new ImageIcon(Client.class.getResource("../Lobby_Image/Make.png"));
 
   ImageIcon roomRefresh = new ImageIcon(Client.class.getResource("../Lobby_Image/Refresh.png"));

   ImageIcon MakePush = new ImageIcon(Client.class.getResource("../Lobby_Image/MakePush.png"));

   ImageIcon roomRefreshPush = new ImageIcon(Client.class.getResource("../Lobby_Image/RefreshPush.png"));

   Socket gsocket, csocket;
   DataInputStream input;
   DataOutputStream output, output1;
   JLabel la = new JLabel();
   DefaultTableModel model;
   JTable roomListTable;
   JScrollPane scroll;
   String list;
   JFrame top;
   JPanel roomListPanel;

   public RoomList(Socket gsocket, Socket csocket, JFrame top, ClientReceiverThread ChatClass) {
      this.gsocket = gsocket;
      this.csocket = csocket;
      this.top = top;

      model = new DefaultTableModel(0, 0) {
         public boolean isCellEditable(int i, int c) {
            return false;
         }
      };
      roomListTable = new JTable(model);
      scroll = new JScrollPane(roomListTable);
      model.addColumn("방 목록");
      model.addColumn("비밀번호");
      roomListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      // roomListTable.disable();

      roomListPanel = new JPanel();

      try {
         input = new DataInputStream(gsocket.getInputStream());
         output = new DataOutputStream(gsocket.getOutputStream());
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      setLayout(null);
      JButton roomMakeButton = new JButton(roomMakeIcon); // 방만들기 버튼추가
    
      JButton roomRefreshButton = new JButton(roomRefresh); // 새로고침버튼 추가

      roomMakeButton.setContentAreaFilled(false); // 버튼 내용영역 채우지않기,이미지로 해놨으니깐
   
      roomRefreshButton.setContentAreaFilled(false);
      roomMakeButton.setBorderPainted(false); // 버튼 테두리 없애기
    
      roomRefreshButton.setBorderPainted(false);
      roomMakeButton.setFocusPainted(false); // 눌렀을때 테두리 안뜨게
 
      roomRefreshButton.setFocusPainted(false);

      JLabel roomMakeButtonMessage = new JLabel("이것은 방만들기 버튼의 라벨");// 마우스 올릴때 라벨 추가하려고 만든 라벨
      roomMakeButtonMessage.setBounds(50, 100, 200, 50);

      // 배열로 줄이자
      roomMakeButton.addMouseListener(new MouseAdapter() { // 방만들기버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            roomMakeButton.setIcon(MakePush);// 버튼 클릭했을때 모양이 바뀐다
            roomMakeButtonMessage.setVisible(true);
            add(roomMakeButtonMessage);

         }

         public void mouseExited(MouseEvent e) {

            roomMakeButtonMessage.setVisible(false);
            roomMakeButton.setIcon(roomMakeIcon);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {

            new makeRoom(top, gsocket, csocket, ChatClass);
         }

      });

      roomRefreshButton.addMouseListener(new MouseAdapter() { // 새로고침버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            roomRefreshButton.setIcon(roomRefreshPush);// 버튼 클릭했을때 모양이 바뀐다

         }

         public void mouseExited(MouseEvent e) {
            roomRefreshButton.setIcon(roomRefresh);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            printListRoom();
         }

      });

      // this.setBounds(475, 170, 1000, 465);//식별용

      roomListTable.addMouseListener(new MouseListener() {

         @Override
         public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

            try {
               output1 = new DataOutputStream(csocket.getOutputStream());
               System.out.println(csocket);

            } catch (IOException e2) {
               // TODO Auto-generated catch block
               e2.printStackTrace();
            }

            int row = roomListTable.getSelectedRow();
            int col = roomListTable.getSelectedColumn();
            String str = null;
            String str2 = roomListTable.getValueAt(row, 0) + ""; // 방 이름
            String str3 = roomListTable.getValueAt(row, 1) + ""; // 비밀번호 유무
            if (str3.equals("****")) {
               new checkRoomPassword(top, gsocket, csocket, ChatClass, str2);
            } else {
               try {
                  output.writeUTF("Join");
                  output.writeUTF(roomListTable.getValueAt(row, col) + "");
                  output.writeUTF(""); // 비밀번호 없음
                  str = input.readUTF();

                  if (str.equals("true")) {
                     // output1.writeUTF("[제어]stop");
                     top.getContentPane().removeAll();
                     top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
                     top.revalidate();
                     top.repaint();
                  } else {
                     JOptionPane.showMessageDialog(null, "입장 불가능");
                  }
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                  str = "false";
               }
            }

         }
      });

      // 테이블 수정 불가
      roomListTable.getTableHeader().setReorderingAllowed(false);
      roomListTable.getTableHeader().setResizingAllowed(false);

      roomListTable.getColumn("방 목록").setPreferredWidth(800); // 룸리스트테이블 방목록 비밀번호 폭 조절
      roomListTable.getColumn("비밀번호").setPreferredWidth(200);

      roomListTable.setRowHeight(50);

      roomListTable.getTableHeader().setBackground(Color.red);
      
      roomListTable.getTableHeader().setForeground(Color.white);

      // roomListTable.getTableHeader().setOpaque(false);

      roomListTable.setBackground(Color.red); // 셀 배경 설정
      roomListTable.setForeground(Color.white); //셀 글자색 설정

      // 룸리스트테이블 투명하게 보이게하기
      roomListTable.setOpaque(false);
      scroll.setOpaque(false);
      scroll.getViewport().setOpaque(false);
      scroll.setBorder(BorderFactory.createEmptyBorder());
      roomListTable.setShowGrid(false);

      this.setOpaque(false);
      this.add(roomMakeButton); // 방만들기 버튼 적용
     
      this.add(roomRefreshButton); // 새로고침 버튼 적용

      roomListTable.setFont(new Font("HY헤드라인M",Font.PLAIN, 20)); // 방목록에 표시될 방이름과 비밀번호 쪽 폰트설정
      roomListTable.getTableHeader().setFont(new Font("HY헤드라인M",Font.PLAIN,20)); // 방목록과 비밀번호 라고 표시된 헤더 폰트 설정

      // roomListTable.setGridColor(Color.red);

      roomListPanel.add(scroll);
      this.add(roomListPanel);

      roomListPanel.setLayout(null);

      scroll.setBounds(0, 0, 1000, 300);

      roomListPanel.setBounds(10, 110, 1400, 400);

      roomListPanel.setOpaque(false);

      roomMakeButton.setBounds(200, 0, 250, 100);
     
      roomRefreshButton.setBounds(600, 0, 300, 100);
      // this.setBackground(Color.blue);//식별용
      setVisible(true);
      printListRoom();
   }

   public void printListRoom() {
      try {
         output.writeUTF("refresh");
         list = input.readUTF();
         System.out.println(list);

         if (list.length() != 4) {
            list = list.substring(1, list.length() - 1);

            String[] list2 = list.split(", ");
            for (int i = 0; i < list2.length; i++) {

            }
            System.out.println(list2);
            model.setNumRows(0);

            for (int i = list2.length - 1; i >= 0; i--) {
               String[] test2 = list2[i].split("=");
               if (!test2[0].equals("로비")) {
                  String[] test = { test2[0], test2[1] };
                  model.addRow(test);
               }

            }
         }

         // la.setText(Arrays.toString(list2));
         // System.out.println(list);

      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
   }
}

class checkRoomPassword extends JFrame implements ActionListener { 
   // 비밀번호 확인창 

   JFrame top;
   Socket gsocket, csocket;
   DataOutputStream output;
   DataInputStream input;
   ClientReceiverThread ChatClass;
   JPasswordField inputPassword = new JPasswordField();
   String str2 = "";
   ImageIcon enterButton = new ImageIcon(Client.class.getResource("../Lobby_Image/enter.png"));
   ImageIcon enterButtonPush = new ImageIcon(Client.class.getResource("../Lobby_Image/enterPush.png"));
   Image passwordCheckBackground = new ImageIcon(Client.class.getResource("../Lobby_Image/passwordCheck.png")).getImage();
   ImageIcon cancel = new ImageIcon(Client.class.getResource("../Lobby_Image/cancel.png"));
   ImageIcon cancelPush = new ImageIcon(Client.class.getResource("../Lobby_Image/cancelPush.png"));

   public checkRoomPassword(JFrame top, Socket gsocket, Socket csocket, ClientReceiverThread ChatClass, String str2) {
      this.top = top;
      this.gsocket = gsocket;
      this.csocket = csocket;
      this.ChatClass = ChatClass;
      this.str2 = str2;

      setTitle("비밀번호 확인");
      setSize(400, 200);
      setLayout(null);
      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
      JButton PWbutton = new JButton(enterButton);
      JButton cancelButton = new JButton(cancel);
      
      //버튼 영역 채우지않기, 테두리 보이지않게하기,눌렀을때 테두리안보이게하기
      PWbutton.setBorderPainted(false);
      PWbutton.setFocusPainted(false);
      PWbutton.setContentAreaFilled(false);
      
      cancelButton.setBorderPainted(false);
      cancelButton.setFocusPainted(false);
      cancelButton.setFocusPainted(false);
      
      inputPassword.setOpaque(false);
      inputPassword.setForeground(Color.white);
      inputPassword.setFont(new Font("돋움",Font.BOLD,15));
      inputPassword.setBounds(150, 70, 100, 40);

      PWbutton.addActionListener(this);
      PWbutton.setBounds(270, 50, 90, 40);
      
      cancelButton.setBounds(270, 100, 90, 40);
      
      this.add(PWbutton);
      this.add(inputPassword);
      this.add(cancelButton);
      
      PWbutton.addMouseListener(new MouseAdapter() { // 새로고침버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            PWbutton.setIcon(enterButtonPush);// 버튼 클릭했을때 모양이 바뀐다

         }

         public void mouseExited(MouseEvent e) {
            PWbutton.setIcon(enterButton);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            String str = null;
            System.out.println("액션발생까지는 성공함");
            try {
               //조인과 텍스트필드의 내용을 보낸다 . 비밀번호를 받아와서 검사를 해서 틀리면 입장불가 메시지를 띄우고 아니면 룸으로 넘어간다.

               output = new DataOutputStream(gsocket.getOutputStream());
               input = new DataInputStream(gsocket.getInputStream());
               System.out.println(csocket);

               output.writeUTF("Join");
               output.writeUTF(str2);
               output.writeUTF(inputPassword.getText());
               str = input.readUTF();
               System.out.println("비밀번호 보내기는 성공함");
               if (str.equals("false")) {
                  JOptionPane.showMessageDialog(null, "입장불가.");
                  // output1.writeUTF("[제어]stop");

               } else {
                  top.getContentPane().removeAll();
                  top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
                  top.revalidate();
                  top.repaint();
                  dispose();
               }
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
         
      });
      
      cancelButton.addMouseListener(new MouseAdapter() { // 새로고침버튼 마우스액션
          public void mouseEntered(MouseEvent e) {
             cancelButton.setIcon(cancelPush);// 버튼 클릭했을때 모양이 바뀐다

          }

          public void mouseExited(MouseEvent e) {
             cancelButton.setIcon(cancel);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
          }

          public void mousePressed(MouseEvent e) {             
              dispose();
          }
       });

      

      JPanel background = new JPanel() {
         public void paintComponent(Graphics g) {

            g.drawImage(passwordCheckBackground, 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };
      background.setBounds(0, 0, 400, 200);
      this.add(background);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      String str = null;
      System.out.println("액션발생까지는 성공함");
      try {

         output = new DataOutputStream(gsocket.getOutputStream());
         input = new DataInputStream(gsocket.getInputStream());
         System.out.println(csocket);

         output.writeUTF("Join");
         output.writeUTF(str2);
         output.writeUTF(inputPassword.getText());
         str = input.readUTF();
         System.out.println("비밀번호 보내기는 성공함");
         if (str.equals("false")) {
            JOptionPane.showMessageDialog(null, "입장불가.");
            // output1.writeUTF("[제어]stop");

         } else {
            top.getContentPane().removeAll();
            top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
            top.revalidate();
            top.repaint();
            this.dispose();
         }
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
   }
}

class makeRoom extends JFrame implements ActionListener { 
   // 방만들기 누르면 뜨는 창
   JFrame top;
   Socket gsocket, csocket;
   DataOutputStream output, output1;
   DataInputStream input;
   ClientReceiverThread ChatClass;
   JTextField roomNameField = new JTextField();
   JPasswordField roomPasswordField = new JPasswordField();
   Image makeRoomBackground = new ImageIcon(Client.class.getResource("../Lobby_Image/makeRoom.png")).getImage();
   ImageIcon roomMake = new ImageIcon(Client.class.getResource("../Lobby_Image/makeRoomButton.png"));
   ImageIcon roomMakePush = new ImageIcon(Client.class.getResource("../Lobby_Image/makeRoomButtonPush.png"));
   ImageIcon cancel = new ImageIcon(Client.class.getResource("../Lobby_Image/cancel.png"));
   ImageIcon cancelPush = new ImageIcon(Client.class.getResource("../Lobby_Image/cancelPush.png"));

   public makeRoom(JFrame top, Socket gsocket, Socket csocket, ClientReceiverThread ChatClass) {
      this.top = top;
      this.gsocket = gsocket;
      this.csocket = csocket;
      this.ChatClass = ChatClass;

      setTitle("방만들기");
      setSize(500, 300);
      setLayout(null);
      setResizable(false);
      setLocationRelativeTo(null);
      // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);

      JButton makeRoomButton = new JButton(roomMake);
      makeRoomButton.addActionListener(this);
      makeRoomButton.setBounds(130, 210, 90, 40);
      
      JButton cancelButton = new JButton(cancel);      
      cancelButton.addActionListener(this);
      cancelButton.setBounds(270, 210, 90, 40);
      

      //버튼 영역 채우지않기, 테두리 보이지않게하기,눌렀을때 테두리안보이게하기
      
      cancelButton.setContentAreaFilled(false);      
      cancelButton.setBorderPainted(false);
      cancelButton.setFocusPainted(false);
      
      makeRoomButton.setContentAreaFilled(false);
      makeRoomButton.setBorderPainted(false);
      makeRoomButton.setFocusPainted(false);
      
      this.add(makeRoomButton);
      this.add(cancelButton);   

      roomNameField.setBounds(200, 80, 110, 35);
      roomPasswordField.setBounds(200, 155, 110, 35);
      
      roomNameField.setForeground(Color.white);
      roomPasswordField.setForeground(Color.white);
      
      roomNameField.setFont(new Font("HY헤드라인M",Font.PLAIN,14));
      roomPasswordField.setFont(new Font("돋움",Font.BOLD,14));
      
      roomNameField.setOpaque(false);
      roomPasswordField.setOpaque(false);
      
      
   
      this.add(roomNameField);
      this.add(roomPasswordField);
      
      makeRoomButton.addMouseListener(new MouseAdapter() { // 새로고침버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            makeRoomButton.setIcon(roomMakePush);// 버튼 클릭했을때 모양이 바뀐다

         }

         public void mouseExited(MouseEvent e) {
            makeRoomButton.setIcon(roomMake);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            String str = null;
            try {
               //소켓두개를 보내주고 게임소켓을 받아온다.
               output = new DataOutputStream(gsocket.getOutputStream());
               output1 = new DataOutputStream(csocket.getOutputStream());
               input = new DataInputStream(gsocket.getInputStream());
               System.out.println(csocket);
               output.writeUTF("Create");
               output.writeUTF(roomNameField.getText());
               output.writeUTF(roomPasswordField.getText());
               str = input.readUTF();
               if (str.equals("true")) {
                  // output1.writeUTF("[제어]stop");
                  top.getContentPane().removeAll();
                  top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
                  top.revalidate();
                  top.repaint();
                  dispose();
               } else {
                  JOptionPane.showMessageDialog(null, "방을 만들 수 없습니다.");
               }
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }

      });
      
      cancelButton.addMouseListener(new MouseAdapter() { // 새로고침버튼 마우스액션
         public void mouseEntered(MouseEvent e) {
            cancelButton.setIcon(cancelPush);// 버튼 클릭했을때 모양이 바뀐다

         }

         public void mouseExited(MouseEvent e) {
            cancelButton.setIcon(cancel);// 마우스가 떼졌을 때 버튼의 모양이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            dispose();
         }

      });

      JPanel mkbackground = new JPanel() {
         public void paintComponent(Graphics g) {

            g.drawImage(makeRoomBackground, 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };
      mkbackground.setBounds(0, 0, 500, 300);
      this.add(mkbackground);

   }
/*
   public void actionPerformed(ActionEvent e) {
      // JFrame top=(JFrame)SwingUtilities.getWindowAncestor(Lobby);
      String str = null;
      try {
         output = new DataOutputStream(gsocket.getOutputStream());
         output1 = new DataOutputStream(csocket.getOutputStream());
         input = new DataInputStream(gsocket.getInputStream());
         System.out.println(csocket);
         output.writeUTF("Create");
         output.writeUTF(roomNameField.getText());
         output.writeUTF(roomPasswordField.getText());
         str = input.readUTF();
         if (str.equals("true")) {
            // output1.writeUTF("[제어]stop");
            top.getContentPane().removeAll();
            top.getContentPane().add(new Room(gsocket, csocket, ChatClass));
            top.revalidate();
            top.repaint();
            this.dispose();
         } else {
            JOptionPane.showMessageDialog(null, "방을 만들 수 없습니다.");
         }
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
   }
   */

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
   }
}

class Chat extends JPanel {
   DataOutputStream output;

   Thread ChatTh;
   JTextArea ChatList;

   public Chat(Socket csocket, ClientReceiverThread ChatClass, JTextArea ChatList) {

      setLayout(null);
      // setBounds(510, 730, 1230, 320);//식별용
      // this.setOpaque(false); // 판넬 안보이게하기
      JTextField ChatField = new JTextField(); // 채팅치는 필드

      this.ChatList = ChatList;
      
     

      ChatList.setEditable(false);
      JScrollPane scroll;
      scroll = new JScrollPane(ChatList);

       //채팅창 불투명
      ChatList.setOpaque(false);
      scroll.setOpaque(false);
      scroll.getViewport().setOpaque(false);
      
       //채팅창 글자 흰색
      ChatList.setForeground(Color.white);
      //scroll.setForeground(Color.white);
      ChatField.setForeground(Color.white);
      
      ChatField.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      
      ChatList.setFont(new Font("HY헤드라인M",Font.PLAIN,18));
      
      
      
      ChatField.setOpaque(false); // 채팅입력하는곳 불투명하게

      this.add(scroll);
      this.add(ChatField);
      try {
         output = new DataOutputStream(csocket.getOutputStream());
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      ChatTh = new Thread(ChatClass);
      ChatTh.start();

      ChatField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JTextField t = (JTextField) e.getSource();
            // ChatList.append(t.getText() + "\n");
            try {
               ChatList.setCaretPosition(ChatList.getDocument().getLength());
               output.writeUTF("[채팅]" + t.getText());
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            t.setText("");
         }
      });

      this.setOpaque(false); // 판넬 안보이게하기
      scroll.setBounds(0, 0, 1230, 310);
      // ChatList.setBounds(0, 0, 1200, 200);
      ChatField.setBounds(0, 320, 1230, 30);
      setVisible(true);
   }
}