import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Matt on 4/8/2015.
 */
public class AI
{
    PApplet parent;
    int difficulty;

    public AI(PApplet p, int _difficulty)
    {
        parent = p;
        difficulty = _difficulty;
    }

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
                BoggleMain.scoreBoard.scoreWord(word, 1);
            }
        }
    }

    private int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        System.out.println(randomNum);
        return randomNum;
    }
}
