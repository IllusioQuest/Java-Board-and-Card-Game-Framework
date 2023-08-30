package example3_cards;

public class MinimalExample {
	
	public class Movable {}
	
	public class PlayingCard extends Movable {}
	
	public class GenericPlayingCard<T> extends PlayingCard {}
	
	public interface Depository<T extends Movable> {}
	
	public class CardStack<C extends PlayingCard> implements Depository<C> {}
	
	public abstract class Dealer<M extends Movable, D extends Depository<M>> {
		
		public abstract Dealer<M, D> bar(D... ds);
		
		public Dealer<M, D> foo(D... ds) {
			return bar(ds);
		}
		
	}
	
	public class CardDealer<C extends PlayingCard> extends Dealer<C, CardStack<C>> {

		@Override
		public Dealer<C, CardStack<C>> bar(CardStack<C>... ds) {
			return this;
		}
	
	}
	
	public static void main(String[] args) {
		new MinimalExample().new CardDealer<GenericPlayingCard<Integer>>().foo(null);
	}

}
