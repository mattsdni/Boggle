import processing.core.PApplet;
import processing.core.PFont;

/**
 * Created by Matt on 3/28/2015.
 */
public class BoggleMain extends PApplet
{

    boggleBoard board;
    dictionary dict;
    PFont font;
    String dir;
    String input = "";
    String typing = "";
    boolean playing = false;
    boolean inMainMenu = true;
    button playButton;
    static boolean locked = false;
    int currentColor;
    ScoreBoard scoreBoard;
    Timer stopWatch;

    public static void main(String args[])
    {
        PApplet.main(new String[]{"BoggleMain"});
    }

    public void setup()
    {
        size(1000, 1000, P3D);
        dir = System.getProperty("user.dir");
        font = createFont(dir + "\\Data\\arialbd.ttf", 48);
        textFont(font, 48);
        dict = new dictionary();
        dict.load();
        board = new boggleBoard(this);
        playButton = new button(this, width / 2, 600, 200, 100, "Play", color(20), color(60));
        scoreBoard = new ScoreBoard(this);
        stopWatch = new Timer(this);
    }

    public void draw()
    {
        if (inMainMenu)
            mainMenu();
        if (playing)
            play();
    }

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

    public void play()
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

    void update(int x, int y)
    {
        if (!locked)
        {
            playButton.update();
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
                System.out.println("button press");
                inMainMenu = false;
                playing = true;
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
                    typing = "Congratulations, you found a word";
                    scoreBoard.scoreWord(input);
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

    public boolean onBoard()
    {
        return false;
    }


}
