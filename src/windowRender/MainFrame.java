package windowRender;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class MainFrame extends JFrame
{
       
    private final int renderWidth; 
    private final int renderHeight;
    private Dimension preferredSizDim;
    public RenderPanel renderPanel;
    private JScrollPane scrollPanel;
    private BufferedImage bi;
    
    
    public MainFrame(BufferedImage bi) {

        this.bi=bi;//To safe file

        this.renderPanel=new RenderPanel(bi, preferredSizDim);
        this.renderWidth=bi.getWidth();
        this.renderHeight=bi.getHeight();
        preferredSizDim=new Dimension(1280,720);
        initComponents();
    }
    
    public final void initComponents(){
        setTitle("JPTrender");
        setResizable(true);
        
        if(renderWidth>=1280 || renderHeight>720){
            setPreferredSize(preferredSizDim);
        }
        
        scrollPanel=new JScrollPane(renderPanel);
        add(scrollPanel);
        
        setResizable(false);
        pack();
        this.setVisible(true);  
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {


                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss") ;
                File outputfile = new File("Renders/render" + dateFormat.format(date) + ".png");
                try {
                    ImageIO.write(bi, "png", outputfile);
                } catch (IOException e1) { e1.printStackTrace(); }
                System.exit(0);
            }
        });
    }
    
}