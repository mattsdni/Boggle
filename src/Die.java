import processing.core.PApplet;

import java.util.Random;

/**
 * Created by Matt on 4/8/2015.
 */
public class Die
{
    String[] sides;
    String topSide;

    public Die(String f1, String f2, String f3, String f4, String f5, String f6)
    {
        sides = new String[6];
        sides[0] = f1;
        sides[1] = f2;
        sides[2] = f3;
        sides[3] = f4;
        sides[4] = f5;
        sides[5] = f6;
    }

    public void roll()
    {
        topSide = sides[randInt(0,5)];
    }

    public int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        System.out.println(randomNum);
        return randomNum;
    }
}
