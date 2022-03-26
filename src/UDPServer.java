import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {
		//Create server socket
		DatagramSocket serverSocket = new DatagramSocket(9876);
		while(true) {
			byte[] receiveData = new byte[1024];
			//block until packet is sent by client
			DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivedPacket);
			//Get the information about the datagram of the client
			InetAddress IPAddress = receivedPacket.getAddress();
			int port = receivedPacket.getPort();
			//Get the data of the packet
			String sentence = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
			System.out.println("RECEIVED FROM CLIENT "+IPAddress.getHostAddress()+": " + sentence);
			//Change the data to capital letters
			String capitalizedSentence = sentence.toUpperCase();
			byte[] sendData = new byte[sentence.length()];
			sendData = capitalizedSentence.getBytes();
			//Send back the response to the client
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}
