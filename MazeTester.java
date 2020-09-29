package maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.*;
import javax.swing.*;

public class MazeTester extends JPanel {

	static JButton b, b1, b2;
	static JLabel l;
	public static void main(String[] args) {
		
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	   JFrame f = new JFrame("Maze Solver"); 
                   l = new JLabel("Menu"); 
                   b = new JButton("Load"); 
                   b1 = new JButton("Start"); 
                   JTextField  textfield1 = new JTextField("Enter name of maze file: ",5);
                   JPanel p = new JPanel(); 
                   p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));  
                   p.add(b); 
                   p.add(textfield1);
                   p.add(b1); 
                   p.add(l); 
                   f.add(p); 
                   
                   p.setBackground(Color.white); 
                   
                   f.setSize(500, 500); 
                   f.setVisible(true);
                   f.show(); 
                   
               	b.addActionListener(new ActionListener(){
            		   public void actionPerformed(ActionEvent ae){
            		      String textFieldValue = textfield1.getText();
            		      System.out.println("textFieldValue");
            		   }
            		});
            }
        });

    	
	}
	


}
