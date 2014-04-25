import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class Animation extends JPanel{
	private int tileSize;
	private JLabel animationName = new JLabel();
	private Color backgroundColor = Color.white; //DEFAULT

	
	public Animation(int tileSize){
		this.tileSize=tileSize;
    	setLayout(new GridBagLayout()); //GridBagLayout in particular to center the JLabel.
    	setPreferredSize(new Dimension(tileSize,tileSize));
    	setupLabel();

	}
	
	private void setupLabel(){
		animationName.setForeground(Color.WHITE);
		animationName.setFont(new Font("Arial", Font.BOLD, 35));
        add(animationName);
	}
	
	public void startAnimation(AnimationType type){
		if (type == AnimationType.MERGING){
			backgroundColor = Color.cyan;
			animationName.setText("Merging");
		}
		else if (type == AnimationType.NEW_TILE){
			backgroundColor = Color.magenta;
			animationName.setText("New Tile");
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, tileSize, tileSize);
        g.setColor(Color.black);
        g.drawRect(0, 0, tileSize, tileSize);
	}


}
