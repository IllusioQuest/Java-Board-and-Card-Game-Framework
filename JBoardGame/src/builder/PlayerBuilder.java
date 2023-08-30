package builder;

import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;

import game.*;

/**
 * Builder-class for creating multiple {@link Player} objects (with the same type of {@link PlayerController})
 * and {@link GameObject}s which accessibilities are
 * restricted to the corresponding players.
 * This should happen in a static way, starting by
 * calling {@link #create(int)}. After doing so, {@link PlayerController}s can be attached
 * using {@link #setController(PlayerController)} and game objects can be created using
 * {@link #withEach(AuthorizationKey, String, Class)}. FInally, you have to invoke {@link #build()}
 * to get an {@link Addable} that can easily be added to the {@link Game} using Game's <code>add</code> method.
 */
public abstract class PlayerBuilder {
	
	Addable memory;
	int amount;
	
	/**
	 * Starting point for using {@link PlayerBuilder}.
	 * @param amount Amount of players to be created.
	 * @return New {@link PlayerBuilder} instance
	 */
	public static PlayerBuilder create(int amount) {
		InnerPlayerBuilder inner = new InnerPlayerBuilder();
		inner.amount = amount;
		inner.memory = new Addable();
		return inner;
	}
	
	/**
	 * Sets the {@link PlayerController} all players should use.
	 * @param <G> The {@link Game} the controllers will be used for
	 * @param controller The controller to be set
	 */
	public <G extends Game> PlayerBuilder setController(PlayerController<G> controller) {
		Player[] players = new Player[amount];
		for(int i = 0; i < amount; i++) {
			players[i] = new Player(createProxy(controller));
		}
		memory.addPlayers(players);
		return this;
	}
	
	/**
	 * Adds as many {@link GameObject}s as there are players. Each GameObject receives a name,
	 * consisting of the <code>id</code> you provide, followed by the number of the corresponding player.
	 * For example, if you had created the PlayerBuilder via
	 * <pre>
	 * {@code PlayerBuilder.create(3)}
	 * </pre>
	 * and call <code>withEach</code> using the id "tb" and {@link Trackboard} as type:
	 * <pre>
	 * {@code builder.withEach(auth(), "tb", Trackboard.class}
	 * </pre>
	 * Then, three instances of Trackboard are added, having the ids <i>"tb0", "tb1", "tb2"</i>, wheras
	 * tb0 is only visible to the player with index 0, and so on.
	 * @param auth
	 * @param id
	 * @param type
	 */
	public PlayerBuilder withEach(AuthorizationKey auth, String id, Class<? extends GameObject> type) {
		for (Player p : memory.getPlayers()) {

				try {
					GameObject newObj = type.getConstructor(AuthorizationKey.class).newInstance(auth);
					newObj.setRestrictions(auth, new VisibilityRestrictions(p));
					memory.addObject(id + Arrays.asList(memory.getPlayers()).indexOf(p), newObj);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			
		}
		return this;
	}
	
	/**
	 * Short-hand for writing <pre>{@code withEach(auth, null, type)}</pre>
	 * @see #withEach(AuthorizationKey, String, Class)
	 */
	public PlayerBuilder withEach(AuthorizationKey auth, Class<? extends GameObject> type) {
		return withEach(auth, null, type);
	}
	
	/**
	 * 
	 * @return An {@link Addable} consisting of players and game objects.
	 */
	public Addable build() {
		return memory;
	}

	private static PlayerController createProxy(PlayerController original) {
        return (PlayerController) Proxy.newProxyInstance(
                original.getClass().getClassLoader(),
                original.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(original, args);
                    }
                });
    }

	
	private static class InnerPlayerBuilder extends PlayerBuilder {}

}
