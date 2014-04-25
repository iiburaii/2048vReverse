import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;




public class TilePanel extends JPanel{
	static final int defaultEmptyTileValue = 0;
	static final int lowerStartValue = 1024;
	private final static int DELAY_COUNTER_MAX = 1;     //5 seconds
	private final static int DELAY_DURATION_MAX = 250;  //0.5 second
	private int delayDuration = DELAY_DURATION_MAX;
	private int newTileDelayCounter, mergingTileDelayCounter = DELAY_COUNTER_MAX;
	private Timer timer;
	private ActionListener actionMergeTile, actionNewTile;
	
	private final Content content;
	private final Animation animation;

	public TilePanel(int tileSize){
		content = new Content(defaultEmptyTileValue, tileSize);
		animation = new Animation(tileSize);
		setSize(tileSize, tileSize);
		add(content);



		actionMergeTile = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mergingTileDelayCounter==0){
					timer.stop();
					mergingTileDelayCounter=DELAY_COUNTER_MAX;
					
					remove(animation);
					add(content);
					
					
					content.mergeSameValueTile();

				}
				else{
					--mergingTileDelayCounter;
				}
			}
		};

		actionNewTile = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (newTileDelayCounter==0){
					timer.stop();
					newTileDelayCounter=DELAY_COUNTER_MAX;
					
					remove(animation);
					add(content);
		
					content.initRandomTileValue();

				}
				else{
					--newTileDelayCounter;
				}
			}
		};
	}

	public boolean hasNotChanged(){
		return content.hasNotChanged();
	}

	public void resetHasNotChangedFlag(){
		content.resetHasNotChangedFlag();
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
		//content.initRandomTileValue();
		
		remove(content);
		add(animation);
		
		animation.startAnimation(AnimationType.NEW_TILE);
		timer = new Timer(delayDuration, actionNewTile);
        timer.setInitialDelay(0);
        timer.start();
		
		
	}

	public void resetTileValue(){
		content.resetTileValue();
	}

	public void mergeNewTile(TilePanel nonZeroTile){
		content.mergeNewTile(nonZeroTile.getValue());
	}

	public void mergeSameValueTile(){
		content.mergeSameValueTile();
		
		/*remove(content);
		add(animation);
		
		animation.startAnimation(AnimationType.MERGING);
		timer = new Timer(delayDuration, actionMergeTile);
        timer.setInitialDelay(0);
        timer.start();*/
        
        
	}
}
