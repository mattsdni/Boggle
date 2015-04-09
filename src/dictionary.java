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
        String word;
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
    }

    /**
     * based on work by Dan Bernstein, djb2, http://www.cse.yorku.ca/~oz/hash.html
     * @param key
     * @return hash value
     */
    public int hash(String key)
    {
        int hashval = 0;
        int length = key.length();
        for(int i = 0; i < length; i++) hashval = key.charAt(i) + (hashval << 5) - hashval;
        return hashval;
    }
}
