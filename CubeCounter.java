import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 
import java.io.FileNotFoundException;
import java.util.List;
import java.lang.StringBuilder;
import java.util.Arrays;

/* Version 5
- Split up code into more methods
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
  public static List<String> getHits(List<String> query, List<String> cube) {

    List<String> hit_cards_list = new ArrayList<String> ();
    for (int i = 0; i < query.size(); ++i) { 
      for (int j = cube.size() - 1; j >= 0; --j) { //only going backwards here for practice
        if (query.get(i).equalsIgnoreCase(cube.get(j) ) ) {
        hit_cards_list.add(cube.get(j));
        }
      }
    }
    return hit_cards_list;
  }


  public static String calculateToString(String query_name, List<String> query, List<String> cube) {

    List<String> hitListHereJustToGetAroundScopeProblem = getHits(query, cube);

    //Format data to look pretty
    Collections.sort(hitListHereJustToGetAroundScopeProblem);
    double hit_percentage = (double) hitListHereJustToGetAroundScopeProblem.size() /cube.size() * 100.0;
    String hit_percentage_formatted = String.format("%.2f", hit_percentage);

    StringBuilder stringy = new StringBuilder();
    for (String hits : hitListHereJustToGetAroundScopeProblem) {
      stringy.append("Match: " ).append(hits).append("\n");
    }
    stringy.append("\n").append("Total cards in cube: ").append(cube.size())
    .append("\n").append("Number of ").append(query_name).append(" ").append(hitListHereJustToGetAroundScopeProblem.size())
    .append("\n").append("Number of ").append(query_name).append(" cards found: ").append (hitListHereJustToGetAroundScopeProblem.size())
    .append("\n").append(query_name).append(" in cube: ").append(hit_percentage_formatted).append("%");
    
    return stringy.toString();    
  }
  
    public static void main(String[] args) {
      String cube_path = "C:\\Users\\Jason\\Desktop\\THUNDER.txt";
      String query_path = "C:\\Users\\Jason\\Desktop\\Master.txt";
      String query_name = "Removal";

      List<String> query = readQuery(query_path);
      List<String> cube = readCube(cube_path);
      String results = calculateToString(query_name, query, cube);
      System.out.println(results);
    
    }
}
