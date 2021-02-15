import java.awt.event.*;
import java.awt.*;

//Constructor function for the KeyHandle class
public class KeyHandle implements MouseMotionListener, MouseListener {

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		Screen.store.click(e.getButton());
		System.out.println("Button pressed");
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		Screen.mse = new Point ((e.getX()) + ((Frame.size.width - Screen.myWidth)/2),  (e.getY()) + ((Frame.size.height - (Screen.myHeight))));
	}

	public void mouseMoved(MouseEvent e) {
		Screen.mse = new Point ((e.getX()) - ((Frame.size.width - Screen.myWidth)/2),  (e.getY()) - ((Frame.size.height - (Screen.myHeight))));
	}

}
