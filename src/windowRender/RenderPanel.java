package windowRender;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class RenderPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener   {

    private final BufferedImage bi;//Image loaded
    
    private final Dimension ParentPrefSizDim;
    
    private int pushedButton=2;//Dragging only with middle button
    private boolean releasedButton=true;//Check for the middle button
    
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zooming;
    
    private boolean dragging;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    
    
    AffineTransform at=new AffineTransform();
    
    
    
    public RenderPanel (BufferedImage bi, Dimension dim){
        this.bi= bi;   
        this.ParentPrefSizDim=dim;
        initComponent();
        
    }
    
    private void initComponent() {
        setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight()));
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //Creating "canvas" component
        super.paintComponent(g);
	Graphics2D graphics2d = (Graphics2D)g;
        
        
        //When zoomed
        if (zooming) {
            at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
            
            
            if(zoomFactor<=1){
                xOffset=0;
                yOffset=0;
            }
            
            at.translate(xOffset, yOffset);            
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            zooming = false;
        }
        
        if (dragging) {            
            at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragging = false;
            }            
        }
        
        graphics2d.drawImage(bi, at , null);
        graphics2d.dispose();
        
        setPreferredSize(new Dimension((int)(bi.getWidth()*zoomFactor),(int)(bi.getHeight()*zoomFactor)));
        this.revalidate();
        
    }
      
    @Override
    public void mousePressed(MouseEvent e) {
        
        if(e.getButton()==2 && releasedButton==true){       
            released = false;
            startPoint = MouseInfo.getPointerInfo().getLocation();
            pushedButton=2;
            releasedButton=false;
        }else if (releasedButton==true){
            pushedButton=e.getButton();
        }      
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //If the image is at its scale
        if (xOffset!=0 && yOffset!=0){                
            
            if(pushedButton==2 && releasedButton==false){
                Point curPoint = e.getLocationOnScreen();
                xDiff = curPoint.x - startPoint.x;
                yDiff = curPoint.y - startPoint.y;

                dragging = true;
                repaint();
        }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==2){
            released = true;
            releasedButton=true;
            repaint();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent arg0) {
      
    }
  
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton()==2 && e.getClickCount()==2){
            System.out.println("paso por aqui");
            at = new AffineTransform();
            xOffset=0;
            yOffset=0;
            zoomFactor=1;
            prevZoomFactor = zoomFactor;
            at.translate(xOffset, yOffset);            
            at.scale(zoomFactor, zoomFactor);
            
            repaint();
        }
    }

    
    @Override
    public void mouseEntered(MouseEvent e) {
      
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
     
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        zooming = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
        /*
        if (zoomFactor<=1){
            zoomFactor=1;
            at.scale(zoomFactor, zoomFactor);
            repaint();
        }*/
    }
    
}

