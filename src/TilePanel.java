import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;




public class TilePanel extends JPanel{
    static final int defaultEmptyTileValue = 0;
	static final int lowerStartValue = 1024;
	private Content content;

	
	public TilePanel(int tileSize){
		content = new Content(defaultEmptyTileValue, tileSize);
        setSize(tileSize, tileSize);
        add(content);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public int getValue(){
		return content.getValue();
	}
	
	public Color getColor(){
		return content.getColor();
	}
	
	public String getValueString(){
		return content.getValueString();
	}

	public void initRandomTileValue(){
		content.initRandomTileValue();
	}

    public void makeNewTile(int val){
        content.makeNewTile(val);
    }

	public void resetTileValue(){
		content.resetTileValue();
	}

	public void mergeNewTile(TilePanel nonZeroTile){
		content.mergeNewTile(nonZeroTile.getValue());
	}

	public void mergeSameValueTile(){
		content.mergeSameValueTile();
	}
}
