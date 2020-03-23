import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 

public class CardCounter_v1 {

static int removal_count = 0;
static int card_total = 1;

// Use an ArrayList here to track types of cards. I can also just use it to find the count
static ArrayList<String> removal_cards = new ArrayList<String> ();

  public static void main(String[] args) {
    try {
      File cube_file = new File("C:\\Users\\Jason\\Desktop\\THUNDER.txt");
      Scanner Reader = new Scanner(cube_file);
      
      while (Reader.hasNextLine() ) 
      {String data = Reader.nextLine();
        
        System.out.println(data);
        if (Reader.hasNextLine()) {card_total +=1;};
        if (data.equalsIgnoreCase("Counterspell")) {removal_count +=1; removal_cards.add("Counterspell");}
        if (data.equalsIgnoreCase("Lightning Bolt")) {removal_count +=1; removal_cards.add("Lightning Bolt");};
        if (data.equalsIgnoreCase("Swords to Plowshares")) {removal_count +=1; removal_cards.add("Swords to Plowshares");};
      } 
      Reader.close();
    }   
  
    catch (Exception e) 
    {System.out.println("Could not find file");}

    // sort the ArrayList
    Collections.sort(removal_cards);

    double removal_percentage = (double) removal_count/card_total * 100.0;
    String removal_percentage_formatted = String.format("%.2f", removal_percentage);
    
    System.out.println("\n" + "Total cards: " + (card_total));
    System.out.println("Removal count: " + removal_count);
    System.out.println("Removal percentage of cube: " + removal_percentage_formatted + "%" + "\n");
    System.out.println("Removal Cards:");
    for (String i : removal_cards) {System.out.println(i);}

  }
}

   
