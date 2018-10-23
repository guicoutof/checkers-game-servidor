package rooms;

public class Room {
	private Player player1 = null;
	private Player player2 = null;
	private int status = 0;
	
	public Room() {
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	public void checkStatus() {
		if(player1!= null && player2 != null) {
			status = 2;
		}else if(player1!= null || player2 != null) {
			status = 1;
		}else status = 0;
	}

	
}
