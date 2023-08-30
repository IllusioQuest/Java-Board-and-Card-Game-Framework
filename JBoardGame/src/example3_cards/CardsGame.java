package example3_cards;

import game.*;
import builder.*;

public class CardsGame extends Game {

	@Override
	protected void initialize() {
		CardStack<GenericPlayingCard<Integer>> s = addObject(GenericCardStackBuilder.create(auth(), true, 2, 1,2,3,4,5,6,7,8,9,10));
		
		add(PlayerBuilder
		 		.create(2)
				.setController(
						new PlayerController<CardsGame>() {
							@Override
							public <T> T choose(T[] arr, GameState<? extends Game> ctx, TurnDescription description) {
								//if (arr instanceof GenericPlayingCard<?>[]) {
									int highest = 0;
									PlayingCard highestCard = null;
									for (Object card : arr) {
										if ((int) ((GenericPlayingCard<?>) card).getValue(auth()) > highest) {
											highest = (int) ((GenericPlayingCard<?>) card).getValue(auth());
											highestCard = (GenericPlayingCard<?>) card;
										}
									}
									return (T) highestCard;
								//}
								//else
								//	return arr[0];
							}
						})
				.withEach(auth(), "hand", new CardHand<GenericPlayingCard<Integer>>(auth()).getClass())
				.build());
	
		CardHand<GenericPlayingCard<Integer>> hand0 = (CardHand<GenericPlayingCard<Integer>>) get("hand0");
		CardHand<GenericPlayingCard<Integer>> hand1 = (CardHand<GenericPlayingCard<Integer>>) get("hand1"); 
			
		CardDealer.handOut(auth(), s, 10, hand0, hand1);
		
		System.out.println("--- Player 0:  ------------------");
		for (GenericPlayingCard<Integer> c : hand0.getCards(auth())) {
			System.out.println(c.getValue(auth()));
		}
		
		System.out.println("--- Player 1:  ------------------");
		for (GenericPlayingCard<Integer> c : hand1.getCards(auth())) {
			System.out.println(c.getValue(auth()));
		}
		
		System.out.println("\nRemaining:\n---------------");
		for (int i = 0; i < s.getSize(); i++) {
			System.out.println(s.getCardAtIndex(auth(), i).getValue(auth()));
		}
		
		points = new Trackboard(auth());
		 
	}
	
	private int highestCard, counter;
	private Trackboard points;

	@Override
	protected void run() {
		highestCard = 0;
		counter = 0;
		for (int i = 0; i < 20; i++) {
			playersTurn();
			nextPlayer();
		}
		
		this.setActivePlayer(getPlayers()[0]);
		int p0 = points.getProgress(getActivePlayer());
		int p1 = points.getProgress(nextPlayer());
		System.out.println(String.format("\nPlayer 0 has %d points.\nPlayer 1 has %d points.\nPlayer %d wins!", p0, p1, (p0 > p1) ? 0 : 1));
	}
	
	@Override
	protected void turn(Player p) {
		CardHand<GenericPlayingCard<Integer>> hand = ((CardHand<GenericPlayingCard<Integer>>) get("hand" + getPlayerNumber(p)));
		GenericPlayingCard<Integer> chosenCard = (GenericPlayingCard<Integer>) playersDecision(hand.getCards(auth()).toArray(), null);
		hand.remove(auth(), chosenCard);
		System.out.println(getPlayerNumber(p) + " chose card numbered " + chosenCard.getValue(auth()));
		counter += chosenCard.getValue(auth());
		if (chosenCard.getValue(auth()) >= highestCard) {
			highestCard = chosenCard.getValue(auth());
		}
		else {
			points.move(auth(), nextPlayer(), counter);
			System.out.println("-----> " + getPlayerNumber(getActivePlayer()) + " won " + counter + " points.");
			System.out.println("-----> " + getPlayerNumber(getActivePlayer()) + " has now " + points.getProgress(getActivePlayer()) + " points.");
			counter = 0;
			highestCard = 0;
			previousPlayer();
		}
	}
	
	public static void main(String[] args) {
		CardsGame g = new CardsGame();
		g.startGame();
	}

}
