import processing.core.PApplet;

import java.util.LinkedList;

/**
 * Created by Matt on 3/29/2015.
 */
public class ScoreBoard
{
    PApplet parent;
    int playerScore, player2Score;
    LinkedList<String> player1Words, player2Words;

    ScoreBoard(PApplet p)
    {
        parent = p;
        playerScore = 0;
        player2Score = 0;
        player1Words = new LinkedList<String>();
        player2Words = new LinkedList<String>();
    }

    public void display()
    {
        parent.fill(20);
        parent.textAlign(parent.RIGHT);
        parent.text("Score: " + playerScore, parent.width - 20, 40);
        parent.textAlign(parent.CENTER);
        String temp = player1Words.toString();
        temp = temp.replaceAll("[\\[\\] ]", "");
        temp = temp.replaceAll("[\\,]","\n");
        parent.text(temp, 100, 195);
        parent.strokeWeight(5);
        parent.line(20,160,200,160);
        parent.text("Player Words", 110, 150);
        parent.line(parent.width-200,160,parent.width-20,160);
        parent.text("AI Words", parent.width-110, 150);
        String temp2 = player2Words.toString();
        temp2 = temp2.replaceAll("[\\[\\] ]", "");
        temp2 = temp2.replaceAll("[\\,]", "\n");
        parent.text(temp2, parent.width-100, 195);
    }

    /**
     * 
     * @param word
     * @param player 0 for player1 (human), 1 for player2/AI
     * @return
     */
    public boolean scoreWord(String word, int player)
    {
        if (player == 0)
        {
            if (taken(word, player)) //don't score a word if they already guessed it
                return false;
            if (word.length() == 3 || word.length() == 4)
                playerScore++;
            else if (word.length() == 5)
                playerScore += 2;
            else if (word.length() == 6)
                playerScore += 3;
            else if (word.length() == 7)
                playerScore += 5;
            else if (word.length() >= 8)
                playerScore += 11;
            return true;
        }
        if (player == 1)
        {
            if (taken(word, player)) //don't score a word if they already guessed it
                return false;
            if (word.length() == 3 || word.length() == 4)
                player2Score++;
            else if (word.length() == 5)
                player2Score += 2;
            else if (word.length() == 6)
                player2Score += 3;
            else if (word.length() == 7)
                player2Score += 5;
            else if (word.length() >= 8)
                player2Score += 11;
            return true;
        }
        return false;
    }

    private boolean taken(String word, int player)
    {
        if (player == 0)
        {
            if (player1Words.indexOf(word) != -1)
                return true;
            player1Words.add(word);
        }
        if (player == 1)
        {
            if (player2Words.indexOf(word) != -1)
                return true;
            player2Words.add(word);
        }
        return false;
    }

}
