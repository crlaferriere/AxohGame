package axohEngine2.states;

import axohEngine2.Game;

public class TitleMenuTransition extends StateTransition {

	public TitleMenuTransition(Game game) {
		super(game);
	}

	@Override
	public void load() {
		System.out.println("Loading title menu!");
	}

	@Override
	public void unload() {
		System.out.println("Unloading title menu...");
	}
	
	
	
}
