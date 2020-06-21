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
        long startTime = System.currentTimeMillis();
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
                //Control time
                long endTime = System.currentTimeMillis();
                long milliseconds = endTime - startTime;
                long minutes = (milliseconds / 1000) / 60;
                long seconds = (milliseconds / 1000) % 60;
                String text = "Render time: " + minutes + "m " + seconds + " s";

                //Print text on image
                Graphics graphics = bi.getGraphics();
                graphics.setColor(Color.DARK_GRAY);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString(text, 3, 10);

                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss") ;
                File outputfile = new File("renders/render" + dateFormat.format(date) + ".png");
                try {
                    ImageIO.write(bi, "png", outputfile);
                } catch (IOException e1) { e1.printStackTrace(); }
                System.exit(0);
            }
        });
    }
    
}