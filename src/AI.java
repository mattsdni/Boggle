import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Matt on 4/8/2015.
 * A basic computer player for boggle
 */
public class AI
{
    PApplet parent;
    int difficulty;
    LinkedList<String> knownWords;

    /**
     * An AI that plays boggle
     * @param p a reference to the PApplet
     * @param _difficulty The difficulty of the AI (needs improvement)
     */
    public AI(PApplet p, int _difficulty)
    {
        parent = p;
        difficulty = _difficulty;
        knownWords = new LinkedList<String>();
    }

    /**
     * Finds words on the board and adds them to a linked list of known words
     */
    public void findWords()
    {
        Scanner scan = null;
        String word;
        String dir = System.getProperty("user.dir");
        try
        {
            scan = new Scanner(new File(dir + "\\words.txt"));
        }
        catch (IOException e)
        {
            System.err.println("Error reading from: " + "words.txt");
            System.exit(1);
        }
        while(scan.hasNext())
        {
            word = scan.nextLine();
            if (word.length() > 2 && BoggleMain.board.hasWord(word) && randInt(0, difficulty) == 0)
            {
                knownWords.add(word);
            }
        }
    }

    /**
     * randomly adds some of the known words to the AI word list
     */
    public void addWords()
    {
        if (parent.frameCount%60==0 && randInt(0,10) == 0)
        {
            BoggleMain.scoreBoard.scoreWord(knownWords.get(randInt(0,knownWords.size()-1)), 1);
        }
    }

    /**
     * generates random numbers between min and max
     * @param min
     * @param max
     * @return the random number
     */
    private int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
