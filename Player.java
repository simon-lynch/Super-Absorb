package absorb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Player extends AbstractAction{
	private int x;
	private int y;
	private int xv;
	private int yv;
	private int size;
	private int speed;
	private float scoreSubtract;
	private Rectangle hitbox;
	private String cmd;
	JFrame frame = new JFrame();
	public Player() {
		speed = 4;
		size = 25;
		x = Game.FRAME_WIDTH / 2;
		y = (int) (Game.FRAME_HEIGHT / 2f);
		xv = 0;
		yv = 0;
		hitbox = new Rectangle(x, y, size, size);
	}
	public int[] getCoords() {
		int[] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
	public Rectangle getHitbox() {
		hitbox = new Rectangle(x, y, size, size);
		return hitbox;
	}
	public int getWidth() {
		return size;
	}
	public void move() {
		if (size > 0) {
			speed = 4;
		}
		if (size > 50) {
			speed = 3;
		}
		if (size > 100) {
			speed = 2;
		}
		if (size > 200) {
			speed = 1;
		}
		if ((x + size) > Game.FRAME_WIDTH) {
			xv = 0;
			x += -1;
		}
		if (x < 0) {
			xv = 0;
			x += 1;
		}
		if ((y + size) > (Game.FRAME_HEIGHT - 30)) {
			yv = 0;
			y += -1;
		}
		if (y < 0) {
			yv = 0;
			y += 1;
		}
		x += xv;
		y += yv;
		if (yv != 0 || xv != 0) {
			decreaseSize();
		}
	}
	private void decreaseSize() {
		scoreSubtract += 0.03125;
		if (scoreSubtract == 1) {
			size -= scoreSubtract;
			scoreSubtract = 0;
		}
		if (size < 1) {
			int choice = JOptionPane.showOptionDialog(frame, "You lose! New game?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (choice == JOptionPane.YES_OPTION) {
				size = 25;
				x = Game.FRAME_WIDTH / 2;
				y = (int) (Game.FRAME_HEIGHT / 2);
				xv = 0;
				yv = 0;
				hitbox = new Rectangle(x, y, size, size);
			}
			else {
				System.exit(0);
			}
		}
	}
	public void actionPerformed(ActionEvent ae) {
		if (cmd.equals("RightArrow")) {
			System.out.println("Success");
		}
	}
	public void moveRight() {
		xv = speed;
	}
	public void moveLeft() {
		xv = -speed;
	}
	public void moveUp() {
		yv = -speed;
	}
	public void moveDown() {
		yv = speed;
	}
	public void drawPlayer(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.drawRect(x, y, size, size);
		
	}
	public void incrementSize(int i) {
		size += i;
	}
	public void setAction(String string) {
		cmd = string;
	}
	public void stopMoveRight() {
		xv = 0;
	}
	public void stopMoveDown() {
		yv = 0;
	}
	public void stopMoveLeft() {
		xv = 0;
	}
	public void stopMoveUp() {
		yv = 0;
	}
}
