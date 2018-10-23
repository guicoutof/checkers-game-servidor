package rooms;
import java.net.InetAddress;

public class Player {
	private String nick;
    private InetAddress ip;
    private int port;
    
    public Player() {
		// TODO Auto-generated constructor stub
	}
    
    public Player(String nick, InetAddress ip, int port) {
    	this.nick = nick;
    	this.ip = ip;
    	this.port = port;
    }
    
    public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String exibir(){
        String exibir = "Nick: "+nick+ " IP: "+ip+"/"+port;
        return exibir;
    }
}
