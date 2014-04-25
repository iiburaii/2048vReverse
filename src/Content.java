import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Content extends JPanel{
	private int tileValue;
    private JLabel tileLabel = new JLabel();
    private int tileSize;
    private boolean hasNotChanged = true;
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
		this.tileSize=tileSize;
    	setLayout(new GridBagLayout()); //GridBagLayout in particular to center the JLabel.
    	setPreferredSize(new Dimension(tileSize,tileSize));
    	setupLabel();
    }
	private void setupLabel(){
		tileLabel.setForeground(Color.WHITE);
        tileLabel.setFont(new Font("Arial", Font.BOLD, 35));
        add(tileLabel);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(getColor());
		g.fillRect(0, 0, tileSize, tileSize);
        g.setColor(Color.black);
        g.drawRect(0, 0, tileSize, tileSize);
	}
	
	public boolean hasNotChanged(){
		return hasNotChanged;
	}
	public void resetHasNotChangedFlag(){
		hasNotChanged=true;
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

	public void resetTileValue(){
		changeValue(TilePanel.defaultEmptyTileValue);
	}
	
	public void mergeNewTile(int nonZeroTileValue){
		changeValue(nonZeroTileValue);
	}
	
	public void mergeSameValueTile(){
		if (tileValue >1) {
			changeValue(tileValue/2);
			hasNotChanged=false;
		}
	}
	
	private void changeValue(int newValue){
		tileValue = newValue;
		if (tileValue>0){
			changeTileLabel(getValueString());
		}
		else{
			changeTileLabel("");
		}
		repaint();
	}
	
	private void changeTileLabel(String newText){
        tileLabel.setText(newText);
    }
	
}
