import processing.core.PApplet;

/**
 * Created by Matt on 3/29/2015.
 * Adapted from code by Casey Reas and Ben Fry at http://processingjs.org/learning/topic/buttons/
 */
public class button
{
    PApplet parent;
    int x, y;
    int height, width;
    String text;
    int bColor;
    int baseColor, highlightColor;
    int currentColor;
    boolean over = false;
    boolean pressed = false;

    button(PApplet p, int _x, int _y, int _w, int _h, String _text, int color, int highlight)
    {
        parent = p;
        x = _x;
        y = _y;
        width = _w;
        height = _h;
        text = _text;
        baseColor = color;
        highlightColor = highlight;
        currentColor = baseColor;
    }

    public void display()
    {
        parent.rectMode(parent.CENTER);
        parent.noStroke();
        parent.fill(currentColor);
        parent.rect(x,y,width,height);
        parent.textMode(parent.CENTER);
        parent.fill(255);
        parent.text(text, x, y*1.025f);
        parent.getBackground();
    }

    boolean over()
    {
        if( overRect(x, y, width, height) )
        {
            over = true;
            return true;
        }

        else
        {
            over = false;
            return false;
        }
    }

    boolean overRect(int x, int y, int width, int height)
    {
        if (parent.mouseX >= x-width/2 && parent.mouseX <= x+width/2 && parent.mouseY >= y-height/2 && parent.mouseY <= y+height/2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean pressed()
    {
        if(over)
        {
            BoggleMain.locked = true;
            return true;
        }

        else
        {
            BoggleMain.locked = false;
            return false;
        }
    }

    void update()
    {
        if(over())
        {
            currentColor = highlightColor;
        }
        else
        {
            currentColor = baseColor;
        }
    }

    public void mouseClicked()
    {
        System.out.println("test");
    }

}
