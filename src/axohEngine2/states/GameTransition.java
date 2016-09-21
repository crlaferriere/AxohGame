package axohEngine2.states;

import axohEngine2.Game;

public class GameTransition extends StateTransition {

	public GameTransition(Game game) {
		super(game);
	}

	@Override
	public void load() {
		System.out.println("Loading game!");
		game.loadGame();
	}

	@Override
	public void unload() {
		System.out.println("Unloading game...");
	}
	
	
	
}
