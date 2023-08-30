package builder;

import java.util.ArrayList;
import java.util.List;

import game.*;

/**
 * GenericCardStackBuilders are used to easily create an instance of {@link CardStack}&lt;{@link GenericPlayingCard}&lt;X&gt&gt;; 
 * with various parameters. The methods should be accessed staticly, starting with
 * {@link #startByType}. Therefore, the {@link AuthorizationKey} is needed and the generic type parameter, the
 * playing cards are supposed to have.  After calling all methods needed, the <code>CardStack</code> can be obtained using
 * {@link #build()}.
 * @param <X> Generic type parameter of the <code>GenericPlayingCard</code>s
 */
public abstract class GenericCardStackBuilder<X> {
	
	protected AuthorizationKey auth;
	
	/**
	 * Adds a single {@link GenericPlayingCard} with given value to the stack.
	 * @param Card's value
	 */
	public abstract GenericCardStackBuilder<X> add(X value);
	
	/**
	 * Adds a single {@link GenericPlayingCard} to the stack.
	 * @param Card to add
	 */
	public abstract GenericCardStackBuilder<X> add(GenericPlayingCard<X> card);
	
	/**
	 * Adds multiple {@link GenericPlayingCard}s with given values to the stack.
	 * @param Cards' values
	 */
	public abstract GenericCardStackBuilder<X> values(X... values);
	
	/**
	 * Adds multiple {@link GenericPlayingCard}s to the stack.
	 * @param Cards to add
	 */
	public abstract GenericCardStackBuilder<X> cards(GenericPlayingCard<X>... cards);
	
	/**
	 * Forces the builder to shuffle all cards when building, i. e. arranging them in random order.
	 */
	public abstract GenericCardStackBuilder<X> shuffle();
	
	/**
	 * This method allows copying all cards in the stack multiple times in a way that c1 != c2 but the cards do
	 * have the same value. The multiplication is enabled for cards already in the stack during invocation as well
	 * as for the cards added later on. Multiple calls of multiply do not result in multiplying the copies.
	 * @param multiplicator the amount of cards with same value which shall be in the stack
	 */
	public abstract GenericCardStackBuilder<X> multiply(int multiplicator);
	
	/**
	 * @return The pre-defined CardStack
	 */
	public abstract CardStack<GenericPlayingCard<X>> build();
	
	/**
	 * @param <T> Generic type parameter of the {@linkplain GenericPlayingCard Generic Playing Cards}
	 * @param c Type parameter's runtime class
	 * @return A new CardStackBuilder to be used with the given type
	 */
	public static <T> GenericCardStackBuilder<T> startByType(AuthorizationKey auth, Class<T> c) {
		return new InnerCardStackBuilder<T>(auth);
	}
	
	/**
	 * Short-hand for invoking all methods of {@link GenericCardStackBuilder} successively.
	 * @param <T> Type parameter of the {@link GenericPlayingCard}s
	 * @param key {@link AuthorizationKey}
	 * @param shuffled True, if {@link #shuffle()} shall be invoked
	 * @param multiplicator Multiplier of {@link #multiply(int)}
	 * @param ts Playing Cards' values
	 * @return {@link CardStack} containing the defined cards
	 */
	public static <T> CardStack<GenericPlayingCard<T>> create(AuthorizationKey key, boolean shuffled, int multiplicator, T... ts) {
		InnerCardStackBuilder<T> builder = new InnerCardStackBuilder<T>(key).multiply(multiplicator).values(ts);
		if (shuffled)
			builder.shuffle();
		return builder.build();
	}
	
	private static class InnerCardStackBuilder<T> extends GenericCardStackBuilder<T> {
		
		private ArrayList<GenericPlayingCard<T>> cards;
		private CardStack<GenericPlayingCard<T>> stack;
		
		private boolean shuffle = false;
		private int multiplicator = 1;
		
		public InnerCardStackBuilder(AuthorizationKey auth) {
			this.auth = auth;
			cards = new ArrayList<GenericPlayingCard<T>>();
			stack = new CardStack<GenericPlayingCard<T>>(auth);
		}
		
		@Override
		public InnerCardStackBuilder<T> add(T value) {
			GenericPlayingCard<T> c = new GenericPlayingCard<T>(auth, stack, value);
			cards.add(c);
			stack.addCard(auth, c);
			return this;
		}
		
		@Override
		public InnerCardStackBuilder<T> add(GenericPlayingCard<T> card) {
			stack.addCard(auth, card);
			cards.add(card);
			return this;
		}
		
		@Override
		public InnerCardStackBuilder<T> values(T... values) {
			for (T value : values) {
				add(value);
			}
			return this;
		}
		
		@Override
		public InnerCardStackBuilder<T> cards(GenericPlayingCard<T>... cards) {
			stack.addCards(auth, cards);
			this.cards.addAll(List.of(cards));
			return this;
		}
		
		@Override
		public InnerCardStackBuilder<T> shuffle() {
			shuffle = true;
			return this;
		}
		
		@Override
		public InnerCardStackBuilder<T> multiply(int multiplicator) {
			if (multiplicator > 0)
				this.multiplicator = multiplicator;
			else
				this.multiplicator = 1;
			return this;
		}
		
		@Override
		public CardStack<GenericPlayingCard<T>> build() {
			if (multiplicator > 1) {
				for (GenericPlayingCard<T> card : cards) {
					for (int i = 1; i < multiplicator; i++) {
						stack.addCard(auth, new GenericPlayingCard<T>(auth, stack, card.getValue(auth)));
					}
				}
			}
			if (shuffle)
				stack.shuffle(auth);
			return stack;
		}
		
	}

}
