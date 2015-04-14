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
    /**
     * creates the button
     * @param p a reference to the PApplet
     * @param _x the x position of the button
     * @param _y the y position of the button
     * @param _w the width of the button
     * @param _h the height of the button
     * @param _text the button text value
     * @param color the color of the button
     * @param highlight the color of the button on mouse over
     */
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
    /**
     * display the button
     */
    public void display()
    {
        parent.rectMode(parent.CENTER);
        parent.noStroke();
        parent.fill(currentColor);
        parent.rect(x,y,width,height);
        parent.textMode(parent.CENTER);
        parent.fill(255);
        parent.text(text, x, y+parent.g.textSize/2.5f);
        parent.getBackground();
    }
    /**
     * check whether the mouse is over the button
     * @return true/false
     */
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
    /**
     * Collision detection for the mouse<->rectangle
     * @param x the x position of the button
     * @param y the y position of the button
     * @param width the width of the button
     * @param height the height of the button
     * @return true/false
     */
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
    /**
     * Determines whether the button is being pressed
     * @return
     */
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
    /**
     * Causes the button to highlight on mouse over
     */
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
}
