import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 
import java.io.FileNotFoundException;
import java.util.List;

/* Version 4
- Introduced a query name to allow users to search up whatever criteria they want.
- Cleaned up code organization
- May rename variables later 

*/

public class CubeCounter {

//read cube file and store as ArrayList
  public static List<String> readCube(String cube_path) {
    List<String> cube_cards_list = new ArrayList<String> ();
    try {
      File cube_file = new File (cube_path);
      Scanner reader =  new Scanner (cube_file);
        
        while (reader.hasNextLine()) {
        String cube_data = reader.nextLine(); 
        cube_cards_list.add(cube_data);
        }
      reader.close();
    }
    catch (FileNotFoundException exception2) {System.out.println("Some error occured with the cube list upload");  exception2.printStackTrace();
    }
    return cube_cards_list; 
  }

  //read query file and store as Arraylist
  public static List<String> readQuery(String query_path) {
    List<String> query_reference_list = new ArrayList<String> ();  
    try {
      File query_file = new File (query_path);
      Scanner reader =  new Scanner (query_file);
        while (reader.hasNextLine()) { 
          String query_data = (reader.nextLine());
          query_reference_list.add((query_data)); //populate array with what it reads
        }
      reader.close();
    }
    catch (FileNotFoundException exception) {System.out.println("Some error occured with the query upload"); exception.printStackTrace();
    }

    return query_reference_list;
  }

//compare ArrayLists by using loops to compare the elements and record hits to a new Arraylist
  public static void compare(String query_name, List<String> query, List<String> cube) {

    List<String> hit_cards_list = new ArrayList<String> ();

    for (int i = 0; i < query.size(); ++i) { 
      for (int j = cube.size() - 1; j >= 0; --j) { //only going backwards here for practice
        if (query.get(i).equalsIgnoreCase(cube.get(j) ) ) {
        hit_cards_list.add(cube.get(j));
        }
      }
    }

    //Format data to look pretty
    Collections.sort(hit_cards_list);
    double hit_percentage = (double) hit_cards_list.size() /cube.size() * 100.0;
    String hit_percentage_formatted = String.format("%.2f", hit_percentage);

    //Print Results
    System.out.println(query_name + " found: ");
    for (String r : hit_cards_list) {System.out.println("Match: " + r);}
    System.out.println("\n" + "Total cards in cube: " + cube.size() + "\n");
    System.out.println("Number of " + query_name + " cards found: " + hit_cards_list.size() + "\n");
    System.out.println("% of " + query_name + " in cube: " + hit_percentage_formatted + "%" + "\n");
        
  }
    public static void main(String[] args) {
      String cube_path = ("C:\\Users\\Jason\\Desktop\\THUNDER.txt");
      String query_path = ("C:\\Users\\Jason\\Desktop\\Master.txt");
      String query_name = "removal";

      List<String> query = readQuery(query_path);
      List<String> cube = readCube(cube_path);
      compare(query_name, query, cube);
    
    }
}
 
