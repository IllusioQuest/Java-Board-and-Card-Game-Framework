package game;

import java.util.HashSet;

public abstract class Dealer<M extends Movable, D extends Depository<M>> {
	
	private HashSet<D> origins = new HashSet<D>();
	private AuthorizationKey auth;
	
	public Dealer<M, D> authorize(AuthorizationKey auth) {
		this.auth = auth;
		return this;
	}
	
	public Dealer<M, D> take(D depository) {
		origins.add(depository);
		return this;
	}
	
	/*@SuppressWarnings("unchecked")
	protected abstract <T extends Depository<? extends M>> Dealer<M> handOut(AuthorizationKey auth, HashSet<T> origins, int amountEach, T... depositories);
	
	@SuppressWarnings("unchecked")
	public <T extends Depository<? extends M>> Dealer<M> handOut(int amountEach, T... depositories) {
		return handOut(auth, origins, amountEach, depositories);
	}*/
	
	@SuppressWarnings("unchecked")
	protected abstract Dealer<M, D> handOut(AuthorizationKey auth, HashSet<D> origins, int amountEach, D... depositories);
	
	@SuppressWarnings("unchecked")
	public Dealer<M, D> handOut(int amountEach, D... depositories) {
		return handOut(auth, origins, amountEach, depositories);
	}

}
