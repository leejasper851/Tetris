import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

class Surface extends JPanel implements ActionListener {
	private String[] blocks = new String[20];
	private String[] colors = new String[20];
	private String currentBlock;
	private int currentRotate;
	private char currentColor;
	private String state = "new";
	private Timer timer = new Timer(500, this);
	
	public Surface() {
		setFocusable(true);
		addKeyListener(new TetrisKeyAdapter());
		addMouseListener(new TetrisMouseAdapter());
		addMouseMotionListener(new TetrisMouseAdapter());
		init();
	}
		
	private void init() {
		for (int i = 0; i < 20; i++) {
			blocks[i] = "0000000000";
			colors[i] = "0000000000";
		}
		timer.start();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new Color(0, 0, 0));
		g2d.fillRect(0, 0, 300, 600);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (blocks[i].charAt(j) != '0') {
					switch (colors[i].charAt(j)) {
						case '0':
							g2d.setPaint(new Color(0, 0, 255));
							break;
						case '1':
							g2d.setPaint(new Color(255, 165, 0));
							break;
						case '2':
							g2d.setPaint(new Color(128, 0, 128));
							break;
						case '3':
							g2d.setPaint(new Color(255, 0, 0));
							break;
						case '4':
							g2d.setPaint(new Color(0, 255, 0));
							break;
						case '5':
							g2d.setPaint(new Color(0, 255, 255));
							break;
						default:
							g2d.setPaint(new Color(255, 255, 0));
							break;
					}
					g2d.fillRect(j*30, i*30, 30, 30);
				}
				g2d.setPaint(new Color(128, 128, 128));
				g2d.drawRect(j*30, i*30, 30, 30);
			}
		}
		if (state == "game over") {
			g2d.setPaint(new Color(0, 0, 0));
			g2d.fillRect(50, 220, 200, 100);
			g2d.setPaint(new Color(255, 255, 255));
			g2d.drawRect(50, 220, 200, 100);
			g2d.setFont(new Font("Dialog", Font.PLAIN, 30));
			g2d.drawString("Game Over!", 150 - g2d.getFontMetrics().stringWidth("Game Over!")/2, 255);
			g2d.drawRect(85, 267, 130, 40);
			g2d.setFont(new Font("Dialong", Font.PLAIN, 25));
			g2d.drawString("Restart", 150 - g2d.getFontMetrics().stringWidth("Restart")/2, 296);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (state == "play") {
			goDown();
		}
		if (state == "new") {
			newBlock();
		}
		repaint();
	}
	
	private void newBlock() {
		Random rand = new Random();
		int num = rand.nextInt(7);
		switch (num) {
			case 0:
				leftBlock();
				break;
			case 1:
				rightBlock();
				break;
			case 2:
				middleBlock();
				break;
			case 3:
				leftLeanBlock();
				break;
			case 4:
				rightLeanBlock();
				break;
			case 5:
				longBlock();
				break;
			default:
				squareBlock();
				break;
		}
		if (state != "game over") {
			state = "play";
		}
		currentRotate = 0;
	}
	
	private void leftBlock() {
		if (blocks[0].charAt(3) == '2' || blocks[1].charAt(3) == '2' || blocks[1].charAt(4) == '2' || blocks[1].charAt(5) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 3, '1');
		blocksStringChange(1, 3, '1');
		blocksStringChange(1, 4, '1');
		blocksStringChange(1, 5, '1');
		colorsStringChange(0, 3, '0');
		colorsStringChange(1, 3, '0');
		colorsStringChange(1, 4, '0');
		colorsStringChange(1, 5, '0');
		currentBlock = "left";
		currentColor = '0';
	}
	
	private void rightBlock() {
		if (blocks[0].charAt(5) == '2' || blocks[1].charAt(3) == '2' || blocks[1].charAt(4) == '2' || blocks[1].charAt(5) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 5, '1');
		blocksStringChange(1, 3, '1');
		blocksStringChange(1, 4, '1');
		blocksStringChange(1, 5, '1');
		colorsStringChange(0, 5, '1');
		colorsStringChange(1, 3, '1');
		colorsStringChange(1, 4, '1');
		colorsStringChange(1, 5, '1');
		currentBlock = "right";
		currentColor = '1';
	}
	
	private void middleBlock() {
		if (blocks[0].charAt(4) == '2' || blocks[1].charAt(3) == '2' || blocks[1].charAt(4) == '2' || blocks[1].charAt(5) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 4, '1');
		blocksStringChange(1, 3, '1');
		blocksStringChange(1, 4, '1');
		blocksStringChange(1, 5, '1');
		colorsStringChange(0, 4, '2');
		colorsStringChange(1, 3, '2');
		colorsStringChange(1, 4, '2');
		colorsStringChange(1, 5, '2');
		currentBlock = "middle";
		currentColor = '2';
	}
	
	private void leftLeanBlock() {
		if (blocks[0].charAt(3) == '2' || blocks[0].charAt(4) == '2' || blocks[1].charAt(4) == '2' || blocks[1].charAt(5) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 3, '1');
		blocksStringChange(0, 4, '1');
		blocksStringChange(1, 4, '1');
		blocksStringChange(1, 5, '1');
		colorsStringChange(0, 3, '3');
		colorsStringChange(0, 4, '3');
		colorsStringChange(1, 4, '3');
		colorsStringChange(1, 5, '3');
		currentBlock = "left lean";
		currentColor = '3';
	}
	
	private void rightLeanBlock() {
		if (blocks[0].charAt(4) == '2' || blocks[0].charAt(5) == '2' || blocks[1].charAt(3) == '2' || blocks[1].charAt(4) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 4, '1');
		blocksStringChange(0, 5, '1');
		blocksStringChange(1, 3, '1');
		blocksStringChange(1, 4, '1');
		colorsStringChange(0, 4, '4');
		colorsStringChange(0, 5, '4');
		colorsStringChange(1, 3, '4');
		colorsStringChange(1, 4, '4');
		currentBlock = "right lean";
		currentColor = '4';
	}
	
	private void longBlock() {
		if (blocks[0].charAt(3) == '2' || blocks[0].charAt(4) == '2' || blocks[0].charAt(5) == '2' || blocks[0].charAt(6) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 3, '1');
		blocksStringChange(0, 4, '1');
		blocksStringChange(0, 5, '1');
		blocksStringChange(0, 6, '1');
		colorsStringChange(0, 3, '5');
		colorsStringChange(0, 4, '5');
		colorsStringChange(0, 5, '5');
		colorsStringChange(0, 6, '5');
		currentBlock = "long";
		currentColor = '5';
	}
	
	private void squareBlock() {
		if (blocks[0].charAt(4) == '2' || blocks[0].charAt(5) == '2' || blocks[1].charAt(4) == '2' || blocks[1].charAt(5) == '2') {
			state = "game over";
			timer.stop();
			repaint();
			return;
		}
		blocksStringChange(0, 4, '1');
		blocksStringChange(0, 5, '1');
		blocksStringChange(1, 4, '1');
		blocksStringChange(1, 5, '1');
		colorsStringChange(0, 4, '6');
		colorsStringChange(0, 5, '6');
		colorsStringChange(1, 4, '6');
		colorsStringChange(1, 5, '6');
		currentBlock = "square";
		currentColor = '6';
	}
	
	private class TetrisKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_DOWN || key == 's' || key == 'S') && state == "play") {
				goDown();
			}
			if ((key == KeyEvent.VK_LEFT || key == 'a' || key == 'A') && state == "play") {
				goLeft();
			}
			if ((key == KeyEvent.VK_RIGHT || key == 'd' || key == 'D') && state == "play") {
				goRight();
			}
			if ((key == KeyEvent.VK_UP || key == 'w' || key == 'W' || key == 'z' || key == 'Z') && state == "play") {
				rotate();
			}
			repaint();
		}
	}
	
	private void goDown() {
		if (canDown()) {
			for (int i = 19; i > -1; i--) {
				for (int j = 0; j < 10; j++) {
					if (blocks[i].charAt(j) == '1') {
						blocksStringChange(i, j, '0');
						blocksStringChange(i+1, j, '1');
						colorsStringChange(i, j, '0');
						colorsStringChange(i+1, j, currentColor);
					}
				}
			}
		} else {
			solidify();
			clearRows();
		}
	}
	
	private boolean canDown() {
		for (int i = 0; i < 10; i++) {
			for (int j = 19; j > -1; j--) {
				if (blocks[j].charAt(i) == '1') {
					if (j == 19 || blocks[j+1].charAt(i) == '2') {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	private void goLeft() {
		if (canLeft()) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 20; j++) {
					if (blocks[j].charAt(i) == '1') {
						blocksStringChange(j, i, '0');
						blocksStringChange(j, i-1, '1');
						colorsStringChange(j, i, '0');
						colorsStringChange(j, i-1, currentColor);
					}
				}
			}
		}
	}
	
	private boolean canLeft() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (blocks[i].charAt(j) == '1') {
					if (j == 0 || blocks[i].charAt(j-1) == '2') {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	private void goRight() {
		if (canRight()) {
			for (int i = 9; i > -1; i--) {
				for (int j = 0; j < 20; j++) {
					if (blocks[j].charAt(i) == '1') {
						blocksStringChange(j, i, '0');
						blocksStringChange(j, i+1, '1');
						colorsStringChange(j, i, '0');
						colorsStringChange(j, i+1, currentColor);
					}
				}
			}
		}
	}
	
	private boolean canRight() {
		for (int i = 0; i < 20; i++) {
			for (int j = 9; j > -1; j--) {
				if (blocks[i].charAt(j) == '1') {
					if (j == 9 || blocks[i].charAt(j+1) == '2') {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	private void rotate() {
		int[] coords = findCoords();
		int[][] relativeCoords = getRelativeCoords();
		if (canRotate(coords, relativeCoords)) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 10; j++) {
					if (blocks[i].charAt(j) == '1') {
						blocksStringChange(i, j, '0');
					}
				}
			}
			blocksStringChange(coords[0], coords[1], '1');
			for (int i = 0; i < 3; i++) {
				blocksStringChange(coords[0]+relativeCoords[i][0], coords[1]+relativeCoords[i][1], '1');
				colorsStringChange(coords[0]+relativeCoords[i][0], coords[1]+relativeCoords[i][1], currentColor);
			}
			currentRotate = (currentRotate+1) % 4;
		}
	}
	
	private int[] findCoords() {
		if (currentBlock == "left" || currentBlock == "right" || currentBlock == "middle") {
			if (currentRotate == 0 || currentRotate == 2) {
				for (int i = 0; i < 20; i++) {
					int count = 0;
					for (int j = 0; j < 10; j++) {
						if (blocks[i].charAt(j) == '1') {
							count++;
							if (count == 2) {
								int[] coords = {i, j};
								return coords;
							}
						}
					}
				}
			} else {
				for (int i = 0; i < 10; i++) {
					int count = 0;
					for (int j = 0; j < 20; j++) {
						if (blocks[j].charAt(i) == '1') {
							count++;
							if (count == 2) {
								int[] coords = {j, i};
								return coords;
							}
						}
					}
				}
			}
		} else if (currentBlock == "left lean" || currentBlock == "right lean") {
			int order;
			if (currentRotate == 0 || currentRotate == 3) {
				order = 2;
			} else {
				order = 1;
			}
			if (currentRotate == 0 || currentRotate == 2) {
				for (int i = 0; i < 10; i++) {
					int count = 0;
					for (int j = 0; j < 20; j++) {
						if (blocks[j].charAt(i) == '1') {
							count++;
							if (count == order) {
								int[] coords = {j, i};
								return coords;
							}
						}
					}
				}
			} else {
				for (int i = 0; i < 20; i++) {
					int count = 0;
					for (int j = 0; j < 10; j++) {
						if (blocks[i].charAt(j) == '1') {
							count++;
							if (count == order) {
								int[] coords = {i, j};
								return coords;
							}
						}
					}
				}
			}
		} else if (currentBlock == "long") {
			int order;
			if (currentRotate == 0 || currentRotate == 1) {
				order = 3;
			} else {
				order = 2;
			}
			if (currentRotate == 0 || currentRotate == 2) {
				for (int i = 0; i < 20; i++) {
					int count = 0;
					for (int j = 0; j < 10; j++) {
						if (blocks[i].charAt(j) == '1') {
							count++;
							if (count == order) {
								int[] coords = {i, j};
								return coords;
							}
						}
					}
				}
			} else {
				for (int i = 0; i < 10; i++) {
					int count = 0;
					for (int j = 0; j < 20; j++) {
						if (blocks[j].charAt(i) == '1') {
							count++;
							if (count == order) {
								int[] coords = {j, i};
								return coords;
							}
						}
					}
				}
			}
		}
		return new int[2];
	}
	
	private int[][] getRelativeCoords() {
		switch (currentBlock) {
			case "left":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,0},{-1,1},{1,0}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,-1},{0,1},{1,1}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-1,0},{1,-1},{1,0}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{-1,-1},{0,-1},{0,1}};
						return relativeCoords3;
				}
			case "right":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,0},{1,0},{1,1}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,-1},{0,1},{1,-1}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-1,-1},{-1,0},{1,0}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{-1,1},{0,-1},{0,1}};
						return relativeCoords3;
				}
			case "middle":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,0},{0,1},{1,0}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,-1},{0,1},{1,0}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-1,0},{0,-1},{1,0}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{-1,0},{0,-1},{0,1}};
						return relativeCoords3;
				}
			case "left lean":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,1},{0,1},{1,0}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,-1},{1,0},{1,1}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-1,0},{0,-1},{1,-1}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{-1,-1},{-1,0},{0,1}};
						return relativeCoords3;
				}
			case "right lean":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,0},{0,1},{1,1}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,1},{1,-1},{1,0}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-1,-1},{0,-1},{1,0}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{-1,1},{-1,0},{0,-1}};
						return relativeCoords3;
				}
			case "long":
				switch (currentRotate) {
					case 0:
						int[][] relativeCoords0 = {{-1,0},{1,0},{2,0}};
						return relativeCoords0;
					case 1:
						int[][] relativeCoords1 = {{0,-2},{0,-1},{0,1}};
						return relativeCoords1;
					case 2:
						int[][] relativeCoords2 = {{-2,0},{-1,0},{1,0}};
						return relativeCoords2;
					default:
						int[][] relativeCoords3 = {{0,-1},{0,1},{0,2}};
						return relativeCoords3;
				}
			default:
				return new int[3][2];
		}
	}
	
	private boolean canRotate(int[] coords, int[][] relativeCoords) {
		if (currentBlock == "square") {
			return false;
		}
		for (int i = 0; i < 3; i++) {
			if (coords[0]+relativeCoords[i][0] < 0 || coords[0]+relativeCoords[i][0] > 19 || coords[1]+relativeCoords[i][1] < 0 || coords[1]+relativeCoords[i][1] > 9) {
				return false;
			}
			if (blocks[coords[0]+relativeCoords[i][0]].charAt(coords[1]+relativeCoords[i][1]) == '2') {
				return false;
			}
		}
		return true;
	}
	
	private void solidify() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (blocks[i].charAt(j) == '1') {
					blocksStringChange(i, j, '2');
				}
			}
		}
		state = "new";
	}
	
	private void clearRows() {
		for (int i = 19; i > -1; i--) {
			boolean full = true;
			for (int j = 0; j < 10; j++) {
				if (blocks[i].charAt(j) == '0') {
					full = false;
					break;
				}
			}
			if (full) {
				clearRow(i);
				i++;
			}
		}
	}
	
	private void clearRow(int row) {
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < 10; j++) {
				blocksStringChange(i, j, blocks[i-1].charAt(j));
				colorsStringChange(i, j, colors[i-1].charAt(j));
			}
		}
		blocks[0] = "0000000000";
	}
	
	private void blocksStringChange(int row, int column, char newChar) {
		blocks[row] = blocks[row].substring(0,column) + newChar + blocks[row].substring(column+1,10);
	}
	
	private void colorsStringChange(int row, int column, char newChar) {
		colors[row] = colors[row].substring(0,column) + newChar + colors[row].substring(column+1,10);
	}
	
	private class TetrisMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point mouseCoords = e.getPoint();
			if (state == "game over" && mouseCoords.getX() > 85 && mouseCoords.getX() < 215 && mouseCoords.getY() > 267 && mouseCoords.getY() < 307) {
				state = "new";
				repaint();
				init();
			}
		}
	}
}

public class Tetris extends JFrame {
	public Tetris() {
		initUI();
	}
	
	private void initUI() {
		add(new Surface());
		setTitle("Tetris");
		setSize(301, 631);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Tetris tetris = new Tetris();
				tetris.setVisible(true);
			}
		});
	}
}
