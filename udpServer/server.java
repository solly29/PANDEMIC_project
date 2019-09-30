package udp;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class server {
	private static DatagramSocket dsock = null;

       public static void main(String[] args){
    	   HashMap<InetAddress, Integer> user = new HashMap<InetAddress, Integer>();
            

             int port = 9000;

            

       // 클라이언트에게 DatagramPacket을 전송하거나 수신하기 위해 DatagramSocket 객체 생성

             //DatagramSocket dsock = null;

             try{

                    System.out.println("접속 대기상태입니다.");

                    dsock = new DatagramSocket(port);

                    String line = null;


                    while(true){

                           byte[] buffer = new byte[1024];

                           DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                           
                           
                           dsock.receive(receivePacket);
                           System.out.println(receivePacket.getPort());

                           String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());

                           System.out.println("전송 받은 문자열 : " + msg);

                           if(msg.equals("quit")) break;
								user.put(receivePacket.getAddress(), receivePacket.getPort());
			                           
                           for(InetAddress ip : user.keySet()) {
                        	   DatagramPacket sendPacket = null;
                        	   if(ip != receivePacket.getAddress()) {
                        		   sendPacket = new DatagramPacket(receivePacket.getData(),receivePacket.getData().length, ip, user.get(ip));
                        		   try {
									dsock.send(sendPacket);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                        	   }

                               
                           }
                           

                           

                    }

                    System.out.println("UDPEchoServer를 종료합니다.");

                   

             }catch(Exception e){

                    System.out.println(e);

             }finally{

                    if(dsock != null)

                           dsock.close();

             }

       }

}