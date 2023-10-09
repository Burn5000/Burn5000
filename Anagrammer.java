import javax.lang.model.util.ElementScanner14;
import javax.swing.RowFilter.ComparisonType;
import java.io.FileReader;   //  Read Unicode chars from a file.
import java.io.IOException;  //  In case there's IO trouble.

//Anagrammer Project in Java
class AnagramTree {
    private TreeNode root;
    private static int letters = 26;
    private class TreeNode {
        private WordNode words;
        private byte [] summary;
        private TreeNode left;
        private TreeNode right;
    
        private TreeNode(WordNode word,byte[] summary,TreeNode left,TreeNode right) {
            this.words = word;
            this.summary = summary;
            this.left = left;
            this.right = right;
        }
    }
    private class WordNode
    {
        private String word;
        private WordNode next;
        private WordNode(String word,WordNode next)
        {
            this.word = word;
            this.next = next;
        }
        
    }
    public AnagramTree()
    {
        root = new TreeNode(new WordNode("", null), new byte [26], null, null);
    }
    public void add(String word)
    {
        
            TreeNode subtree = root;
            while(true)
            {
                
                int test = compareSummaries(stringToSummary(word),subtree.summary);
                if (test < 0)
                {
                    if (subtree.left == null)
                    {
                        subtree.left = new TreeNode(new WordNode(word,null),stringToSummary(word),null,null);
                    }
                    else
                    {
                        subtree = subtree.left;
                    }
                }
                else if(test > 0)
                {
                    if (subtree.right == null)
                    {
                        subtree.right = new TreeNode(new WordNode(word,null),stringToSummary(word),null,null);
                    }
                    else
                    {
                        subtree = subtree.right;
                    }
                }
            else 
            {
            WordNode current = subtree.words;
            WordNode previous = null;
              while (current != null) 
              {
                if (word.compareTo(current.word) < 0)
                {
                    if (previous == null) 
                    {
                      subtree.words = new WordNode(word, current);
                    } 
                    else 
                    {
                      previous.next = new WordNode(word, current);
                    }
                    return;
                }
                previous = current;
                current = current.next;
              }                         

            }
        }
    }
    public void anagrams()
    {
        if(root == null)
        {
            throw new IllegalStateException("Tree is empty.");
        }
        else
        {
            heighting(root);
        }
    }
    private void heighting(TreeNode root)
    {
      if (root == null)
      {
        return;
      }
      else
      {
        WordNode index = root.words;
        if (index.next != null)
        {
        while(index != null)
        {
            System.out.println(index.word);
            index = index.next;
        }
        }
         heighting(root.left);
         heighting(root.right);
        
        
      }
    }
    private int compareSummaries(byte[] left, byte[] right) {
        for (int i = 0; i < 26; i++) {
            if (left[i] < right[i]) {
                return left[i] - right[i]; // s1 is smaller should be negative
            } 
            else if (left[i] > right[i])
            {
                return left[i] - right[i]; // s2 is smaller should be positive
            }
        }
        return 0; //summaries are equal

        
    }    
    private byte[] stringToSummary(String word)
    {
        byte[] summary = new byte[letters];
        for(int i = 0;i < word.length(); i +=1)
        {
            summary[word.charAt(i) - 'a'] +=1;
        }
        return summary;
    }
}
class Words
{
  private int           ch;      //  Last CHAR from READER, as an INT.
  private FileReader    reader;  //  Read CHARs from here.
  private StringBuilder word;    //  Last word read from READER.

//  Constructor. Initialize an instance of WORDS, so it reads words from a file
//  whose pathname is PATH. Throw an exception if we can't open PATH.

  public Words(String path)
  {
    try
    {
      reader = new FileReader(path);
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      throw new IllegalArgumentException("Cannot open '" + path + "'.");
    }
  }

//  HAS NEXT. Try to read a WORD from READER, converting it to lower case as we
//  go. Test if we were successful.

  public boolean hasNext()
  {
    word = new StringBuilder();
    while (ch > 0 && ! isAlphabetic((char) ch))
    {
      read();
    }
    while (ch > 0 && isAlphabetic((char) ch))
    {
      word.append(toLower((char) ch));
      read();
    }
    return word.length() > 0;
  }

//  IS ALPHABETIC. Test if CH is an ASCII letter.

  private boolean isAlphabetic(char ch)
  {
    return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z';
  }

//  NEXT. If HAS NEXT is true, then return a WORD read from READER as a STRING.
//  Otherwise, return an undefined STRING.

  public String next()
  {
    return word.toString();
  }

//  READ. Read the next CHAR from READER. Set CH to the CHAR, represented as an
//  INT. If there are no more CHARs to be read from READER, then set CH to -1.

  private void read()
  {
    try
    {
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      ch = -1;
    }
  }

//  TO LOWER. Return the lower case ASCII letter which corresponds to the ASCII
//  letter CH.

  private char toLower(char ch)
  {
    if ('a' <= ch && ch <= 'z')
    {
      return ch;
    }
    else
    {
      return (char) (ch - 'A' + 'a');
    }
  }

//  MAIN. For testing. Open a text file whose pathname is the 0th argument from
//  the command line. Read words from the file, and print them one per line.

  public static void main(String [] args)
  {
    Words words = new Words(args[0]);
    while (words.hasNext())
    {
      System.out.println("'" + words.next() + "'");
    }
  }
}

public class Anagrammer
{
    public static void main(String[] args)
    {
       Words reader = new Words("/Users/yusufmo/Documents/1933CSCI/warAndPeace.txt");
       AnagramTree tree = new AnagramTree();
       while (reader.hasNext()) 
       {
        String word = reader.next();
        tree.add(word);
       }
       tree.anagrams();

       

    }
    
    // Traverse the tree to print all its anagrams
}
