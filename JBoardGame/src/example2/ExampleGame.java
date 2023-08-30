package example2;

import game.Game;
import game.Player;
import game.Trackboard;

public class ExampleGame extends Game {

	Player a, b;
	Trackboard points;
	
	boolean winnerIsA;
	
	@Override
	protected void run() {
		System.out.println("------------------\nNew game: ");
		while (points.getProgress(a) > 0 && points.getProgress(b) > 0) {
			playersTurn();
			nextPlayer();
		}
		if (points.getProgress(a) <= 0) {
			System.out.println("B wins");
			winnerIsA = false;
		}
		else {
			System.out.println("A wins");
			winnerIsA = true;
		}
	}
	
	@Override
	protected void turn(Player p) {
		System.out.println("Player " + (p == a ? "A" : "B") + "'s turn:");
		System.out.println("He has " + points.getProgress(p) + " points.");
		Integer decision = playersDecision(new Integer[] {0, 1}, null);
		if (decision == 0) {
			System.out.println("He chooses safety and steals one point.");
			points.move(auth(), nextPlayer(), -1);
			points.move(auth(), previousPlayer(), 1);
		}
		if (decision == 1) {
			System.out.println("He decides to flip a coin.");
			if (Math.random() > 0.5) {
				System.out.println("Head...");
				points.setProgress(auth(), p, 0);
			}
			else {
				System.out.println("Tails!");
				points.move(auth(), nextPlayer(), -5);
				points.move(auth(), previousPlayer(), 5);
			}
		}
		System.out.println("He's got " + points.getProgress(p) + " points now.\n\n");
	}

	@Override
	protected void initialize() {
		a = addPlayer(new Player(new RiskyController()));
		b = addPlayer(new Player(new StrategyController()));
		points = addObject(new Trackboard(auth()));
		points.setStartValue(auth(), 6).addPlayer(auth(), a).addPlayer(auth(), b);
	}

	public static void main(String[] args) {
		int aWins = 0;
		int bWins = 0;
		for (int i = 0; i < 1000; i++) {
			ExampleGame g = new ExampleGame();
			g.startGame();
			if (g.winnerIsA) aWins++; else bWins++;
		}
		System.out.println("Victories A: " + aWins);
		System.out.println("Victories B: " + bWins);
	}

}
