import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import rooms.Player;
import rooms.Room;
import rooms.Rooms;

public class Main {
	private static int port = 9999;
	private static Rooms rooms = new Rooms();
	
	public static void main(String[] args) {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(port,InetAddress.getByName("localhost"));
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 	        
        while(true){
            byte[]receiveData = new byte[8300];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Aguardando pacote...");
            try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {

				e.printStackTrace();
			}//recebimento de pacotes 
            
            try {
				tratarMensagem(receivePacket,serverSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }   

	}
	
	/***
	 * Tratamento da mensagem recebida pelo socket
	 * 
	 * @param receivePacket pacote UDP
	 * @param serverSocket Socket do servidor
	 */
	
	public static void tratarMensagem(DatagramPacket receivePacket,DatagramSocket serverSocket) throws IOException{
        String sentence = new String(receivePacket.getData());
        InetAddress IPAddress = receivePacket.getAddress();
        int portReceive = receivePacket.getPort();
        byte[] sendData = new byte[1024];
     
        switch(sentence.charAt(0)){
        case '1': //conectar em uma partida
        	if(sentence.charAt(1) == '0'){ //pedido de login
                String nick[] = sentence.split("\n");
                Player player = new Player(nick[1], IPAddress,portReceive);
                if(rooms.verificaPlayer(player)){
                	rooms.addPlayer(player);
                    System.out.println("Player conectado");
                    //foi logado
                    String newsentence = "11\n";
                    sendData = newsentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket (sendData,sendData.length,IPAddress,portReceive);
                    serverSocket.send(sendPacket);
                    salaPronta(receivePacket,serverSocket);
                }else {
                	String newsentence = "90\n";//erro nick invalido
                    sendData = newsentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket (sendData,sendData.length,IPAddress,portReceive);
                    serverSocket.send(sendPacket);
                }
        	}
        	break;
        case '2': //mandar movimento para o oponente
        	if(sentence.charAt(1) == '0'){
        		
        		
        		
        	}
        	break; 
        default:
        	Player player = rooms.buscarAdversario(portReceive);	
        	if(player!=null) {
        		sendData = receivePacket.getData();
        		DatagramPacket sendPacket = new DatagramPacket (sendData,sendData.length,player.getIp(),player.getPort());
        		serverSocket.send(sendPacket);
        	}
        	break;
        }
        
	}
	
	/***
	 * Verifica se há uma sala pronta, <br> se houver é enviado um pacote para ambos os players para abertura da sala
	 * @param receivePacket pacote UDP
	 * @param serverSocket socket do Servidor
	 */
	
	public static void salaPronta(DatagramPacket receivePacket,DatagramSocket serverSocket) {
		InetAddress IPAddress;
        int portReceive;
        byte[] sendData = new byte[1024];
        for(Room r:rooms.getRooms()) {
        	r.checkStatus();
        	if(r.getStatus()==2) {
        		String newsentence = "12\n";
        		newsentence += r.getPlayer1().getNick()+"\n";
        		newsentence += r.getPlayer2().getNick()+"\n";
                sendData = newsentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket (sendData,sendData.length,r.getPlayer1().getIp(),r.getPlayer1().getPort());
                try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                sendPacket = new DatagramPacket (sendData,sendData.length,r.getPlayer2().getIp(),r.getPlayer2().getPort());
                try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                r.setStatus(3);
        	}
        	
        }
	}
	
	

}
