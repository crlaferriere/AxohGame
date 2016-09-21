package axohEngine2.states;

import axohEngine2.Game;

public abstract class StateTransition {
	
	protected Game game;
	
	public StateTransition(Game game) {
		this.game = game;
	}

	public abstract void load();
	
	public abstract void unload();
	
}
