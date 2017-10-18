import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	System.out.println("Left");
        }
        if (key == KeyEvent.VK_RIGHT) {
        	System.out.println("Right");
        }
        if (key == KeyEvent.VK_UP) {
        	System.out.println("Up");
        }
        if (key == KeyEvent.VK_DOWN) {
        	System.out.println("Down");
        }
    }
}