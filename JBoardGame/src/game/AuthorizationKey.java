package game;

public class AuthorizationKey {
	
	private AuthorizationKey() {}
	
	static AuthorizationKey generateKey() {
		return new AuthorizationKey();
	}

}
