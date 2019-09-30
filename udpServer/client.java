package udp;

import java.net.*;

import java.io.*;


 

public class client implements Runnable{
	DatagramSocket dsock = null;
	public client() {
		// TODO Auto-generated constructor stub
		 String ip = "39.127.8.83";

         int port = 9000;

         InetAddress inetaddr = null;//ip가 들어간다.

          

         try{

                inetaddr = InetAddress.getByName(ip);//ip를 넣고

         }catch(UnknownHostException e){

                System.out.println("잘못된 도메인이나 ip입니다.");

                System.exit(1);

         }

         
        

         try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//입력값을 바이트 형식으로 바꿔서 넣음

                dsock = new DatagramSocket();


                String line = null;
                Thread t = new Thread(this);
                t.start();

                while((line = br.readLine())!=null){
                		
                       DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, inetaddr, port);
                       dsock.send(sendPacket);

                       if(line.equals("quit")) break;


                       
                }

                       
                      

                

                System.out.println("UDPEchoClient를 종료합니다.");

         }catch(Exception ex){

                System.out.println(ex);

         }finally{

                if(dsock != null)

                       dsock.close();

         }
	}

       public static void main(String[] args){
    	   new client();

            

       }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			byte[] buffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            try {
				dsock.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


               String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);

               System.out.println("전송받은 문자열 : "+msg);
		}
	}


 

}