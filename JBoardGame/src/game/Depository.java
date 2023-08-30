package game;

public interface Depository<T extends Movable> {
	
	public void place(AuthorizationKey key, T object);
	
	public void remove(AuthorizationKey key, T object);
	
	public int getSize(AuthorizationKey key);

	void onlyRemove(AuthorizationKey auth, T object);

}
