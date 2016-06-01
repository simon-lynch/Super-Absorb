package absorb;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Game extends JPanel{
	private Food food;
	private Enemy[] enemys;
	private static Player player;
	
	private String diff;
	
	private static JFrame frame;
	
	public static int FRAME_WIDTH = 600;
	public static int FRAME_HEIGHT = 500;
	
	private int highestScore;
	private int hardScore;
	private int medScore;
	private int easyScore;
	
	public Game() throws InterruptedException{
		Object[] startOptions = {"Easy","Medium","Hard"};
		int n = JOptionPane.showOptionDialog(frame,
				"Select a difficulty",
				"Super Absorb",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				startOptions,
				startOptions[2]);
		diff = "medium";
		if (n == JOptionPane.YES_OPTION) {
			FRAME_WIDTH = 800;
			FRAME_HEIGHT = 800;
			diff = "easy";
		}
		if (n == JOptionPane.CANCEL_OPTION) {
			FRAME_WIDTH = 400;
			FRAME_HEIGHT = 400;
			diff = "hard";
		}
		
		player = new Player();
		
		setBackground(Color.BLACK);
		
		makePlayerBinds();
        
        food = new Food();
        
        enemys = new Enemy[4];
        enemys[0] = new Enemy();
        enemys[1] = new Enemy();
        enemys[2] = new Enemy();
        enemys[3] = new Enemy();
        
        highestScore = 0;
        
        loadFiles();
        
        frame = new JFrame("Super Absorb");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		
		while(true){
			Thread.sleep(10);
			player.move();
			this.repaint();
			enemys[0].targetPlayer();
			enemys[1].targetPlayer();
			enemys[2].targetPlayer();
			enemys[3].targetPlayer();
			this.checkCollisions();
			this.checkScore();
		}
	}
	private void makePlayerBinds() {
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RightArrow");
		am.put("RightArrow", new ActionHandler("RightArrow"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "RightArrowRelease");
		am.put("RightArrowRelease", new ActionHandler("RightArrowRelease"));
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DownArrow");
		am.put("DownArrow", new ActionHandler("DownArrow"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "DownArrowRelease");
		am.put("DownArrowRelease", new ActionHandler("DownArrowRelease"));
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftArrow");
		am.put("LeftArrow", new ActionHandler("LeftArrow"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "LeftArrowRelease");
		am.put("LeftArrowRelease", new ActionHandler("LeftArrowRelease"));
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UpArrow");
		am.put("UpArrow", new ActionHandler("UpArrow"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "UpArrowRelease");
		am.put("UpArrowRelease", new ActionHandler("UpArrowRelease"));
		
	}
	public static Player getPlayer() {
		return player;
	}
	private void loadFiles() {
		String line = null;
        FileReader fr = null;
		try {
			fr = new FileReader("highscore.txt");
		} catch (FileNotFoundException e) {
			File file = new File("highscore.txt");
		    FileWriter writer = null;
		    try {
		        writer = new FileWriter(file);
		        writer.write("0 0 0");
		    } catch (IOException ioe) {
		        e.printStackTrace();
		    } finally {
		        if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		    }
		    System.out.printf("File is located at %s%n", file.getAbsolutePath());
		}
        BufferedReader br = new BufferedReader(fr);
        try {
			line = br.readLine();
			br.close();
		} catch (NumberFormatException e) {
			System.out.println("Highscore file bad");
		} catch (IOException e) {
			System.out.println("Highscore file bad");
		}
        String[] broke = line.split("\\ ");
        if (diff.equals("easy")) {
        	highestScore = Integer.parseInt(broke[0]);
        }
        else if (diff.equals("medium")) {
        	highestScore = Integer.parseInt(broke[1]);
        }
        else {
        	highestScore = Integer.parseInt(broke[2]);
        }
        easyScore = Integer.parseInt(broke[0]);
        medScore = Integer.parseInt(broke[1]);
        hardScore = Integer.parseInt(broke[2]);
	}
	public static int[] getPlayerCoords() {
		return player.getCoords();
	}
	public void checkCollisions() {
		if (player.getHitbox().intersects(food.getHitbox())) {
			player.incrementSize(food.getSize());
			food = new Food();
		}
		if (player.getHitbox().intersects(enemys[0].getHitbox()) || player.getHitbox().intersects(enemys[1].getHitbox()) || player.getHitbox().intersects(enemys[2].getHitbox()) || player.getHitbox().intersects(enemys[3].getHitbox())) {
			gameOver();
		}
	}
	public void gameOver() {
		int choice = JOptionPane.showOptionDialog(frame, "You lose! New game?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (choice == JOptionPane.YES_OPTION) {
			player = new Player();
			enemys[0] = new Enemy();
			enemys[1] = new Enemy();
			enemys[2] = new Enemy();
			enemys[3] = new Enemy();
		}
		else {
			if (diff.equals("easy")) {
				easyScore = highestScore;
			}
			else if (diff.equals("medium")) {
				medScore = highestScore;
			}
			else if (diff.equals("hard")) {
				hardScore = highestScore;
			}
			String writing = easyScore + " " + medScore + " " + hardScore;
		    File file = new File("highscore.txt");
		    FileWriter writer = null;
		    try {
		        writer = new FileWriter(file);
		        writer.write(writing);
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		    }
		    System.out.printf("File is located at %s%n", file.getAbsolutePath());
			System.exit(0);
		}
	}
	public void checkScore() {
		if (highestScore < player.getWidth()) {
			highestScore = player.getWidth();
		}
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		RenderingHints hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(hints);
		super.paintComponent(g);
		player.drawPlayer(g2d);
		enemys[0].drawEnemy(g2d);
		enemys[1].drawEnemy(g2d);
		enemys[2].drawEnemy(g2d);
		enemys[3].drawEnemy(g2d);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("DialogInput", Font.BOLD, 30));
		g2d.drawString((Integer.toString(player.getWidth())), (FRAME_WIDTH / 2 - 15), (FRAME_HEIGHT - 35));
		g2d.drawString("High Score: " + Integer.toString(highestScore), (FRAME_WIDTH / 2 - 130), 20);
		food.drawFood(g2d);
	}
}