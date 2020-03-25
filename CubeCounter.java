import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 
import java.io.FileNotFoundException;
import java.util.List;

/* Version 3
- Removed static ArrayLists and changed ArrayList to List interface
- Implemented methods to clean up and compartmentalize the code 
- Cleaned up code spacing
- Will add other criteria later
*/

public class CubeCounter {

//method to read Master file
  public static List<String> readMaster(String master_path) {
    List<String> master_reference_list = new ArrayList<String> ();  
    try {
      File master_file = new File (master_path);
      Scanner reader1 =  new Scanner (master_file);
        while (reader1.hasNextLine()) { 
          String master_data = (reader1.nextLine());
          master_reference_list.add((master_data)); //populate array with what it reads
        }
      reader1.close();
    }
    catch (FileNotFoundException exception) {System.out.println("Some error occured with the master upload"); exception.printStackTrace();
    }

    return master_reference_list;
  }
// method to read Cube file
  public static List<String> readCube(String cube_path) {
    List<String> cube_cards_list = new ArrayList<String> ();
    try {
      File cube_file = new File (cube_path);
      Scanner reader2 =  new Scanner (cube_file);
        
        while (reader2.hasNextLine()) {
        String cube_data = reader2.nextLine(); 
        cube_cards_list.add(cube_data);
        }
      reader2.close();
    }
    catch (FileNotFoundException exception2) {System.out.println("Some error occured with the cube list upload");  exception2.printStackTrace();
    }
    return cube_cards_list; 
  }

// method to compare lists by using loops to compare the elements of arrays and add what it find to new array
  public static List<String> find(List<String> master, List<String> cube) {

    List<String> removal_cards_list = new ArrayList<String> ();

    for (int i = 0; i < master.size(); ++i) { 
      for (int j = cube.size() - 1; j >= 0; --j) { //only going backwards here for practice
        if (master.get(i).equalsIgnoreCase(cube.get(j) ) ) {
        removal_cards_list.add(cube.get(j));
        }
      }
    }
    Collections.sort(removal_cards_list); //sort array alphabetically 

    return removal_cards_list;
  }
    public static void main(String[] args) {
      String master_path = ("C:\\Users\\Jason\\Desktop\\Master.txt");
      String cube_path = ("C:\\Users\\Jason\\Desktop\\THUNDER.txt");

      List<String> master = readMaster(master_path);
      List<String> cube = readCube(cube_path);
      List<String> removal = find(master, cube);

      //Format to look pretty
      double removal_percentage = (double) removal.size() /cube.size() * 100.0;
      String removal_percentage_formatted = String.format("%.2f", removal_percentage);

      //Print Results
      for (String r : removal) {System.out.println("Match: " + r);}
      System.out.println("\n" + "Total cards in cube: " + cube.size() + "\n");
      System.out.println("Number of removal cards found: " + removal.size() + "\n");
      System.out.println("% of removal cards in cube: " + removal_percentage_formatted + "%" + "\n");
      System.out.println("List of removal cards found:");
    }
}
