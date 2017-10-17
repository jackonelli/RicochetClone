import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import javax.swing.JFrame;

/**
 * RotateImage
 * 1. scales an image's dimensions by a factor of two 
 * 2. rotates it 90 degrees * rotations around the image center 
 * 3. returns the image
 * 
 * It's unclear if this is a more efficient approach. It could be made once and then stored.
 * This could also work to rotate the robots according to the heading they have.
 * 
 * Code modified version of: http://www.java2s.com/Code/Java/2D-Graphics-GUI/RotateImage45Degrees.htm
 * 
 *  @author JonathanFager
 */
public class RotateImage extends JFrame {
  private Image inputImage;
  int rotations;

  private BufferedImage sourceBI;

  private BufferedImage destinationBI = null;

  private Insets frameInsets;

  private boolean sizeSet = false;
  

  public Image RotateImage(String imageFile, int rotations) {
	this.rotations = rotations;
    addNotify();
    frameInsets = getInsets();
    inputImage = Toolkit.getDefaultToolkit().getImage(imageFile);

    MediaTracker mt = new MediaTracker(this);
    mt.addImage(inputImage, 0);
    try {
      mt.waitForID(0);
    } catch (InterruptedException ie) {
    }

    sourceBI = new BufferedImage(inputImage.getWidth(null), inputImage
        .getHeight(null), BufferedImage.TYPE_INT_ARGB);

    Graphics2D g = (Graphics2D) sourceBI.getGraphics();
    g.drawImage(inputImage, 0, 0, null);

    AffineTransform at = new AffineTransform();

    // scale image
    at.scale(2.0, 2.0);

    // rotate 90 degress * rotations around image center
    at.rotate((90.0 * rotations) * Math.PI / 180.0, sourceBI.getWidth() / 2.0, sourceBI
        .getHeight() / 2.0);

    /*
     * translate to make sure the rotation doesn't cut off any image data
     */
    AffineTransform translationTransform;
    translationTransform = findTranslation(at, sourceBI);
    at.preConcatenate(translationTransform);

    // instantiate and apply affine transformation filter
    BufferedImageOp bio;
    bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

    destinationBI = bio.filter(sourceBI, null);

    int frameInsetsHorizontal = frameInsets.right + frameInsets.left;
    int frameInsetsVertical = frameInsets.top + frameInsets.bottom;
    setSize(destinationBI.getWidth() + frameInsetsHorizontal, destinationBI
        .getHeight()
        + frameInsetsVertical);
	return destinationBI;
  }

  /*
   * find proper translations to keep rotated image correctly displayed
   */
  private AffineTransform findTranslation(AffineTransform at, BufferedImage bi) {
    Point2D p2din, p2dout;

    p2din = new Point2D.Double(0.0, 0.0);
    p2dout = at.transform(p2din, null);
    double ytrans = p2dout.getY();

    p2din = new Point2D.Double(0, bi.getHeight());
    p2dout = at.transform(p2din, null);
    double xtrans = p2dout.getX();

    AffineTransform tat = new AffineTransform();
    tat.translate(-xtrans, -ytrans);
    return tat;
  }
}
