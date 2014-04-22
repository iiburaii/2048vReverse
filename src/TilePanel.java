import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


/**
 * @author Jino Park
 * 2048 in reverse
 * CS150
 */

public class TilePanel extends JPanel{
    static final int defaultEmptyTileValue = 0;
	static final int lowerStartValue = 1024;
    private final static int DELAY_COUNTER_MAX = 1;     //5 seconds
    private final static int DELAY_DURATION_MAX = 1000; //1 second
    private boolean isMerging = false;
    private boolean isMakingNewTile = false;
	private int tileSize; //Pixels
	private int tileValue;
    private Timer timer;
    private int delayDuration = DELAY_DURATION_MAX;
    private int delayCounter = DELAY_COUNTER_MAX;
    private JLabel tileLabel = new JLabel();
    private ActionListener actionMergeTile, actionNewTile;
    private int TEMP_NUM;

	private static final Hashtable<Integer, Color> colorTable;
	static{
		colorTable = new Hashtable<Integer, Color>();
		colorTable.put(0,new Color(135,135,135));
		colorTable.put(2048,new Color(195,195,195));
		colorTable.put(1024,new Color(195,195,195));
		colorTable.put(512,new Color(0,195,50));
		colorTable.put(256,new Color(0,195,50));
		colorTable.put(128,new Color(36,0,215));
		colorTable.put(64,new Color(36,0,215));
		colorTable.put(32,new Color(195,0,55));
		colorTable.put(16,new Color(195,0,55));
		colorTable.put(8,new Color(0,0,0));
		colorTable.put(4,new Color(0,0,0));
		colorTable.put(2,new Color(36,0,155));
		colorTable.put(1,new Color(36,0,155));
	}

	public TilePanel(int tile_size){
        setLayout(new GridBagLayout()); //GridBagLayout in particular to center the JLabel.
		tileValue = defaultEmptyTileValue;
		tileSize = tile_size;
        setSize(tileSize, tileSize);
        tileLabel.setForeground(Color.WHITE);
        tileLabel.setFont(new Font("Arial", Font.BOLD, 35));
        add(tileLabel);

        actionMergeTile = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delayCounter ==0){
                    isMerging = false;
                    timer.stop();
                    delayCounter=DELAY_COUNTER_MAX;
                    divide();

                }
                else{
                    --delayCounter;
                    repaint();
                    //refreshTileContents();
                }
            }
        };

        actionNewTile = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delayCounter ==0){
                    isMakingNewTile = false;
                    timer.stop();
                    delayCounter=DELAY_COUNTER_MAX;
                    //initRandomTileValue();
                    makeNewTile(TEMP_NUM);

                }
                else{
                    --delayCounter;
                    repaint();
                    //refreshTileContents();
                }
            }
        };
	}
/*
    public void refreshTileContents(){
        setBackground(getColor());
        setBorder(BorderFactory.createLineBorder(Color.black));

        if (isMerging){
            setBackground(Color.CYAN);
            changeTileLabel("Merging");
        }
        else if (tileValue>0){
            changeTileLabel(getValueString());
        }
        else{
            changeTileLabel("");
        }
    }
*/
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

        setBackground(getColor());
        setBorder(BorderFactory.createLineBorder(Color.black));

        if (isMerging){
            setBackground(Color.CYAN);
            changeTileLabel("Merging");
        }
        else if (isMakingNewTile){
            setBackground(Color.magenta);
            changeTileLabel("New Tile");
        }
        else if (tileValue>0){
            changeTileLabel(getValueString());
        }
        else{
            changeTileLabel("");
        }
	}

	public int getTileValue(){
		return tileValue;
	}
	
	public Color getColor(){
		return colorTable.get(tileValue);
	}
	
	public String getValueString(){
		return String.valueOf(tileValue);
	}

	public void initRandomTileValue(){
		Random r = new Random();
		int tileValueModifier = r.nextInt(2)+1;
        TEMP_NUM = tileValueModifier;
		//changeValue(tileValueModifier*lowerStartValue);
        //makeNewTile(tileValueModifier);
        timer = new Timer(delayDuration, actionNewTile);
        isMakingNewTile = true;
        timer.setInitialDelay(0);
        timer.start();
	}

    public void makeNewTile(int val){
        changeValue(val*lowerStartValue);
    }

	public void resetTileValue(){
		changeValue(defaultEmptyTileValue);
	}

	public void mergeNewTile(TilePanel nonZeroTile){
		changeValue(nonZeroTile.getTileValue());
	}

	public void mergeSameValueTile(){
        timer = new Timer(delayDuration, actionMergeTile);
        isMerging = true;
        timer.setInitialDelay(0);
        timer.start();
		//divide();
	}

	private void divide(){
		if (tileValue >1) {
			changeValue(tileValue /2);
		}
	}

	private void multiply(){}
	private void add(){}
	private void subtract(){}

	//This gives too much access, so must be private.
	private void changeValue(int newValue){
		tileValue = newValue;
		repaint();
        //refreshTileContents();
	}
    private void changeTileLabel(String newText){
        tileLabel.setText(newText);
    }
}
