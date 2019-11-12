package voiceTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class server {
	private static DatagramSocket dsock = null;

	public static void main(String[] args) {
		HashMap<InetAddress, Integer> user = new HashMap<InetAddress, Integer>();

		int port = 9003;

		// 클라이언트에게 DatagramPacket을 전송하거나 수신하기 위해 DatagramSocket 객체 생성

		// DatagramSocket dsock = null;

		try {

			System.out.println("접속 대기상태입니다.");

			dsock = new DatagramSocket(port);

			String line = null;
			AudioInputStream audioInputStream;
			SourceDataLine sourceDataLine = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			AudioFormat format = null;

			while (true) {

				byte[] buffer = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

				// System.out.println(receivePacket.getPort());

				/*
				 * String msg = new String(receivePacket.getData(), 0,
				 * receivePacket.getLength());
				 * 
				 * System.out.println("전송 받은 문자열 : " + msg);
				 * 
				 * if(msg.equals("quit")) break;
				 */
				//dsock.receive(receivePacket);
				//user.put(receivePacket.getAddress(), receivePacket.getPort());
				//System.out.println(user);
				format = new AudioFormat(8000.0f, 16, 1, true, false);

				// InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
				// audioInputStream = new AudioInputStream(byteArrayInputStream,format,
				// audioData.length / format.getFrameSize());
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

				
				 /*while (true) { dsock.receive(receivePacket); buffer =
				receivePacket.getData(); sourceDataLine.write(buffer, 0, buffer.length); }*/
				 
				while(true) {
					DatagramPacket sendPacket = null;
					dsock.receive(receivePacket);
					user.put(receivePacket.getAddress(), receivePacket.getPort());
					for (InetAddress ip : user.keySet()) {
						
						//if(!ip.equals(receivePacket.getAddress())) {
						sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getData().length, ip,
								user.get(ip));
							try {
							dsock.send(sendPacket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						//}
					}
				}

			}

		} catch (Exception e) {

			System.out.println(e);

		} finally {

			if (dsock != null)

				dsock.close();

		}

	}

}
