package game;

public class GenericPlayingCard<T> extends PlayingCard {
	
	private final T value;

	public GenericPlayingCard(AuthorizationKey auth, Depository<? extends Movable> startingPosition, T value) {
		super(auth, startingPosition);
		this.value = value;
	}
	
	//Methods allowed to be used by a PlayerController
	
	public T getValue(AuthorizationKey auth) {
		checkAuth(auth);
		return value;
	}

}
