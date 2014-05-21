package simulacion;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
/**
 * @author simulacion
 */
public class Actor extends JComponent{
    
    private int rejilla;
    private StageM stage;
    private ImageIcon imagen;
    public Actor(StageM s){
        setSize(25,50);
        stage=s;
        rejilla=2;
    }
    protected void mover(){
        this.setLocation(this.getX()+getRejilla(), this.getY());
    }
    public void setRejilla(int n){
        this.rejilla=n;
    }
    private int getRejilla(){
        return rejilla;
    }
    protected void remover(){
        stage.remove(this);
        stage.repaint();
    }
    protected void girar(int grads){
        
    }
    public void paint(Graphics g){
        Dimension d=getSize();
        g.drawImage(imagen.getImage(), 0, 0,getWidth(),getHeight(),null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
}
