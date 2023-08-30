package game;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {
	
	//Private things
	
	private GameProcessor processor; 
	
	private AuthorizationKey auth;
	
	////////////////////////////////////////////////
	//Protected things (to be used by the subclasses)
	
	protected <T extends GameObject> T addObject(T obj) {
		return processor.addObject(obj);
	}
	
	protected void addObject(GameObject... objects) {
		for (GameObject obj : objects) {
			processor.addObject(obj);
		}
	}
	
	protected <T extends GameObject> T addObject(String id, T obj) {
		return processor.addObject(id, obj);
	}
	
	protected void addObject(HashMap<String, GameObject> objects) {
		for (Map.Entry<String, GameObject> entry : objects.entrySet()) {
			addObject(entry.getKey(), entry.getValue());
		}
	}
	
	protected void add(Addable add) {
		addObject(add.getObjects());
		addPlayer(add.getPlayers());
	}
	
	protected Player nextPlayer() {
		int playerIndex = (processor.getActivePlayerIndex() + (processor.getPlayingDirection() ? -1 : 1)) % processor.getPlayers().length;
		if (playerIndex < 0)
			playerIndex += processor.getPlayers().length;
		processor.setActivePlayer(playerIndex);
		return processor.getActivePlayer();
	}
	
	protected Player previousPlayer() {
		int playerIndex = (processor.getActivePlayerIndex() + (processor.getPlayingDirection() ? 1 : -1)) % processor.getPlayers().length;
		if (playerIndex < 0)
			playerIndex += processor.getPlayers().length;
		processor.setActivePlayer(playerIndex);
		return processor.getActivePlayer();
	}
	
	protected Player setActivePlayer(Player player) {
		for (int i = 0; i < processor.getPlayers().length; i++) {
			if (processor.getPlayers()[i] == player)
				processor.setActivePlayer(i);
		}
		return processor.getActivePlayer();
	}
	
	protected Player getActivePlayer() {
		return processor.getActivePlayer();
	}
	
	protected Player[] getPlayers() {
		return processor.getPlayers();
	}
	
	protected void setPlayingDirection(boolean direction) {
		processor.setPlayingDirection(direction);
	}
	
	protected boolean getPlayingDirection() {
		return processor.getPlayingDirection();
	}
	
	protected abstract void turn(Player p);
	
	protected final void playersTurn() {
		turn(getActivePlayer());
	}
	
	protected <T> T playersDecision(T[] possibilities, TurnDescription description) {
		return processor.playersDecision(possibilities, getActivePlayer(), createGameState(), description);
	}
	
	protected AuthorizationKey auth() {
		return auth;
	}
	
	protected void setVisibilityRestrictions(GameObject obj, VisibilityRestrictions restrictions) {
		processor.setVisibilityRestrictions(obj, restrictions);
	}
	
	protected void setVisibilityRestrictions(GameObject obj, Player... players) {
		processor.setVisibilityRestrictions(obj, new VisibilityRestrictions(players));
	}
	
	protected void setVisibilityRestrictions(GameObject obj, boolean visibleToAll) {
		processor.setVisibilityRestrictions(obj, new VisibilityRestrictions(visibleToAll));
	}
	
	protected HashMap<String, GameObject> getObjects(Addable add) {
		return add.getObjects();
	}
	
	protected GameObject get(String id) {
		return processor.getObject(id);
	}
	
	protected <T extends GameObject> T get(String id, Class<T> type) {
		return (T) processor.getObject(id);
	}
	
	protected int getPlayerNumber(Player p) {
		return processor.getPlayerNumber(p);
	}

	/////////////////////////////////////////////
	//Used for initialization
	
	protected Player addPlayer(Player player) {
		return processor.addPlayer(player);
	}
	
	protected void addPlayer(Player... players) {
		for (Player p : players) {
			processor.addPlayer(p);
		}
	}
	
	/////////////////////////////////////////////
	//Public things used by all classes
	
	public Game() {
		auth = AuthorizationKey.generateKey();
		this.processor = new GameProcessor();
	}
	
	public Game(GameProcessor processor) {
		auth = AuthorizationKey.generateKey();
		this.processor = processor;
	}
	
	public final void startGame() {
		initialize();
		processor.endInitialization();
		run();
	}
	
	/////////////////////////////////////////////
	//Protected methods to be overridden by the subclasses
	
	protected abstract void initialize();
	
	protected abstract void run();
	
	protected GameState<? extends Game> createGameState() {
		return createDefaultGameState();
	};
	
	/////////////////////////////////////////////
	//Private processing
	
	private GameState<? extends Game> createDefaultGameState(Player player) {
		HashMap<String, GameObject> objects = new HashMap<String, GameObject>();
		int i = 0;
		for (GameObject obj : processor.getObjects()) {
			objects.put(String.valueOf(i), obj.restricted(player));
			i++;
		}
		return new GenericGameState<Game>(objects, processor.getPlayers());
	}
	
	private GameState<? extends Game> createDefaultGameState() {
		return createDefaultGameState(getActivePlayer());
	}

}
