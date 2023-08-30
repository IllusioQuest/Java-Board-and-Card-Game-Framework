package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Addable {
	
	public Addable() {
		players = new ArrayList<Player>();
		objects = new HashMap<String, GameObject>();
	}
	
	private ArrayList<Player> players;
	private HashMap<String, GameObject> objects;
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void addObject(String id, GameObject object) {
		objects.put(id, object);
	}
	
	public void addObject(GameObject object) {
		objects.put(null, object);
	}
	
	public void addPlayers(Player... players) {
		for (Player p : players) {
			addPlayer(p);
		}
	}
	
	public void addObjects(GameObject... objects) {
		for (GameObject o : objects) {
			addObject(o);
		}
	}
	
	public Player[] getPlayers() {
		return players.toArray(new Player[players.size()]);
	}
	
	HashMap<String, GameObject> getObjects() {
		return objects;
	}

}
