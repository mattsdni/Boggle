import processing.core.PApplet;

public class Timer
{
    PApplet parent;
    int score;
    int seconds;
    int startTime;
    int tempTime;

    Timer(PApplet p)
    {
        parent = p;
        seconds = 180;
        startTime = parent.second();
    }


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
    public String toString()
    {
        return seconds/60+":"+seconds%60;
    }
}
