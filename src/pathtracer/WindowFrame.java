package pathtracer;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.*;
import javax.swing.*;

public class WindowFrame extends JPanel
{
    public BufferedImage bi;
    int w;
    int h;
    
    public void createAndShowGUI(int w, int h, BufferedImage bi)
    {
        this.w=w;
        this.h=h;
        this.bi=bi;
        JFrame frame = new JFrame("JPTrender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( this );
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bi, 0, 0, this);
        System.out.println("no");
    } 
    
    public void updateRender(BufferedImage bi2){          
        this.bi=bi2;
        repaint();        
    }
    
    
    
}