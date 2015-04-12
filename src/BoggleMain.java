import processing.core.PApplet;
import processing.core.PFont;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Matt on 3/28/2015.
 */
public class BoggleMain extends PApplet
{

    static boggleBoard board;
    dictionary dict;
    PFont font;
    String dir;
    String input = "";
    String typing = "";
    boolean sPlaying = false;
    boolean mPlaying = false;
    boolean inPostGame = false;
    boolean inMainMenu = true;
    button playButton;
    button multiPlayButton;
    static boolean locked = false;
    int currentColor;
    static ScoreBoard scoreBoard;
    Timer stopWatch;
    String playerWords, AIWords;
    AI ai;

    public static void main(String args[])
    {
        PApplet.main(new String[]{"BoggleMain"});
    }

    public void setup()
    {
        size(1200, 1000, P3D);
        dir = System.getProperty("user.dir");
        font = createFont(dir + "\\Data\\arialbd.ttf", 48);
        textFont(font, 48);
        dict = new dictionary();
        dict.load();
        board = new boggleBoard(this);
        playButton = new button(this, width / 2-210, 600, 350, 75, "Single Player", color(20), color(60));
        multiPlayButton = new button(this, width / 2+210, 600, 350, 75, "Multiplayer", color(20), color(60));
        scoreBoard = new ScoreBoard(this);
        stopWatch = new Timer(this);
        ai = new AI(this, 10);
        ai.findWords();
    }

    public void draw()
    {
        if (inMainMenu)
            mainMenu();
        if (sPlaying)
            sPlay();
        if (mPlaying)
            mPlay();
        if(inPostGame)
            postGame();
    }

    public void mainMenu()
    {
        background(100, 150, 200);
        fill(255);
        textAlign(CENTER);
        textSize(48);
        text("Boggle", width / 2, 200);
        playButton.display();
        multiPlayButton.display();
        update(mouseX, mouseY);
    }

    public void sPlay()
    {
        background(240);
        board.display();
        noFill();
        strokeWeight(3);
        stroke(0);
        rectMode(CENTER);
        rect(width / 2, 95, 500, 50);
        fill(20);
        textAlign(CENTER);
        textSize(24);
        text("Enter a word:", width / 2, 50);
        fill(0);
        text(typing, width / 2, 105);
        scoreBoard.display();
        stopWatch.display();
        if (stopWatch.seconds < 0)
        {
            postGamePrep();
        }
    }

    public void mPlay()
    {
        background(240);
        board.display();
        noFill();
        strokeWeight(3);
        stroke(0);
        rectMode(CENTER);
        rect(width / 2, 95, 500, 50);
        fill(20);
        textAlign(CENTER);
        textSize(24);
        text("Enter a word:", width / 2, 50);
        fill(0);
        text(typing, width / 2, 105);
        scoreBoard.display();
        stopWatch.display();
    }

    public void postGamePrep()
    {
        delay(2000);
        for (int i = 0; i < scoreBoard.player1Words.size(); i++)
        {
            if (AIWords.contains(scoreBoard.player1Words.get(i)))
                scoreBoard.player1Words.remove(i);
        }
        for (int i = 0; i < scoreBoard.player2Words.size(); i++)
        {
            if (playerWords.contains(scoreBoard.player2Words.get(i)))
                scoreBoard.player2Words.remove(i);
        }
        playerWords = scoreBoard.player1Words.toString();
        playerWords = playerWords.replaceAll("[\\[\\] ]", "");
        playerWords = playerWords.replaceAll("[\\,]", "\n");
        AIWords = scoreBoard.player2Words.toString();
        AIWords = AIWords.replaceAll("[\\[\\] ]", "");
        AIWords = AIWords.replaceAll("[\\,]", "\n");
        sPlaying = false;
        inPostGame = true;

    }
    public void postGame()
    {
        textSize(48);
        background(240);
        fill(20);
        textAlign(CENTER);
        text(playerWords, 300, 220);
        text(AIWords, width-300, 220);
        strokeWeight(5);
        line(125, 170, 475, 170);
        text("Player Words", 300, 150);
        line(width-125,170,width-475,170);
        text("AI Words", width-300, 150);


    }
    void update(int x, int y)
    {
        if (!locked)
        {
            playButton.update();
            multiPlayButton.update();
        } else
        {
            locked = false;
        }
    }

    public void mousePressed()
    {
        if (inMainMenu)
        {
            if (playButton.pressed())
            {
                currentColor = playButton.baseColor;
                inMainMenu = false;
                sPlaying = true;
            }
            if (multiPlayButton.pressed())
            {
                currentColor = multiPlayButton.baseColor;
                inMainMenu = false;
                mPlaying = true;
            }
        }
    }

    public void mouseClicked()
    {
        System.out.println("main");
    }

    public void keyPressed()
    {
        if (typing.length() > 24) //if there is an error message, clear it upon text input
            typing = "";
        // If the return key is pressed, save the String and clear it
        if (key == '\n' )
        {
            if (typing.length() > 0)
            {
                input = typing;
                input = input.toLowerCase();
                typing = "";
                //make sure word is at least 4 letters
                if (input.length() < 3)
                    typing = "Word must be at least 3 letters!";

                    //make sure word is in dictionary
                else if (dict.dictionary.get(dict.hash(input)) == null)
                    typing = "Word is not in dictionary!";

                    //make sure word is on board
                else if(!board.hasWord(input))
                    typing = "Word is not on the board!";
                else
                {
                    if(scoreBoard.scoreWord(input, 0))
                        typing = "Congratulations, you found a word";
                    else
                        typing = "You already found that word";

                }
            }
        }
        else if (key == BACKSPACE)
        {
            if (typing.length() > 0)
                typing = typing.substring(0, typing.length()-1);
        }
        else
        {
            if (keyCode != SHIFT)
                if(typing.length() < 30)
                    typing += key;
        }
    }

    public void printAllWords()
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
            if (word.length() > 2 && board.hasWord(word))
            {
                System.out.println(word);
            }
        }
    }
}
