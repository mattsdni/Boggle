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
    letterTile[][] tiles;
    String[][] dice;
    Scanner scan = null;
    String dir;

    boggleBoard(PApplet p)
    {
        parent = p;
        tiles = new letterTile[4][4];
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
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tiles[i][j] = new letterTile(parent, letterGenerator(i*4+j));
    }

    public void display()
    {
        parent.rectMode(parent.CORNER);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tiles[i][j].display((int)((parent.width-775)/2+200*i), 160+200*j);
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

    public boolean hasWord(String word)
    {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (hasWord(word, i, j))
                    return true;
        return false;
    }

    private boolean hasWord(String word, int row, int col)
    {
        if (word.equals(""))
            return true;
        if (row > 3 || col > 3 || row < 0 || col < 0)
            return false;
        if (tiles[row][col].letter.toLowerCase().compareTo(word.charAt(0)+"") != 0 || tiles[row][col].visited)
        {
            return false;
        }
        tiles[row][col].visited = true;
        word = word.substring(1, word.length());
        boolean found = this.hasWord(word, row+1, col) ||
                this.hasWord(word, row, col+1) ||
                this.hasWord(word, row+1, col+1) ||
                this.hasWord(word, row-1, col) ||
                this.hasWord(word, row, col-1) ||
                this.hasWord(word, row-1, col-1) ||
                this.hasWord(word, row+1, col-1) ||
                this.hasWord(word, row-1, col+1);
        tiles[row][col].visited = false;
        return found;
    }
}