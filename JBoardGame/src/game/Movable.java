package game;

public abstract class Movable extends GameObject {

	Depository currPosition;
	
	public Movable(AuthorizationKey auth, Depository startingPosition) {
		super(auth);
		currPosition = startingPosition;
	}
	
	void moveTo(Depository depository) {
		if (depository != currPosition) {
			currPosition.remove(auth, this);
			depository.place(auth, this);
			this.currPosition = depository;
		}
	}
	
	void setPosition(Depository depository) {
		if (depository != currPosition) {
			currPosition.onlyRemove(auth, this);
			this.currPosition = depository;
		}
	}

}

//Note: I'm still unsatisfied with this class and the whole system "Movable - Depository". I'm happy to receive 
//      suggestions on that and ensure type safety.