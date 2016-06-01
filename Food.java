package absorb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Food{
	private int x;
	private int y;
	private int size;
	private Rectangle hitbox;
	public Food() {
		x = (int) (Math.random() * (Game.FRAME_WIDTH - 40)) + 20;
		y = (int) (Math.random() * (Game.FRAME_HEIGHT - 70)) + 20;
		size = (int) (Math.random() * 9) + 5;
		hitbox = new Rectangle(x, y, size, size);
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public void drawFood(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillOval(x, y, size, size);
	}
	public int[] getCoords() {
		int[] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
	public int getSize() {
		return size;
	}
}
