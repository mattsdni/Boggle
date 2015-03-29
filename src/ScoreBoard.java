import processing.core.PApplet;

/**
 * Created by Matt on 3/29/2015.
 */
public class ScoreBoard
{
    PApplet parent;
    int score;
    ScoreBoard(PApplet p)
    {
        parent = p;
        score = 0;
    }

    public void display()
    {
        parent.fill(20);
        parent.textAlign(parent.RIGHT);
        parent.text("Score: "+ score, parent.width-20, 40);
    }

    public void scoreWord(String word)
    {
        if (word.length() == 3 || word.length() == 4)
            score++;
        else if (word.length() == 5)
            score += 2;
        else if (word.length() == 6)
            score += 3;
        else if (word.length() == 7)
            score += 5;
        else if (word.length() >= 8)
            score += 11;
    }

}
