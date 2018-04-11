package com.game.minesweeper.Views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

//creates a popup jframe to display the given msg to help the user
public class ViewPopupHelp extends JFrame{

	private final int fontSize = 14;

	
	public ViewPopupHelp(String msg, int frameWidth, int frameHeight)
	{
		super("Help Window");

		
		//display the message
		JTextArea help = new JTextArea(msg);
		help.setBackground(getBackground());
		help.setFont(new Font("Arial",Font.BOLD,fontSize));
		help.setLineWrap(true);
		help.setWrapStyleWord(true);
		help.setEditable(false);
		
	    setSize(frameWidth,frameHeight);
	    setLayout(new BorderLayout());
	    setLocationRelativeTo(null);
	    
	    add(help,BorderLayout.PAGE_START);

	    setVisible(true);
	}
}
