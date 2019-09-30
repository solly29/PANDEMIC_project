package voiceTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;


public class client implements Runnable{
	DatagramSocket dsock = null;
	AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    AudioFormat format = null;
	public client() {
		// TODO Auto-generated constructor stub
		
		format = new AudioFormat(8000.0f, 16, 1, true, false);
        TargetDataLine microphone;
        
        
		 String ip = "220.88.247.12";

         int port = 9003;

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
                
                try {
                    microphone = AudioSystem.getTargetDataLine(format);

                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(format);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int numBytesRead;
                    int CHUNK_SIZE = 1024;
                    byte[] data = new byte[microphone.getBufferSize() / 5];
                    microphone.start();

                    int bytesRead = 0;
                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, inetaddr, port);
                    dsock.send(sendPacket);
                    try {
                    	while(true) {
	                        while (bytesRead < 1) {
	                        	numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
	                            bytesRead = bytesRead + numBytesRead;
	                           
	                        }
	                        sendPacket = new DatagramPacket(data, data.length, inetaddr, port);
	                        dsock.send(sendPacket);
	                        bytesRead = 0;
                    	}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch (Exception e) {
        			// TODO: handle exception
                	System.out.println(e);
        		}

                /*while((line = br.readLine())!=null){
                		
                       DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, inetaddr, port);
                       dsock.send(sendPacket);

                       if(line.equals("quit")) break;


                       
                }*/

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
            
            byte[] audioData = receivePacket.getData();
            //InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            //audioInputStream = new AudioInputStream(byteArrayInputStream,format, audioData.length / format.getFrameSize());
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            try {
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(format);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            sourceDataLine.start();
            int cnt = 0;
            byte tempBuffer[] = new byte[2048];
            while (true) {
            	try {
					dsock.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	buffer = receivePacket.getData();
            	sourceDataLine.write(buffer, 0, buffer.length);
        	}
            
            	//System.out.println(new String(receivePacket.getData(), 0, receivePacket.getData().length));

              // String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);

              // System.out.println("전송받은 문자열 : "+msg);
		}
	}


 

}