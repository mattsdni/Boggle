import processing.core.PApplet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Matt on 3/28/2015.
 * Represents the boggle board. Is able to check whether words are on the board
 */
public class boggleBoard
{
    PApplet parent;
    letterTile[][] tiles;
    String[][] diceText;
    Scanner scan = null;
    String dir;
    ArrayList<Integer> shuffleOrder;
    /**
     * creates the board
     * @param p a reference to the PApplet
     */
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
            scan = new Scanner(new File(dir + "\\Data\\dice.txt"));
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

    /**
     * shuffles the order that the 'dice' will fall into the board spaces
     */
    private void makeShuffled()
    {
        for(int i = 0; i < 16; i++)
        {
            shuffleOrder.add(i);
        }
        Collections.shuffle(shuffleOrder);
    }
    /**
     * 'Rolls' the 'dice'
     * @param i the die to roll
     * @return the upwards facing side/letter
     */
    private String letterGenerator(int i)
    {
        return diceText[shuffleOrder.get(i)][randInt(0,5)];
    }
    /**
     * displays the board
     */
    public void display()
    {
        parent.rectMode(parent.CORNER);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tiles[i][j].display(((parent.width-775)/2+200*i), 160+200*j);
    }

    /**
     * Generates random numbers between min and max
     * @param min
     * @param max
     * @return
     */
    public int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * checks to see whether the board has a word on it
     * @param word the word in question
     * @return
     */
    public boolean hasWord(String word)
    {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (hasWord(word, i, j))
                    return true;
        return false;
    }

    /**
     * helper function for the recursive word checking
     * @param word the owrd in question
     * @param row the row to look at
     * @param col the column to look at
     * @return true/false
     */
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