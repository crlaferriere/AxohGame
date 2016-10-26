package axohEngine2.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import axohEngine2.Judgement;
import axohEngine2.project.TYPE;

public class Hero extends Mob {

	public Hero(Judgement game, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, String name) {
		super(game, g2d, sheet, spriteNumber, TYPE.PLAYER, name);
	}
	
	@Override
	public void renderMob() {
		super.renderMob();
		g2d.setColor(Color.green);
		g2d.fillRect(0, 0, 1, 1);
	}

}
