package game;

import java.util.ArrayList;

public class CardHand<C extends PlayingCard> extends CardStack<C> {

	public CardHand(AuthorizationKey auth) {
		super(auth);
		visible = true;
	}
	
	@SafeVarargs
	public CardHand(AuthorizationKey auth, C... initialCards) {
		super(auth, initialCards);
		visible = true;
	}
	
	public ArrayList<C> getCards() {
		if (visible)
			return getCards(auth);
		return null;
	}
	
	public C getCardAtIndex(int index) {
		if (visible)
			return getCardAtIndex(auth, index);
		return null;
	}
	
	public C getBottom() {
		if (visible)
			return getBottom(auth);
		return null;
	}
	
	public C getTop() {
		if (visible)
			return getTop(auth);
		return null;
	}

}
