package axohEngine2.states;

import axohEngine2.Game;

public class GameMenuTransition extends StateTransition {

	public GameMenuTransition(Game game) {
		super(game);
	}

	@Override
	public void load() {
		System.out.println("Loading game menu!");
	}

	@Override
	public void unload() {
		System.out.println("Unloading game menu...");
	}
	
	
	
}
