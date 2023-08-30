package example;

import game.Game;
import game.GameState;
import game.Player;

public class ExampleGame extends Game {

	int sum = 0;
	
	@Override
	protected void turn(Player p) {
		sum++;
		System.out.println(p.hashCode() + ": " + sum);
	}

	@Override
	protected void initialize() {
		addPlayer(new Player(null), new Player(null), new Player(null), new Player(null));
	} 

	@Override
	protected void run() {
		while(sum < 10) {
			playersTurn();
			nextPlayer();
		}
		setPlayingDirection(!getPlayingDirection());
		while(sum < 20) {
			playersTurn();
			nextPlayer();
		}
	}
	
	public static void main(String[] args) {
		ExampleGame game = new ExampleGame();
		game.startGame();
	}
	
}
