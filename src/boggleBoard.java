import processing.core.PApplet;
import processing.core.PConstants;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Matt on 3/28/2015.
 */
public class boggleBoard
{
    PApplet parent;
    letterTile[] tiles;
    String[][] dice;
    Scanner scan = null;
    String dir;

    boggleBoard(PApplet p)
    {
        parent = p;
        tiles = new letterTile[16];
        dice = new String[16][6];
        dir = System.getProperty("user.dir");
        try
        {
            scan = new Scanner(new File(dir + "\\dice.txt"));
        }
        catch (IOException e)
        {
            System.err.println("Error reading from: " + "dice.txt");
            System.exit(1);
        }

        //initialize dice
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 6; j++)
                dice[i][j] = scan.next();

        //initialize tiles
        for (int i = 0; i < 16; i++)
        {
            tiles[i] = new letterTile(parent, letterGenerator(i));
        }
    }

    public void display()
    {

        parent.rectMode(parent.CORNER);
        for (int i = 0; i < 16; i++)
        {
            tiles[i].display(115+200*(i%4), 160+200*(i/4));
        }
    }

    private String letterGenerator(int i)
    {
        return dice[i][randInt(0,5)];
    }

    public int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        System.out.println(randomNum);
        return randomNum;
    }
}
