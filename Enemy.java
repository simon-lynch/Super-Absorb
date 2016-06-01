package absorb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {
	private int x;
	private int y;
	private int xv;
	private int yv;
	private int size;
	private Rectangle hitbox;
	public Enemy() {
		size = 15;
		x = (int) (Math.random() * (Game.FRAME_WIDTH - 40)) + 20;
		y = (int) (Math.random() * ((Game.FRAME_HEIGHT - 30) - 40)) + 20;
		hitbox = new Rectangle(x, y, size, size);
		xv = 2;
		yv = 2;
		int[] playerCoords = Game.getPlayerCoords();
		if (x < playerCoords[0])
			xv = xv * -1;
		if (y < playerCoords[1])
			yv = yv * -1;
	}
	public void drawEnemy(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.fillOval(x, y, size, size);
	}
	public Rectangle getHitbox() {
		hitbox = new Rectangle(x, y, size, size);
		return hitbox;
	}
	public void dontMove() {
		x += 0;
		y +=0;
	}
	public void targetPlayer() {
		if ((x + size) > Game.FRAME_WIDTH) {
			xv = -2;
		}
		if (x < 0) {
			xv = 2;
		}
		if ((y + size) > (Game.FRAME_HEIGHT - 30)) {
			yv = -2;
		}
		if (y < 0) {
			yv = 2;
		}
		x += xv;
		y += yv;
	}
	public int[] getCoords() {
		int[] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
}
