package Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/*class PeaceNightCard extends Card {
   // 여기서 CityName은 "평온한 하룻밤" 다음 도시 감연 단계를 생략합니다
   public PeaceNightCard(ControlPanel Controlpanel, String CityName) {
      super(Controlpanel, CityName);
      this.CityName = "PeaceNight";
      this.color = "Special";

      ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
      ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
      setIcon(Card);
      this.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
         }

         public void mouseExited(MouseEvent e) {
            setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            // 평온한 하룻밤의 이벤트 같은 경우에는 서버에서 이벤트를 처리해줘야한다.
            // 서버에다가 이번에는 전염카드 이벤트 발생시키지 않는 메시지를 보내면 될꺼 같다.
            Controlpanel.Havecard.removeCard(CityName);
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new BasicSelect(Controlpanel));
            Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
            Controlpanel.revalidate();
            Controlpanel.repaint();
         }
      });
   }
}// 평온한 하룻밤 카드

class AntiBodiesCard extends Card {// 항체 보유자 카드
   // 버린 감염 카드 더미에 있는 아무 감염 카드 1장을 게임에서 제거합니다.
   public AntiBodiesCard(ControlPanel Controlpanel, String CityName) {
      super(Controlpanel, CityName);
      this.CityName = "AntiBodies";
      this.color = "Special";

      ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
      ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
      setIcon(Card);
      this.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
         }

         public void mouseExited(MouseEvent e) {
            setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            // 이것도 서버가 처리해줘야한다 서버의 버린 카드 더미에서
            // 그 뭐냐 그거그거그거 배열을 받아와 그 다음 JOptionpane을 사용해서 내가 카드 7장 넘어가면
            // 삭제 되도록 만들때 JOptionpane 그걸 응용해서 하면 될듯
            Controlpanel.Havecard.removeCard(CityName);
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new BasicSelect(Controlpanel));
            Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
            Controlpanel.revalidate();
            Controlpanel.repaint();
         }
      });
   }
}

class PredictCard extends Card {//예측
   public PredictCard(ControlPanel Controlpanel, String CityName) {
      super(Controlpanel, CityName);
      this.CityName = "Predict";
      this.color = "Special";

      ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
      ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
      setIcon(Card);
      this.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
         }

         public void mouseExited(MouseEvent e) {
            setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            // 이것도 서버가 처리해줘야한다 전염카드 덱위에서 6장을 뽑아
            // 뽑아서 이것도 JOptionPane을 사용하자.
            Controlpanel.Havecard.removeCard(CityName);
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new BasicSelect(Controlpanel));
            Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
            Controlpanel.revalidate();
            Controlpanel.repaint();
         }
      });
   }
}

class EmergencyAirCard extends Card {//긴급공중수송
   public EmergencyAirCard(ControlPanel Controlpanel, String CityName) {
      super(Controlpanel, CityName);
      this.CityName = "EmergencyAir";
      this.color = "Special";

      ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
      ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
      setIcon(Card);

      this.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            setIcon((CardPush));
         }

         public void mouseExited(MouseEvent e) {
            setIcon((Card));
         }

         public void mousePressed(MouseEvent e) {
            Controlpanel.Havecard.removeCard(CityName);
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new EmergencyAirPanel(Controlpanel));
            Controlpanel.revalidate();
            Controlpanel.repaint();
         }
      });
   }

   class EmergencyAirPanel extends ControlShape {
      ControlPanel Controlpanel;
      JScrollPane scroll;
      JPanel panel;
      ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/button.png"));

      EmergencyAirPanel(ControlPanel Controlpanel) {
         this.setLayout(new BorderLayout());
         this.Controlpanel = Controlpanel;
         panel = new JPanel();
         // panel.setLayout(new GridLayout(8, 7, 0, 0));

         panel.setPreferredSize(new Dimension(1920, 600));
         scroll = new JScrollPane(panel);
         // scroll.setSize(new Dimension(1920, 300));
         // scroll.setPreferredSize(new Dimension(1920, 1080));
         scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
         scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         String[] text = Controlpanel.Mainpanel.citys.returntext();
         JLabel[] lcity = new JLabel[48];

         for (int i = 0; i < 48; i++) {
            lcity[i] = new JLabel(button);
            lcity[i].setText(text[i + 1]);
            lcity[i].setVerticalTextPosition(JLabel.CENTER);
            lcity[i].setHorizontalTextPosition(JLabel.CENTER);
            lcity[i].addMouseListener(new MoveCity());
            panel.add(lcity[i]);
         }

         for (int i = 0; i < 18; i++)
            // panel.add(new JLabel());

            add(scroll);
      }

      class MoveCity extends MouseAdapter {
         public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String Choicecity = label.getText();
            Point ChoicePoint = Controlpanel.Mainpanel.citys.CityPosition(Choicecity);
            Controlpanel.Mainpanel.character.setXY(ChoicePoint.x, ChoicePoint.y);
            Controlpanel.Mainpanel.character.setCC(Choicecity,
                  Controlpanel.Mainpanel.citys.returnCity(Choicecity).getColor());

            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new BasicSelect(Controlpanel));
            Controlpanel.revalidate();
            Controlpanel.repaint();
            Controlpanel.Mainpanel.repaint();
         }
      }
   }
}

class GrandOfMoneyCard extends Card {//정부보조금
   public GrandOfMoneyCard(ControlPanel Controlpanel, String CityName) {

      super(Controlpanel, CityName);
      this.CityName = "GrantOfMoney";
      this.color = "Special";

      ImageIcon Card = new ImageIcon(Card.class.getResource("../Image/" + CityName + ".png"));
      ImageIcon CardPush = new ImageIcon(Card.class.getResource("../Image/" + CityName + "Push.png"));
      setIcon(Card);
      this.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            setIcon((CardPush));// 마우스가 카드 위에 올라갔을 때 색상이 바뀌게.
         }

         public void mouseExited(MouseEvent e) {
            setIcon((Card));// 마우스가 떼졌을 때 카드의 색상이 원래대로
         }

         public void mousePressed(MouseEvent e) {
            // 이것도 서버가 처리해줘야한다 전염카드 덱위에서 6장을 뽑아
            // 뽑아서 이것도 JOptionPane을 사용하자.
            Controlpanel.Havecard.removeCard(CityName);
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new GrantOfMoneyPanel(Controlpanel));
            Controlpanel.Mainpanel.Controlpanel.setBounds(0, 840, 1920, 240);
            Controlpanel.revalidate();
            Controlpanel.repaint();
         }
      });
   }

   class GrantOfMoneyPanel extends ControlShape {
      ControlPanel Controlpanel;
      JScrollPane scroll;
      JPanel panel;
      ImageIcon button = new ImageIcon(AirplanePanel.class.getResource("../Image/button.png"));

      GrantOfMoneyPanel(ControlPanel Controlpanel) {
         this.setLayout(new BorderLayout());
         this.Controlpanel = Controlpanel;
         panel = new JPanel();

         panel.setPreferredSize(new Dimension(1920, 600));
         scroll = new JScrollPane(panel);

         scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
         scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         String[] text = Controlpanel.Mainpanel.citys.returntext();
         JLabel[] lcity = new JLabel[48];

         for (int i = 0; i < 48; i++) {
            lcity[i] = new JLabel(button);
            lcity[i].setText(text[i + 1]);
            lcity[i].setVerticalTextPosition(JLabel.CENTER);
            lcity[i].setHorizontalTextPosition(JLabel.CENTER);
            lcity[i].addMouseListener(new GrantOfMoneyControl());
            panel.add(lcity[i]);
         }
         add(scroll);
      }

      class GrantOfMoneyControl extends MouseAdapter {
         public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String Choicecity = label.getText();
            City choice=Controlpanel.Mainpanel.citys.returnCity(Choicecity);
            choice.setLabatory();
            
            Controlpanel.invalidate();
            Controlpanel.removeAll();
            Controlpanel.add(new BasicSelect(Controlpanel));
            Controlpanel.revalidate();
            Controlpanel.repaint();
            Controlpanel.Mainpanel.repaint();
         }
      }
   }
}*/