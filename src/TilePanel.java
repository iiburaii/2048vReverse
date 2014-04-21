import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * @author Jino Park
 * 2048 in reverse
 * CS150
 */

public class TilePanel extends JPanel{
	static final int lowerStartValue = 1024;
	private int tile_size; //Pixels
	private int value;
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
		value = 0;
		this.tile_size = tile_size;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.setColor(getColor());
		g.fillRect(0, 0, tile_size, tile_size);//Tile Body

		g.setColor(new Color (0,0,0));
		g.drawRect(0, 0, tile_size, tile_size);//Tile Border

		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 35));

		String valueString = getValueString();
		FontMetrics tempFM = g.getFontMetrics();

		//drawString draws top right from coordinates, so getAscent() is necessary.
		int string_xPos = (tile_size/2)-(tempFM.stringWidth(valueString)/2);
		int string_yPos = (tile_size/2)+(tempFM.getAscent()/2);
		if (value>0){
			g.drawString(valueString, string_xPos, string_yPos);
		}
	}

	public int getValue(){
		return value;
	}
	
	public Color getColor(){
		return colorTable.get(value);
	}
	
	public String getValueString(){
		return String.valueOf(value);
	}

	public void initRandomTileValue(){
		Random r = new Random();
		int tileValue = r.nextInt(2)+1;
		changeValue(tileValue*lowerStartValue);
	}

	public void resetTileValue(){
		changeValue(0);
	}

	public void mergeNewTile(TilePanel nonZeroTile){
		changeValue(nonZeroTile.getValue());
	}

	public void mergeSameValueTile(){
		divide();
	}

	private void divide(){
		if (value>1) {
			changeValue(value/2);
		}
	}

	private void multiply(){}
	private void add(){}
	private void subtract(){}

	//This gives too much access, so must be private.
	private void changeValue(int newValue){
		value = newValue;
		repaint();
	}
}
