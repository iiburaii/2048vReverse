import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class R2048 extends JApplet {
	private final int TILE_SIZE = 150; //Pixels
	private final int NUM_TILES = 4;
	private final int windowWidth = NUM_TILES*TILE_SIZE;
	private final int windowHeight = NUM_TILES*TILE_SIZE;
	private final JPanel mainJPanel = new JPanel(new GridLayout(NUM_TILES, NUM_TILES));
	private final TilePanel[][] gameBoard = new TilePanel[NUM_TILES][NUM_TILES];

	public void init(){
		setLayout(new FlowLayout());

		setSize(windowWidth+50,windowHeight+200);
		for (int i=0; i<NUM_TILES; ++i){
			for (int j=0; j<NUM_TILES; ++j){
				gameBoard[j][i] = new TilePanel(TILE_SIZE);
				//Initial setup must be added this way due to the way GridLayout inserts components.
				mainJPanel.add(gameBoard[j][i]);
			}
		}

		mainJPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftPressed");
		mainJPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightPressed");
		mainJPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downPressed");
		mainJPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");

		mainJPanel.getActionMap().put("leftPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEnd();
				moveLeft();
			}
		});

		mainJPanel.getActionMap().put("rightPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEnd();
				moveRight();
			}
		});

		mainJPanel.getActionMap().put("downPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEnd();
				moveDown();
			}
		});

		mainJPanel.getActionMap().put("upPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEnd();
				moveUp();
			}
		});

		addRandomTile();
		add(mainJPanel);

		//Instructions
		JTextPane instructions = new JTextPane();
		instructions.setEditable(false);
		instructions.setContentType("text/html");
		String s = "<HTML><FONT SIZE=+2>JApplet Game: 2048 in Reverse</FONT>"+
				"<br>HOW TO PLAY:<br>" +
				"<br>Use arrow keys to play. Pressing a key (<FONT COLOR='RED'>up, down, left, right</FONT>) causes all tiles to shift in that direction." +
				"<br>Two adjacent duplicate tiles, when shifted correctly, will merge into a single tile with half value." + 
				"<br>A random tile is added after every shift, denoted in magenta. Find the <FONT SIZE=+2>'<b>1</b>'</FONT> tile to win.";
		instructions.setText(s);
		JScrollPane tmpSPane = new JScrollPane(instructions);
		
		JPanel tmp = new JPanel(new GridLayout());
		tmp.add(tmpSPane);
		add(tmp);
	}

	public void moveLeft(){
		boolean hasMerged = false;

		for (int j=0; j<NUM_TILES; ++j){
			for (int i=1; i<NUM_TILES; ++i){
				TilePanel left = gameBoard[i-1][j];
				TilePanel right = gameBoard[i][j];

				if (left.getValue()==0 && right.getValue()==0){
					//This is actually necessary...
				}
				else if (right.getValue()==left.getValue()){
					if (right.hasNotChanged() && left.hasNotChanged()){
						left.mergeSameValueTile();
						right.resetTileValue();
						hasMerged = true;
					}
				}
				else if (left.getValue()==0){

					left.mergeNewTile(right);
					right.resetTileValue();
					hasMerged = true;
					i = 0;
				}
			}
		}

		if (hasMerged){
			addRandomTile();
		}

		resetHasNotChangedFlag();
	}

	public void moveRight(){
		boolean hasMerged = false;

		for (int j=0; j<NUM_TILES; ++j){
			for (int i=NUM_TILES-2; i>=0; --i){
				TilePanel left = gameBoard[i][j];
				TilePanel right = gameBoard[i+1][j];

				if (left.getValue()==0 && right.getValue()==0){
					//This is actually necessary...
				}
				else if (left.getValue()==right.getValue()){
					if (right.hasNotChanged() && left.hasNotChanged()){
						right.mergeSameValueTile();
						left.resetTileValue();
						hasMerged = true;
					}
				}
				else if (right.getValue()==0){
					right.mergeNewTile(left);
					left.resetTileValue();
					hasMerged = true;
					i = NUM_TILES-1;
				}

			}
		}

		if (hasMerged){
			addRandomTile();
		}

		resetHasNotChangedFlag();
	}

	public void moveDown(){
		boolean hasMerged = false;

		for (int i=0; i<NUM_TILES; ++i){
			for (int j=NUM_TILES-2; j>=0; --j){
				TilePanel above = gameBoard[i][j];
				TilePanel below = gameBoard[i][j+1];

				if (above.getValue()==0 && below.getValue()==0){
					//This is actually necessary...
				}
				else if (above.getValue() == below.getValue()){
					if (above.hasNotChanged() && below.hasNotChanged()){
						below.mergeSameValueTile();
						above.resetTileValue();
						hasMerged=true;
					}
				}
				else if (below.getValue() == 0){
					below.mergeNewTile(above);
					above.resetTileValue();
					hasMerged=true;
					j = NUM_TILES-1;
				}

			}
		}

		if (hasMerged){
			addRandomTile();
		}

		resetHasNotChangedFlag();
	}

	public void moveUp(){
		boolean hasMerged = false;

		for (int i=0; i<NUM_TILES; ++i){
			for (int j=1; j<NUM_TILES; ++j){
				TilePanel above = gameBoard[i][j-1];
				TilePanel below = gameBoard[i][j];

				if (below.getValue()==0 && above.getValue()==0){
					//This is a different case from the next conditional, so a necessary check.
				}
				else if (below.getValue()==above.getValue()){
					if (above.hasNotChanged() && below.hasNotChanged()){
						above.mergeSameValueTile();
						below.resetTileValue();
						hasMerged=true;
					}
				}
				else if (above.getValue()==0){
					above.mergeNewTile(below);
					below.resetTileValue();
					hasMerged=true;
					j=0;
				}

			}
		}

		if (hasMerged){
			addRandomTile();
		}

		resetHasNotChangedFlag();
	}

	public void resetHasNotChangedFlag(){
		for (int i=0; i<NUM_TILES; ++i){
			for (int j=0; j<NUM_TILES; ++j){
				gameBoard[i][j].resetHasNotChangedFlag();
			}
		}
	}

	public void addRandomTile(){
		ArrayList<Point> availableTileList = new ArrayList<Point>();
		for (int i=0; i<NUM_TILES; ++i){
			for (int j=0; j<NUM_TILES; ++j){
				if (gameBoard[i][j].getValue()==0){
					availableTileList.add(new Point (i, j));
				}
			}
		}

		Random r = new Random();
		int index1,index2,numRandTiles,indexOfAvailable;

		//Special case of the start of the game.
		if (availableTileList.size() == Math.pow(NUM_TILES, 2)){
			numRandTiles = 2;
		}
		else{
			numRandTiles = 1;
		}

		for (int i=0; i<numRandTiles; ++i){
			indexOfAvailable = r.nextInt(availableTileList.size());
			Point tempPoint = availableTileList.get(indexOfAvailable);
			index1 = tempPoint.x;
			index2 = tempPoint.y;
			gameBoard[index1][index2].initRandomTileValue();
			availableTileList.remove(indexOfAvailable);
		}
		checkEnd();	
	}

	private void checkEnd(){
		ArrayList<Point> tempAvailList = new ArrayList<Point>();
		for (int i=0; i<NUM_TILES; ++i){
			for (int j=0; j<NUM_TILES; ++j){
				if (gameBoard[i][j].getValue()==0){
					tempAvailList.add(new Point (i, j));
				}
				else if (gameBoard[i][j].getValue()==1){
					JOptionPane.showMessageDialog(mainJPanel, "You win!");
					System.exit(1);
				}
			}
		}

		if (tempAvailList.isEmpty()){
			JOptionPane.showMessageDialog(mainJPanel, "You lose.");
			System.exit(1);
		}
	}
}
