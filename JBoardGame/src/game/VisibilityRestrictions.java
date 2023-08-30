package game;

import java.util.ArrayList;
import java.util.Arrays;

public class VisibilityRestrictions {
	
	private boolean visibleToAll = false;
	
	private ArrayList<Player> players;
	
	public VisibilityRestrictions (boolean visibleToAll) {
		this.visibleToAll = visibleToAll;
		if (!visibleToAll)
			players = new ArrayList<Player>();
	}
	
	public VisibilityRestrictions (ArrayList<Player> players) {
		this.players = players;
	}
	
	public VisibilityRestrictions (Player... players) {
		this(new ArrayList<Player>(Arrays.asList(players)));
	}
	
	boolean visibleTo (Player player) {
		if (visibleToAll)
			return true;
		return (players.contains(player));
	}
	
	//Package restricted methods to be used by GameProcessor
	
	void setVisibleToAll(boolean visibleToAll) {
		this.visibleToAll = visibleToAll;
	}
	
	void setAuthorizedPlayers (ArrayList<Player> players) {
		this.players = players;
	}
	
	void setAuthorizedPlayers (Player... players) {
		this.players = new ArrayList<Player>(Arrays.asList(players));
	}

}
