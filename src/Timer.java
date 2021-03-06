import processing.core.PApplet;

public class Timer
{
    PApplet parent;
    int score;
    int seconds;
    int startTime;
    int tempTime;

    /**
     * creates a timer
     * @param p a reference to the PApplet
     */
    Timer(PApplet p)
    {
        parent = p;
        seconds = 180;
        startTime = parent.second();
    }

    /**
     * displays the timer
     */
    public void display()
    {
        if (startTime != parent.second())
        {
            seconds--;
            startTime = parent.second();
        }
        parent.fill(20);
        parent.textAlign(parent.LEFT);
        parent.text("Time Remaining: "+ this, 20, 40);

    }

    /**
     * creates a string representation of the timer
     * @return
     */
    public String toString()
    {
        String t = ((seconds%60) < 10) ? "0"+seconds%60 : seconds%60+"";
        return seconds/60+":"+ t;
    }
}
