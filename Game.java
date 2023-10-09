import java.util.Random;
//Solitaire Game written in Java

//  CARD. A playing card from a standard deck but not a joker. You can't extend
//  this class because it's FINAL.  You can't modify its instances, because its
//  methods provide no way to do that.

final class Card
{

//  RANK NAME. Printable names of card ranks.

  private static final String [] rankName =
  {
    "ace",       //   0
    "two",       //   1
    "three",     //   2
    "four",      //   3
    "five",      //   4
    "six",       //   5
    "seven",     //   6
    "eight",     //   7
    "nine",      //   8
    "ten",       //   9
    "jack",      //  10
    "queen",     //  11
    "king"       //  12
  };

//  SUIT NAME. Printable names of card suits.

  private static final String [] suitName =
  {
    "clubs",     //  0
    "diamonds",  //  1
    "hearts",    //  2
    "spades"     //  3
  };

  private int rank;  //  Rank of this CARD, between 0 and 12.
  private int suit;  //  Suit of this CARD, between 0 and 3.

//  CARD. Constructor. Make a new CARD, with a given RANK and SUIT.

  public Card(int rank, int suit)
  {
    if (0 <= rank && rank <= 12 && 0 <= suit && suit <= 3)
    {
      this.rank = rank;
      this.suit = suit;
    }
    else
    {
      throw new IllegalArgumentException("Illegal rank or suit.");
    }
  }

//  GET RANK. Return the RANK of this CARD.

  public int getRank()
  {
    return rank;
  }

//  GET SUIT. Return the SUIT of this CARD.

  public int getSuit()
  {
    return suit;
  }

//  TO STRING. Return a STRING that describes this CARD. For printing only!

  public String toString()
  {
    return rankName[rank] + " (" + rank + ") of " + suitName[suit];
  }
}


class Deck {
    private Card[] deck;
    private int top;
    private int num;

    public Deck() 
    {
        top = 0;
        deck = new Card[52];
        int top = 0;
        for (int i = 0; i < 13; i += 1) {
            for (int y = 0; y < 4; y += 1) {
                deck[top] = new Card(i, y);
                top += 1;
            }
        }
    }

    public Card deal() {
        num +=1;
        if (top >= 52) {
            return null;
        }
        Card dealtCard = deck[top];
        top += 1;
        return dealtCard;
    }

    public void shuffle() 
    {
        if (num==0) 
        {
            Random r = new Random();
            for (int i = 0; i < 52; i += 1) 
            {
            int j = r.nextInt(52);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
            }
        }
        else
        {
        throw new IllegalStateException("Cannot shuffle after dealing.");
        }
        top = 0;
    }
}

class Tableau {
    private Pile[] piles;
    
    public Tableau()
    {
        piles = new Pile[13];
        Deck decky = new Deck();
        decky.shuffle();
        // loop throuh the number suites which is twelve 
        // make new pile  new  pilie []
        for (int index = 0;index < 13;index +=1)
        {
            piles[index] = new Pile();
            piles[index].add(decky.deal());
            piles[index].add(decky.deal());
            piles[index].add(decky.deal());
            piles[index].add(decky.deal());

        }
    }
    public boolean play()
    {
        Card temp = piles[0].draw();
        System.out.println("Got "+ temp +" from Pile"+piles[0]);
        int i = 0;
        while(true)
        {
        if (piles[i].isEmpty())
        {
            for (i = 0; i < 14; i +=1)
            {
            
            if (piles[i].isEmpty())
            {
                System.out.println("Pile " + i + " is empty, We lost!");
                return false;
            }
            }
            System.out.println("We won!");
            return true;
            
        }    
        Card rat = piles[i].draw();
        System.out.println("Got "+ rat +" from Pile "+ i);

        if (temp.getSuit() == rat.getSuit())
        {
            i = temp.getRank();
        }
        if (temp.getSuit() != rat.getSuit())
        {
            i = rat.getRank();           
        }
        temp = rat;
        }   
    
    }
}

class Pile {
    private Layer top;
    private class Layer
    {
        private Card card;
        private Layer next;
        private Layer(Card card,Layer next)
        {
            this.card = card;
            this.next = next;
        }
    }
    public Pile()
    {
        top = null;
    }
    public void add(Card card)
    {
        top = new Layer(card,top);
    }
    public Card draw()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("No cards in pile.");
        }
        else
        {
            Layer temp = top;
            top = top.next;
            return temp.card;
        }
    }
    public boolean isEmpty()
    {
        return top == null;
    }
}
public class Game {
     public static void main(String [] args)
     {
     Tableau tab = new Tableau();
     tab.play();
     }

}

//public class Game {
 // public static void main(String[] args) {
   // int count = 0;
 //   boolean isWon = false;
  //  while (!isWon) {
     // Tableau tab = new Tableau();
     // isWon = tab.play();
     // count++;
  //  }
  //System.out.println("You won the game after " + count + " tries!");
 // }

