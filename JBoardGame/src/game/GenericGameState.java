package game;

import java.util.HashMap;

public class GenericGameState<G extends Game> implements GameState<G> {
	
	private HashMap<String, GameObject> currState; 
	private Player[] players;
	
	public GenericGameState(HashMap<String, GameObject> state, Player[] players) {
		this.currState = state;
		this.players = players;
	}
	
	public GameObject get(String identifier) {
		return currState.get(identifier);
	}
	
	public Player[] getPlayers() {
		return players;
	}

}
