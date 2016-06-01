package absorb;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class ActionHandler extends AbstractAction{
	private String cmd;
	public ActionHandler(String cmd) {
		this.cmd = cmd;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(cmd.equals("RightArrow")) {
			Game.getPlayer().moveRight();
		}
		if (cmd.equals("RightArrowRelease")) {
			Game.getPlayer().stopMoveRight();
		}
		if (cmd.equals("DownArrow")) {
			Game.getPlayer().moveDown();
		}
		if (cmd.equals("DownArrowRelease")) {
			Game.getPlayer().stopMoveDown();
		}
		if(cmd.equals("LeftArrow")) {
			Game.getPlayer().moveLeft();
		}
		if (cmd.equals("LeftArrowRelease")) {
			Game.getPlayer().stopMoveLeft();
		}
		if (cmd.equals("UpArrow")) {
			Game.getPlayer().moveUp();
		}
		if (cmd.equals("UpArrowRelease")) {
			Game.getPlayer().stopMoveUp();
		}
	}
}
