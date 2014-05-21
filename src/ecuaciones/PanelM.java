package ecuaciones;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 * @author MILVER
 */
public class PanelM extends JPanel{
    public PanelM(){
        
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp=new GradientPaint(0, 0,Color.DARK_GRAY, getWidth(), getHeight(), Color.BLACK);
        Shape s=new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), getHeight()/20, getHeight()/20);
        g2d.setPaint(gp);
        g2d.fill(s);
    }
}
