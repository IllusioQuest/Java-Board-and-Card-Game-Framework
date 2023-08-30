package game;

public abstract class GameObject {
	
	protected final AuthorizationKey auth;
	
	public GameObject(AuthorizationKey auth) {
		this.auth = auth;
		restrictions = new VisibilityRestrictions(true);
	}
	
	public GameObject(AuthorizationKey auth, VisibilityRestrictions restrictions) {
		this.auth = auth;
		this.restrictions = restrictions;
	}
	
	protected void checkAuth(AuthorizationKey auth) {
		if (this.auth != auth) {
			throw new UnauthorizedException();
		}
	}
	
	protected VisibilityRestrictions restrictions;
	
	protected GameObject restricted (Player player) {
		if (restrictions.visibleTo(player))
			return this;
		else
			return null;
	}

	protected void endInitialization() {};
	
	public void setRestrictions(AuthorizationKey auth, VisibilityRestrictions restrictions) {
		checkAuth(auth);
		this.restrictions = restrictions;
	}

}
