import processing.core.PApplet;
import processing.core.PFont;
import java.util.*;

/**
 * Created by Matt Dennie on 3/28/2015.
 *
 Assignment: Lab 7
 Title: Boggle
 Course: CSCE 270
 Lab Section: 11:50am
 Semester: Fall, 2014
 Instructor: Laurie Murphy
 Sources consulted: stackoverflow, wikipedia
 Program description: Allows the user to play the computer in a game of boggle
 Known Bugs: no bugs, although AI could be improved
                make word guessing more natural
                develop better AI difficulty settings
                allow player to choose AI difficulty in menu
             Add PvP mode (2 humans), either local or network
 Creativity:
     GUI implemented
     Gameplay mechanics
        words guessed by the computer and the player do not count towards final points
        end game screen added. shows final word lists with cross referenced duplicates removed from both
        AI randomly 'finds' words on the board (see AI class)
        game is timed to 3 minutes (can be changed in Timer class)
     Board is randomly generated every time the program is launched
     Qu tile is available and counts as an extra point if used
     Dictionary uses hashtable for constant time word lookup
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
    boolean inPostGame = false;
    boolean inMainMenu = true;
    button playButton;
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

    /**
     * set up all variables and load data
     */
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
        scoreBoard = new ScoreBoard(this);
        stopWatch = new Timer(this);
        ai = new AI(this, 10);
        ai.findWords();
    }

    /**
     * this is the main loop during execution
     */
    public void draw()
    {
        if (inMainMenu)
            mainMenu();
        if (sPlaying)
            sPlay();
        if(inPostGame)
            postGame();
    }

    /**
     * the main menu
     */
    public void mainMenu()
    {
        background(100, 150, 200);
        fill(255);
        textAlign(CENTER);
        textSize(48);
        text("Boggle", width / 2, 200);
        playButton.display();
        update(mouseX, mouseY);
    }

    /**
     * single player game mode
     */
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
        ai.addWords();
        if (stopWatch.seconds < 0)
        {
            postGamePrep();
        }
    }

    /**
     * prepares the necessary data for the post game screen
     */
    public void postGamePrep()
    {
        delay(2000);
        //find unique player 1 words
        for (int i = 0; i < scoreBoard.player1Words.size(); i++)
        {
            for (int j = 0; j < scoreBoard.player2Words.size(); j++)
            {
                if (scoreBoard.player1Words.get(i).equals(scoreBoard.player2Words.get(j)))
                {
                    scoreBoard.player1Words.remove(i);
                    scoreBoard.player2Words.remove(j);
                    i--;
                    j--;
                    break;
                }
            }
        }
        //find unique player 2 (ai) words
        for (int i = 0; i < scoreBoard.player2Words.size(); i++)
        {
            for (int j = 0; j < scoreBoard.player1Words.size(); j++)
            {
                if (scoreBoard.player2Words.get(i).equals(scoreBoard.player1Words.get(j)))
                {
                    scoreBoard.player2Words.remove(i);
                    scoreBoard.player1Words.remove(j);
                    i--;
                    j--;
                    break;
                }
            }
        }
        //format word lists
        playerWords = scoreBoard.player1Words.toString();
        playerWords = playerWords.replaceAll("[\\[\\] ]", "");
        playerWords = playerWords.replaceAll("[\\,]", "\n");
        AIWords = scoreBoard.player2Words.toString();
        AIWords = AIWords.replaceAll("[\\[\\] ]", "");
        AIWords = AIWords.replaceAll("[\\,]", "\n");
        sPlaying = false;
        inPostGame = true;
    }

    /**
     * the post game screen
     */
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
        line(width - 125, 170, width - 475, 170);
        text("AI Words", width - 300, 150);
        int p1score = postGameScore(scoreBoard.player1Words);
        int p2score = postGameScore(scoreBoard.player2Words);
        text("Score: " + p1score, 300, height - 100);
        text("Score: " + p2score, width-300, height-100);
        if (p1score == p2score)
            text("Tie", width/2, 75);
        if (p1score > p2score)
            text("Player 1 Wins!", width/2, 75);
        if (p1score < p2score)
            text("Player 2 Wins!", width/2, 75);
    }

    /**
     * Calculates the scores at the end of the game
     * @param words
     * @return
     */
    public int postGameScore(LinkedList<String> words)
    {
        int score = 0;
        for (int i = 0 ; i < words.size(); i++)
        {
            if (words.get(i).length() == 3 || words.get(i).length() == 4)
                score++;
            else if (words.get(i).length() == 5)
                score += 2;
            else if (words.get(i).length() == 6)
                score += 3;
            else if (words.get(i).length() == 7)
                score += 5;
            else if (words.get(i).length() >= 8)
                score += 11;
            if (words.get(i).contains("qu"))
                score++;
        }
        return score;
    }

    /**
     * Calls the update function for any buttons created
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     */
    void update(int x, int y)
    {
        if (!locked){playButton.update();}
        else{locked = false;}
    }

    /**
     * called every time the mouse is pressed
     */
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
        }
    }

    /**
     * called every time a key is pressed.
     * This is used for word input.
     */
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
                {   //score the word
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
}
