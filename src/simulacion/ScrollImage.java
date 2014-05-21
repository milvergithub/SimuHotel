package simulacion;

/**
 * @author MILVER
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollImage extends JScrollPane{
	
	public ScrollImage(Component view, int vsbPolicy, int hsbPolicy) 
	{
		super( view, vsbPolicy, hsbPolicy );
		// Set the component to transparent
		if( view instanceof JComponent )
			((JComponent)view).setOpaque(false);
	}
	
	public ScrollImage(Component view)
	{
		 this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public ScrollImage(int vsbPolicy, int hsbPolicy)
	{
		this(null, vsbPolicy, hsbPolicy);
	}
	
	public ScrollImage()
	{
		this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void paint(Graphics g) 
	{
		super.paint( g );
		if( image != null )
		{
			// Draw the background image
		   	Rectangle d = getViewport().getViewRect();
			for( int x = 0; x < d.width; x += image.getIconWidth() )
				for( int y = 0; y < d.height; y += image.getIconHeight() )
					g.drawImage( image.getImage(), x, y, null, null );
			// Do not use cached image for scrolling
			getViewport().setBackingStoreEnabled(false);
		}
	}
	
	public void setBackgroundImage( ImageIcon image )
	{
		this.image = image;
	}
	
	ImageIcon image = null;

}

