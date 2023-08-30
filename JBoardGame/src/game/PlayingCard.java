package game;

public abstract class PlayingCard extends Movable {

	public PlayingCard(AuthorizationKey auth, Depository<? extends Movable> startingPosition) {
		super(auth, startingPosition);
	} 

}
