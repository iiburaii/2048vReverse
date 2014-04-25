import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;


public class Content extends JPanel{

	private int tileValue;
    private JLabel tileLabel = new JLabel();
    
    
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
	
	
	
	public Content(int val, int tileSize){
    	setLayout(new GridBagLayout()); //GridBagLayout in particular to center the JLabel.
    	setSize(tileSize,tileSize);
    	setupLabel();
    	
    }
	public int getValue(){
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
		changeValue(tileValueModifier*TilePanel.lowerStartValue);
	}
	public void makeNewTile(int val){
		changeValue(val*TilePanel.lowerStartValue);
	}
	public void resetTileValue(){
		changeValue(TilePanel.defaultEmptyTileValue);
	}
	public void mergeNewTile(int nonZeroTileValue){
		changeValue(nonZeroTileValue);
	}
	public void mergeSameValueTile(){
		if (tileValue >1) {
			changeValue(tileValue/2);
		}
	}
	private void changeValue(int newValue){
		tileValue = newValue;
	}
	private void changeTileLabel(String newText){
        tileLabel.setText(newText);
    }
	private void setupLabel(){
		tileLabel.setForeground(Color.WHITE);
        tileLabel.setFont(new Font("Arial", Font.BOLD, 35));
        add(tileLabel);
	}
	
}
