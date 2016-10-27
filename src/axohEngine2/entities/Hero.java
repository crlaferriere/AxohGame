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
		// int x = (int) position.getX() - (int) game.camera.getX() + game.CENTERX;
		// int y = (int) position.getY() - (int) game.camera.getY() + game.CENTERY;
		int x = (int) getPosition().getX() - (int) game.camera.getX();
		int y = (int) getPosition().getY() - (int) game.camera.getY();
		g2d.drawImage(getImage(), x, y, getSpriteSize(), getSpriteSize(), game);
		g2d.setColor(Color.red);
		g2d.drawRect(x, y, getSpriteSize(), getSpriteSize());
		g2d.setColor(Color.green);
		g2d.drawRect(0, 0, 0, 0);
		//g2d.drawImage(getImage(), (int) position.getX() - (int) game.camera.getX(), (int) position.getY() - (int) game.camera.getY(), getSpriteSize(), getSpriteSize(), game);
		//position.setX(x);
		//position.setY(y);
	}

}
