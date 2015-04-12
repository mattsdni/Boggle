import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Matt on 3/28/2015.
 */
public class boggleBoard
{
    PApplet parent;
    letterTile[][] tiles;
    String[][] diceText;
    Scanner scan = null;
    String dir;
    ArrayList<Integer> shuffleOrder;

    boggleBoard(PApplet p)
    {
        parent = p;
        shuffleOrder = new ArrayList<Integer>();
        makeShuffled();
        tiles = new letterTile[4][4];
        diceText = new String[16][6];
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
                diceText[i][j] = scan.next();

        //initialize tiles
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tiles[i][j] = new letterTile(parent, letterGenerator(i*4+j));
    }

    private void makeShuffled()
    {
        for(int i = 0; i < 16; i++)
        {
            shuffleOrder.add(i);
        }
        Collections.shuffle(shuffleOrder);
    }
    private String letterGenerator(int i)
    {
        return diceText[shuffleOrder.get(i)][randInt(0,5)];
    }

    public void display()
    {
        parent.rectMode(parent.CORNER);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tiles[i][j].display(((parent.width-775)/2+200*i), 160+200*j);
    }

    public int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
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