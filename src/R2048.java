import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author Jino Park
 * 2048 in reverse
 * CS150
 */

public class R2048 extends JApplet {
	private final int TILE_SIZE = 150; //Pixels
	private final int NUM_TILES = 4;
	private final int windowWidth = NUM_TILES*TILE_SIZE;
	private final int windowHeight = NUM_TILES*TILE_SIZE;
	private final JPanel mainJPanel = new JPanel(new GridLayout(NUM_TILES, NUM_TILES));
	private final TilePanel[][] gameBoard = new TilePanel[NUM_TILES][NUM_TILES];

	public void init(){
		setSize(windowWidth,windowHeight);
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
				moveLeft();
			}
		});

		mainJPanel.getActionMap().put("rightPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveRight();
			}
		});

		mainJPanel.getActionMap().put("downPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});

		mainJPanel.getActionMap().put("upPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});

		addRandomTile();
		add(mainJPanel);
	}

	public void moveLeft(){
		boolean hasMerged = false;

		for (int j=0; j<NUM_TILES; ++j){
			for (int i=1; i<NUM_TILES; ++i){
				TilePanel left = gameBoard[i-1][j];
				TilePanel right = gameBoard[i][j];

				if (left.getTileValue()==0 && right.getTileValue()==0){
					//This is a different case from the next conditional, so a necessary check.
				}
				else if (right.getTileValue()==left.getTileValue()){
					left.mergeSameValueTile();
					right.resetTileValue();
					hasMerged = true;
				}
				else if (left.getTileValue()==0){
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
	}

	public void moveRight(){
		boolean hasMerged = false;

		for (int j=0; j<NUM_TILES; ++j){
			for (int i=NUM_TILES-2; i>=0; --i){
				TilePanel left = gameBoard[i][j];
				TilePanel right = gameBoard[i+1][j];

				if (left.getTileValue()==0 && right.getTileValue()==0){
					//This is a different case from the next conditional, so a necessary check.
				}
				else if (left.getTileValue()==right.getTileValue()){
					right.mergeSameValueTile();
					left.resetTileValue();
					hasMerged = true;
				}
				else if (right.getTileValue()==0){
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
	}

	public void moveDown(){
		boolean hasMerged = false;

		for (int i=0; i<NUM_TILES; ++i){
			for (int j=NUM_TILES-2; j>=0; --j){
				TilePanel above = gameBoard[i][j];
				TilePanel below = gameBoard[i][j+1];

				if (above.getTileValue()==0 && below.getTileValue()==0){
					//This is a different case from the next conditional, so a necessary check.
				}
				else if (above.getTileValue() == below.getTileValue()){
					below.mergeSameValueTile();
					above.resetTileValue();
					hasMerged=true;
				}
				else if (below.getTileValue() == 0){
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
	}

	public void moveUp(){
		boolean hasMerged = false;

		for (int i=0; i<NUM_TILES; ++i){
			for (int j=1; j<NUM_TILES; ++j){
				TilePanel above = gameBoard[i][j-1];
				TilePanel below = gameBoard[i][j];

				if (below.getTileValue()==0 && above.getTileValue()==0){
					//This is a different case from the next conditional, so a necessary check.
				}
				else if (below.getTileValue()==above.getTileValue()){
					above.mergeSameValueTile();
					below.resetTileValue();
					hasMerged=true;
				}
				else if (above.getTileValue()==0){
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
	}

	public void addRandomTile(){
		ArrayList<Point> availableTileList = new ArrayList<Point>();
		for (int i=0; i<NUM_TILES; ++i){
			for (int j=0; j<NUM_TILES; ++j){
				if (gameBoard[i][j].getTileValue()==0){
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
				if (gameBoard[i][j].getTileValue()==0){
					tempAvailList.add(new Point (i, j));
				}
				else if (gameBoard[i][j].getTileValue()==1){
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
