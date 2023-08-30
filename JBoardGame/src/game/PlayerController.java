package game;

public interface PlayerController<G extends Game> {

	public <T> T choose(T[] arr, GameState<? extends Game> ctx, TurnDescription description); //Normally this should be <? extends G>. Might be changed when implementing.
	
	public default boolean me(Player player) {
		if (player.getController() == this)
			return true;
		return false;
	}
	
}
