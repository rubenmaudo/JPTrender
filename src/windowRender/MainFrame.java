package windowRender;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainFrame extends JFrame
{
       
    private final int renderWidth; 
    private final int renderHeight;
    private Dimension preferredSizDim;
    public RenderPanel renderPanel;
    private JScrollPane scrollPanel;
    
    
    public MainFrame(BufferedImage bi) {        
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
                //Print image
                
                /*
                File outputfile = new File("renders/render.png");
                try {
                    ImageIO.write(bi, "png", outputfile);
                } catch (IOException e1) {  } */
                System.exit(0);
            }
        });
    }
    
}