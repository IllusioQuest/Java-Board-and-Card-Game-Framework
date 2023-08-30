package game;

public class Player {
	
	private PlayerController<? extends Game> controller;
	
	public Player(PlayerController<? extends Game> controller) {
		setController(controller);
	}
	
	protected <T, G extends Game> T choose(T[] arr, GameState<? extends G> ctx, TurnDescription description) {
		return controller.choose(arr, ctx, description);
	}
	
	private PlayerController<? extends Game> setController(PlayerController<? extends Game> controller) {
		this.controller = controller;
		return this.controller;
	}
	
	PlayerController<? extends Game> getController(){
		return controller;
	}

}
