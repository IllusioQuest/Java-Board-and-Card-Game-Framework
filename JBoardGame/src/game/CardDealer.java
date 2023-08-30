package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CardDealer<C extends PlayingCard> extends Dealer<C, CardStack<C>> {

	public static <T extends PlayingCard> void handOut(AuthorizationKey auth, CardStack<T> origin, int amountEach, CardStack<T>... depositories) {
		HashSet<CardStack<T>> set = new HashSet<CardStack<T>>();
		set.add(origin);
		new CardDealer<T>().handOut(auth, set, amountEach, depositories);
	}
	
	@Override
	protected Dealer<C, CardStack<C>> handOut(AuthorizationKey auth,
			HashSet<CardStack<C>> origins, int amountEach, CardStack<C>... depositories) {
		
		ArrayList<C> cards = new ArrayList<C>();
		for (CardStack<C> stack : origins) { 
			cards.addAll(stack.getCards(auth));
		}
		for (int i = 0; i < amountEach; i++) {
			for (CardStack<C> stack : depositories) {
				stack.addCard(auth, cards.get(0));
				cards.remove(0);
			}
		}
		return this;
		
	}

}
