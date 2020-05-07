package pathtracer;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WindowFrame extends JPanel
{
    BufferedImage bi;    
    int w; //Width
    int h; //Height
    
    public void createAndShowGUI(int w, int h, BufferedImage bi){
        this.w=w;
        this.h=h;
        this.bi=bi;
        
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(onClose());
        frame.getContentPane().setLayout(new FlowLayout());
        this.add(new JLabel(new ImageIcon(bi)));
        frame.add(this);        
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Print image
                File outputfile = new File("renders/render.png");
                try {
                    ImageIO.write(bi, "png", outputfile);
                } catch (IOException e1) {  } 
                System.exit(0);
            }
        });
    }
    
    public void updateRender(BufferedImage bi){ 
        this.bi=bi;
        repaint();        
    }
}