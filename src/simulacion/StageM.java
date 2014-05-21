package simulacion;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 * @author milver
 */
public class StageM extends JPanel{
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp=new GradientPaint(0, 0, Color.BLACK, getWidth(), getHeight(), Color.GRAY);
        Shape s=new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0);
        g2d.setPaint(gp);
        g2d.fill(s);
    }
}
