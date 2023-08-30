package game;

import java.util.HashMap;

public class Trackboard extends GameObject {

	private HashMap<Player, Integer> progress;
	
	private int startValue;
	
	public Trackboard(AuthorizationKey auth) {
		super(auth);
		progress = new HashMap<Player, Integer>();
		startValue = 0;
	}
	
	//Initialization purposes
	
	public Trackboard setStartValue(AuthorizationKey auth, int start) {
		checkAuth(auth);
		startValue = start;
		return this;
	}
	
	//Methods restricted to the Game
	
	public Trackboard addPlayer(AuthorizationKey auth, Player player) {
		checkAuth(auth);
		progress.put(player, startValue);
		return this;
	}
	
	public Trackboard setProgress(AuthorizationKey auth, Player player, int progress) {
		checkAuth(auth);
		this.progress.put(player, progress);
		return this;
	}
	
	public Trackboard move(AuthorizationKey auth, Player player, int progress) {
		return setProgress(auth, player, getProgress(player) + progress);
	}
	
	//Methods allowed to be used by a PlayerController
	
	public int getProgress(Player player) {
		return (progress.get(player) == null) ? 0 : progress.get(player);
	}
	
}
