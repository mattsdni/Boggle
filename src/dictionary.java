/**
 * Created by Matt on 3/28/2015.
 */
import java.io.File;
import java.io.IOException;
import java.util.*;

public class dictionary
{
    Hashtable dictionary;

    dictionary()
    {
        dictionary = new Hashtable();
    }

    public void load()
    {
        // Create the scanner (open the file for reading)
        Scanner scan = null;
        Scanner lineScan = null;  // used to parse one line
        String word = "";
        int lineNum = 0;                //keep track of current line number
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
            lineNum++;
            word = scan.nextLine();
            this.dictionary.put(hash(word), word);
        }


        System.out.println(dictionary.get(hash("adgaer")));

    }

    public int hash(String key)
    {
        int hashval;

    /* we start our hash out at 0 */
        hashval = 0;

    /* for each character, we multiply the old hash by 31 and add the current
     * character.  Remember that shifting a number left is equivalent to
     * multiplying it by 2 raised to the number of places shifted.  So we
     * are in effect multiplying hashval by 32 and then subtracting hashval.
     * Why do we do this?  Because shifting and subtraction are much more
     * efficient operations than multiplication.
     */
        int length = key.length();
        for(int i = 0; i < length; i++) hashval = key.charAt(i) + (hashval << 5) - hashval;

    /* we then return the hash value mod the hashtable size so that it will
     * fit into the necessary range
     */
        return hashval;
    }
}
