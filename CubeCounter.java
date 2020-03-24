import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 
import java.io.FileNotFoundException;

/* version 2 
- added the FileNotFoundException for better troubleshooting
- Removed the removal_count and total_card_count; 
it's not needed if ArrayLists are being used because I can just use the .size() method
*/ 

/* how this code works:
  1. upload a master text file with cards you want to be found (it will make an ArrayList from this)
  2. upload the deck text file (it will make a separate ArrayList from this file)
  3. code will check for matching elements and list them in a new ArrayList
  */

public class CubeCounter {

//static variables and ArrayLists
static ArrayList<String> master_reference_list = new ArrayList<String> ();  
static ArrayList<String> cube_cards_list = new ArrayList<String> ();
static ArrayList<String> removal_cards_list = new ArrayList<String> ();

  public static void main(String[] args) {

    // Upload the Master Reference list here! (eg. removal cards to be searched);
    try {
      File master_file = new File ("C:\\Users\\Jason\\Desktop\\Master.txt");
      Scanner Reader1 =  new Scanner (master_file);
        
        while (Reader1.hasNextLine()) { 
        String master_data = (Reader1.nextLine());
        master_reference_list.add((master_data)); //populate array with what it reads
        }
      Reader1.close();
    }
    catch (FileNotFoundException exception) {System.out.println("some error occured with the master upload"); exception.printStackTrace();
    }

    // Upload the cube list here! (basically a repeat of the code above)
    try {
      File cube_file = new File ("C:\\Users\\Jason\\Desktop\\THUNDER.txt");
      Scanner Reader2 =  new Scanner (cube_file);
        
        while (Reader2.hasNextLine()) {
        String cube_data = (Reader2.nextLine()); 
        cube_cards_list.add((cube_data));
    }
      Reader2.close();
    }
    catch (FileNotFoundException exception) {System.out.println("some error occured with the cube list upload"); exception.printStackTrace();
    }
    
    // Using loops to compare the elements of 2 arrays and add what it finds to a new array
   
    for (int i = 0; i < master_reference_list.size(); ++i) 
    for (int j = cube_cards_list.size() - 1; j >= 0; --j) //only going backwards here for practice
      
    // need to add curly brackets here to be clear
    
    if (master_reference_list.get(i).equalsIgnoreCase(cube_cards_list.get(j)) )
      {removal_cards_list.add(master_reference_list.get(i));
    }
                                                            
    //practice more if-statements with ifs and fors in {}. First method I used multiplied my results by 3.

    //sorts array alphabetically 
    Collections.sort(removal_cards_list);

    //find percentage and format to precision of 2 and ensure it's not doing some weird integer division
    double removal_percentage = (double) removal_cards_list.size() /cube_cards_list.size() * 100.0;
    String removal_percentage_formatted = String.format("%.2f", removal_percentage);

    //Print Results
    for (String k : removal_cards_list) {System.out.println("Match: " + k);}
    System.out.println("\n" + "Total cards in cube: " + cube_cards_list.size() + "\n");
    System.out.println("Number of removal cards found: " + removal_cards_list.size() + "\n");
    System.out.println("% of removal cards in cube: " + removal_percentage_formatted + "%" + "\n");
    System.out.println("List of removal cards found:");
    
//note to self: make more methods out of this
  }
}
