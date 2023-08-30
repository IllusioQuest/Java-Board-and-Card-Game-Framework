package game;

import java.util.ArrayList;
import java.util.Arrays;

public class CardStack<C extends PlayingCard> extends GameObject implements Depository<C> {
	
	private ArrayList<C> cards;
	
	private int firstCardsVisible;
	protected boolean visible;
	
	public CardStack(AuthorizationKey auth) {
		super(auth);
		cards = new ArrayList<C>();
		firstCardsVisible = 0;
		visible = false;
	}
	
	@SafeVarargs
	public CardStack(AuthorizationKey auth, C... initialCards) {
		super(auth);
		cards = new ArrayList<C>(Arrays.asList(initialCards));
		firstCardsVisible = 0;
	}

	//Methods restricted to the Game
	
	public CardStack<C> addCard(AuthorizationKey auth, C card) {
		place(auth, card);
		return this;
	}
	
	public CardStack<C> addCards(AuthorizationKey auth, C[] cards){
		checkAuth(auth);
		this.cards.addAll(Arrays.asList(cards));
		return this;
	}
	
	public ArrayList<C> getCards(AuthorizationKey auth) {
		checkAuth(auth);
		return cards;
	}
	
	public C getCardAtIndex(AuthorizationKey auth, int index) {
		checkAuth(auth);
		return cards.get(index);
	}
	
	public C getBottom(AuthorizationKey auth) {
		checkAuth(auth);
		return cards.get(0);
	}
	
	public C getTop(AuthorizationKey auth) {
		checkAuth(auth);
		return cards.get(cards.size()-1);
	}

	@Override
	public void place(AuthorizationKey auth, C card) {
		checkAuth(auth);
		card.setPosition(this);
		cards.add(card);
	}
	
	@Override
	public void remove(AuthorizationKey auth, C card) {
		checkAuth(auth);
		cards.remove(card);
		card.setPosition(null);
	}
	
	public void onlyRemove(AuthorizationKey auth, C card) {
		checkAuth(auth);
		cards.remove(card);
	}
	
	public void shuffle(AuthorizationKey auth) {
		checkAuth(auth);
		ArrayList<C> cardsCopy = new ArrayList<C>(cards);
		@SuppressWarnings("unchecked")
		C[] newPositions = (C[]) new PlayingCard[cardsCopy.size()];
		for(int i = 0; i < newPositions.length; i++) {
			newPositions[i] = cardsCopy.remove((int) (Math.random() * cardsCopy.size()));
		}
		cards.removeAll(cards);
		cards.addAll(Arrays.asList(newPositions));
	}
	
	public CardStack<C> setFirstCardsVisible(AuthorizationKey auth, int amount) {
		checkAuth(auth);
		firstCardsVisible = amount;
		return this;
	}
	
	public CardStack<C> setCardsVisible(AuthorizationKey auth, boolean areVisible) {
		checkAuth(auth);
		visible = areVisible;
		return this;
	}
	
	//Methods allowed to be used by a PlayerController
	
	public int getSize() {
		return cards.size();
	}
	
	@SuppressWarnings("unchecked")
	public C[] getFirstVisibleCards() {
		return (C[]) cards.subList(cards.size()-firstCardsVisible, cards.size()).toArray();
	}
	
	public ArrayList<C> getVisibleCards() {
		if (visible)
			return getCards(auth);
		return null;
	}

	@Override
	public int getSize(AuthorizationKey key) {
		return getSize();
	}

}
