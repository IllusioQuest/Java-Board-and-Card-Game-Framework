package example2;

import game.Game;
import game.GameState;
import game.PlayerController;
import game.TurnDescription;

public class RiskyController implements PlayerController<ExampleGame> {

	@Override
	public <T> T choose(T[] arr, GameState<? extends Game> ctx, TurnDescription description) {
		return arr[1];
	}

}
