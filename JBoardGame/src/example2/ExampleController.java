package example2;

import game.Game;
import game.GameState;
import game.PlayerController;
import game.TurnDescription;

public class ExampleController implements PlayerController<ExampleGame> {

	@Override
	public <T> T choose(T[] arr, GameState<? extends Game> ctx, TurnDescription description) {
		if (Math.random() > 0.5 ) {
			return arr[1];
		}
		else
			return arr[0];
	}

}
