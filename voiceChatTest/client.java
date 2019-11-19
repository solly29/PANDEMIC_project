package voiceTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    DatagramSocket socket;
	public client() {
		// TODO Auto-generated constructor stub

		format = new AudioFormat(8000.0f, 16, 1, true, false);
        TargetDataLine microphone;


		 String ip = "59.24.76.229";

         int port = 9003;

         InetAddress inetaddr = null;//ip가 들어간다.

         try{

                inetaddr = InetAddress.getByName(ip);//ip를 넣고

         }catch(UnknownHostException e){

                System.out.println("잘못된 도메인이나 ip입니다.");

                System.exit(1);

         }

         try{

                dsock = new DatagramSocket();

                Thread t = new Thread(this);
                t.start();

                try {
                    microphone = AudioSystem.getTargetDataLine(format);

                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(format);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int numBytesRead;
                    int CHUNK_SIZE = 4096;
                    byte[] data = new byte[4096];
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
			byte[] buffer = new byte[4096];
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            try {
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(format);
				sourceDataLine.start();
				dsock.receive(receivePacket);
			} catch (IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            buffer = receivePacket.getData();
            sourceDataLine.write(buffer, 0, buffer.length);
		}
	}




} 
