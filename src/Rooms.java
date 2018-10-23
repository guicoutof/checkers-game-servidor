import java.net.InetAddress;
import java.util.ArrayList;

public class Rooms {
	ArrayList<Room> Rooms = new ArrayList<Room>();
	
	public Rooms() {
		
	}
	
	public Rooms(ArrayList<Room> Rooms) {
		this.Rooms = Rooms;
	}
	
	
	
	public ArrayList<Room> getRooms() {
		return Rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		Rooms = rooms;
	}

	public void addPlayer(Player player) {
		for(int i = 0;i<Rooms.size();i++) {
			Rooms.get(i).checkStatus();
			if(Rooms.get(i).getStatus()!=2) {
				if(Rooms.get(i).getPlayer1()==null)Rooms.get(i).setPlayer1(player);
				else Rooms.get(i).setPlayer2(player);
				return;
			}
			Rooms.get(i).checkStatus();
		}
		Room room = new Room();
		room.setPlayer1(player);
		Rooms.add(room);
	}
	
	public Player buscarAdversario(int port) {
		for(Room r: Rooms) {
			if(r.getPlayer1().getPort()==port)return r.getPlayer2();
			else if(r.getPlayer2().getPort()==port)return r.getPlayer1();
		}
		return null;	
	}
	
	public boolean verificaPlayer(Player player) {
		for(Room r: Rooms) {
			if(r.getPlayer1() != null && r.getPlayer1().getNick().equals(player.getNick()))return false;
			if(r.getPlayer2() != null && r.getPlayer2().getNick().equals(player.getNick()))return false;
		}
		return true;
	}
	
	
	
	
	
	
}
