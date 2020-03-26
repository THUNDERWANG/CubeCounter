/** CubeCounter v6
 * - added StringBuilder class
 * - cleaned up names of variables and methods
 * 
 * How it works:
 * This program scans multiple card lists, stores the cards as arrays, compares them and prints out results.
 * Change the String cube_path and String query_path to the appropriate file path
 */

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections; 
import java.io.FileNotFoundException;
import java.util.List;
import java.lang.StringBuilder;
import java.util.Arrays;

public class CubeCounter_v6 {

  //read cube file and store cards as ArrayList
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
    catch (FileNotFoundException exception) {System.out.println("Some error occured with the cube list upload");  exception.printStackTrace();
    }
    return cube_cards_list; 
  }

  //read query file and store cards as Arraylist
  public static List<String> readQuery(String query_path) {

    List<String> query_cards_list = new ArrayList<String> ();  
    try {
      File query_file = new File (query_path);
      Scanner reader =  new Scanner (query_file);
        while (reader.hasNextLine()) { 
          String query_data = reader.nextLine();
          query_cards_list.add((query_data)); //populate array with what it reads
        }
      reader.close();
    }
    catch (FileNotFoundException exception) {System.out.println("Some error occured with the query upload"); exception.printStackTrace();
    }
    return query_cards_list;
  }

  //compare ArrayLists by using loops to compare the elements and record the hits to a new Arraylist
  public static List<String> findHits(List<String> query, List<String> cube) {

    List<String> hit_cards_list = new ArrayList<String> ();
    for (int i = 0; i < query.size(); i++) { 
      for (int j = cube.size() - 1; j >= 0; j--) { //only going backwards here for practice
        if (query.get(i).equalsIgnoreCase(cube.get(j) ) ) {
        hit_cards_list.add(cube.get(j));
        }
      }
    }
    return hit_cards_list;
  }

  public static String calculateToString(String query_name, List<String> query, List<String> cube) {

    List<String> hit_cards_list = findHits(query, cube);

    //Format data to look pretty
    Collections.sort(hit_cards_list);
    double hit_percentage = (double) hit_cards_list.size() /cube.size() * 100.0;
    String hit_percentage_formatted = String.format("%.2f", hit_percentage);

    StringBuilder results_string = new StringBuilder();
    for (String hits : hit_cards_list) {
      results_string.append("Match: " ).append(hits).append("\n");
    }
    results_string.append("\nTotal cards in cube: ").append(cube.size())
    .append("\nNumber of ").append(query_name).append(" cards found: ").append(hit_cards_list.size()).append(" | ")
    .append(hit_percentage_formatted).append("%");
    
    return results_string.toString();    
  } 
  
    public static void main(String[] args) {
      String parent_path = "C:\\Users\\Jason\\Desktop\\";
      String cube_path = parent_path + "THUNDER" + ".txt";
      String query_path = parent_path + "Master" + ".txt";
      String query_name = "Removal";

      List<String> query = readQuery(query_path);
      List<String> cube = readCube(cube_path);
      String results = calculateToString(query_name, query, cube);
      System.out.println(results);
    
    }
}
