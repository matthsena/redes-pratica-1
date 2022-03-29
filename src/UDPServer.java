// created on 29/09/2010 at 22:33
import java.net.*;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

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
			String capitalizedSentence = sentence.toUpperCase();
			//Cifra de Cesar
			char[] cifrando = new char[capitalizedSentence.length()];

			for (int i = 0; i < capitalizedSentence.length(); i++)
				cifrando[i] = cesar(capitalizedSentence.charAt(i), 3);

			String fraseCifrada = new String(cifrando);	
			
			byte[] sendData = new byte[fraseCifrada.length()];
			sendData = fraseCifrada.getBytes();
			//Send back the response to the client
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
	
	static char cesar(char letra, int passo){

		if (verificaCaracterEspecial(letra)) {
		// é caracter especial
		return letra;
		}
		
		int maiusculo = ((letra >= 65 && letra <= 90) ? 1 : 0);
		int letraInt = 0;
		if(maiusculo == 1) {
			letraInt = letra + passo;
			if(letraInt > 90) {
				letraInt = letraInt - 26;
			}
		}
		letra = (char)letraInt;
		return letra;
	}
	
	static boolean verificaCaracterEspecial(char c) {
        if (Character.isSurrogate(c)) {
            return true;
        }
        
        if (Character.isWhitespace(c)) {
            return true;
        }
        
        Pattern patternEspecial = Pattern.compile("[^a-zA-Z]");
        Matcher matcherEspecial = patternEspecial.matcher(Character.toString(c));
        
        if (matcherEspecial.find()) {
            return true;
        }
        
        return false;
    }
    
}
