package game;

/**
 * Interface which should be inplemented by enums only. A TurnDescription is passed to a {@link PlayerController} 
 * when the controller is supposed to execute a turn. In many games, there are different scenarios in which a player
 * has to decide what to do. By defining multiple enum constants, the PlayerController is able to check what it has to
 * do and in which phase of a game it is positioned. Considering e.g. the game Ludo, TurnDescription might be implemented
 * by an enum with the values WALK and START_FROM_BASE, meaning that the player either has to choose a player to walk with
 * or choose a player to start with at the starting field.
 */
public interface TurnDescription {

}
