package example2;

import game.Game;
import game.GameState;
import game.GenericGameState;
import game.PlayerController;
import game.Trackboard;
import game.TurnDescription;

public class StrategyController implements PlayerController<ExampleGame> {

	@Override
	public <T> T choose(T[] arr, GameState<? extends Game> ctx, TurnDescription description) {
		GenericGameState<? extends ExampleGame> c = ((GenericGameState<? extends ExampleGame>) ctx);
		Trackboard points = (Trackboard) c.get("0");
		int enemiesPoints = -1;
		if (me(c.getPlayers()[0]))
			enemiesPoints = points.getProgress(c.getPlayers()[1]);
		else
			enemiesPoints = points.getProgress(c.getPlayers()[0]);
		if (enemiesPoints <= 5)
			return arr[1];
		else
			return arr[0];
	}

}
