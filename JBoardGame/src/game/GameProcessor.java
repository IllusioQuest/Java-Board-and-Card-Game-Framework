package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameProcessor {
	
	private HashMap<String, GameObject> gameObjects;
	
	private Player[] players;
	
	//Only used for initialization
	
	private ArrayList<Player> tempPlayerLoad;
	
	Player addPlayer(Player player) {
		tempPlayerLoad.add(player);
		return player;
	}
	
	void endInitialization() {
		players = tempPlayerLoad.toArray(new Player[tempPlayerLoad.size()]);
		for (GameObject obj : getObjects()) {
			obj.endInitialization();
		}
	}
	
	//Package restricted methods to be used by Game
	
	<T extends GameObject> T addObject(String id, T obj){
		if (id == null || id.startsWith("null")) {
			id = generateID(obj);
		}
		gameObjects.put(id, obj);
		return obj;
	}
	
	<T extends GameObject> T addObject(T obj) {
		gameObjects.put(generateID(obj), obj);
		return obj;
	}
	
	private int activePlayerIndex;
	
	Player getActivePlayer() {
		return players[activePlayerIndex];
	}
	
	Player[] getPlayers() {
		return players;
	}
	
	int getActivePlayerIndex() {
		return activePlayerIndex;
	}
	
	Player setActivePlayer(int i) {
		activePlayerIndex = i;
		return players[activePlayerIndex];
	}
	
	private boolean playingDirection;
	
	void setPlayingDirection(boolean direction) {
		playingDirection = direction;
	}
	
	boolean getPlayingDirection() {
		return playingDirection;
	}
	
	<T> T playersDecision(T[] arr, Player player, GameState<? extends Game> ctx, TurnDescription description) {
		return player.choose(arr, ctx, description);
	}
	
	GameObject[] getObjects() {
		return gameObjects.values().toArray(new GameObject[gameObjects.size()]);
	}
	
	GameObject getObject(String id) {
		return gameObjects.get(id);
	}
	
	void setVisibilityRestrictions(GameObject obj, VisibilityRestrictions restrictions) {
		obj.restrictions = restrictions;
	}
	
	int getPlayerNumber(Player p) {
		return Arrays.asList(players).indexOf(p);
	}
	
	//Constructors and public methods
	
	GameProcessor() {
		tempPlayerLoad = new ArrayList<Player>();
		gameObjects = new HashMap<String, GameObject>();
	}
	
	//Private stuff
	
	private String generateID(GameObject obj) {
		String[] wholeName = obj.getClass().toString().split(" ");
		String name = wholeName[wholeName.length-1];
		int id = 100;
		while (gameObjects.get(name + id) != null) {
			id++;
		}
		return name + id;
	}

}
