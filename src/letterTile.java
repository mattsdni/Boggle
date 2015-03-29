import processing.core.PApplet;
import processing.core.PConstants;
/**
 * Created by Matt on 3/28/2015.
 */
public class letterTile
{
    PApplet parent;
    String letter;
    int sizeX, sizeY;
    boolean visited;

    letterTile(PApplet p, String s)
    {
        parent = p;
        letter = s;
        sizeX = 175;
        sizeY = 175;
        visited = false;
    }

    void display(int x, int y)
    {
        parent.noStroke();
        parent.textAlign(PConstants.CENTER);
        parent.fill(100,150,200);
        parent.rect(x,y, sizeX,sizeY);
        parent.fill(240);
        parent.textSize(48);
        parent.text(letter, x+sizeX/2, (int)(y+sizeY/1.6));
    }
}
