import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.lang.StringBuilder;
import java.math.RoundingMode;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;

public class Cube {

    private String name;
    private String filePath;
    private int size;
    private ArrayList<String> cardArray;
    private ArrayList<String> artifactArray;
    private ArrayList<String> bounceArray;
    private ArrayList<String> countersArray;
    private ArrayList<String> disruptionArray;
    private ArrayList<String> landArray;
    private ArrayList<String> massRemovalArray;
    private ArrayList<String> removalArray;
    private ArrayList<String> walkersArray;
    private ArrayList<String> duplicates;
    private HashMap<String, Double> stats;

    public Cube(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
        this.cardArray = pathToArray(filePath);
        this.size = cardArray.size();
    }

    public static ArrayList<String> pathToArray(String filePath) {
        File file = new File(filePath);
        ArrayList<String> cardArray = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String card = scanner.nextLine();
                if (!card.isBlank()) {
                    String parsedCard = card.replace(",", "|");
                    cardArray.add(parsedCard);
                }

            }
            scanner.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        Collections.sort(cardArray);
        return cardArray;
    }

    public static ArrayList<String> loadCheck(String filePath) {
        ArrayList<String> cardArray = pathToArray(filePath);
        ArrayList duplicates = checkDuplicates(cardArray);
        if (!duplicates.isEmpty()) {
            System.out.println("Duplicate Detetected: " + duplicates + " in " + filePath);
        }
        return cardArray;
    }

    public static ArrayList<String> checkDuplicates(ArrayList<String> cardArray) {
        HashSet<String> singleSet = new HashSet<String>();
        ArrayList<String> duplicateArray = new ArrayList<String>();
        for (String card : cardArray) {
            if (!singleSet.add(card)) {
                duplicateArray.add(card);
            }
        }

        return duplicateArray;
    }

    public static ArrayList<String> findHits(Cube cube, ArrayList<String> master) {
        var hitList = new ArrayList<String>();
        for (String card : cube.cardArray) {
            if (master.contains(card)) {
                hitList.add(card);
            }
        }
        Collections.sort(hitList);
        return hitList;
    }

    // just loops findHits method for each cube in cubeList Array
    public static void findHitsAll(ArrayList<Cube> cubeList, Cube master) {
        for (Cube cube : cubeList) {
            cube.artifactArray = findHits(cube, master.artifactArray);
            cube.bounceArray = findHits(cube, master.bounceArray);
            cube.countersArray = findHits(cube, master.countersArray);
            cube.disruptionArray = findHits(cube, master.disruptionArray);
            cube.landArray = findHits(cube, master.landArray);
            cube.removalArray = findHits(cube, master.removalArray);
            cube.walkersArray = findHits(cube, master.walkersArray);
            cube.massRemovalArray = findHits(cube, master.massRemovalArray);

        }
    }

    public static Double calculate(Cube cube, ArrayList<String> hitArray) {
        double unformatPercent = ((double) hitArray.size()) / cube.size * 100.0;
        DecimalFormat formatter = new DecimalFormat("##.##");
        Double percent = Double.valueOf(formatter.format(unformatPercent));
        return percent;
    }

    // just loops calculate method for each cube in cubeList Array
    public static void calculateAll(ArrayList<Cube> cubeList) {
        for (Cube cube : cubeList) {
            cube.stats = new HashMap<String, Double>();
            cube.stats.put("Artifact-Fixing", calculate(cube, cube.artifactArray));
            cube.stats.put("Bounce", calculate(cube, cube.bounceArray));
            cube.stats.put("Counters", calculate(cube, cube.countersArray));
            cube.stats.put("Disruption (hand)", calculate(cube, cube.disruptionArray));
            cube.stats.put("Land-Fixing", calculate(cube, cube.landArray));
            cube.stats.put("Removal", calculate(cube, cube.removalArray));
            cube.stats.put("Walkers", calculate(cube, cube.walkersArray));
            cube.stats.put("Mass Removal", calculate(cube, cube.massRemovalArray));

        }
    }

    public static void main(String[] args) {

        ArrayList<Cube> cubeList = new ArrayList<Cube>();

        // change the directory C:\\Users\\Jason\\Desktop\\ if needed:
        String parentPath = "C:\\Users\\Jason\\Desktop\\Cube Project\\";

        Cube master = new Cube("master", parentPath + "Master Lists\\Master.txt");
        master.artifactArray = loadCheck(parentPath + "Master Lists\\Artifact Fixing.txt");
        master.bounceArray = loadCheck(parentPath + "Master Lists\\Bounce.txt");
        master.countersArray = loadCheck(parentPath + "Master Lists\\Counters.txt");
        master.disruptionArray = loadCheck(parentPath + "Master Lists\\Discard.txt");
        master.landArray = loadCheck(parentPath + "Master Lists\\Land Fixing.txt");
        master.removalArray = loadCheck(parentPath + "Master Lists\\Removal.txt");
        master.walkersArray = loadCheck(parentPath + "Master Lists\\Walkers.txt");
        master.massRemovalArray = loadCheck(parentPath + "Master Lists\\Mass Removal.txt");

        // create cube objects here:
        Cube thunder = new Cube("THUNDERWANG", parentPath + "Cube Lists\\THUNDERDOMEXMAGELIST.txt");
        cubeList.add(thunder);
        Cube ikzann = new Cube("ikzann", parentPath + "Cube Lists\\Reasonablecube.txt");
        cubeList.add(ikzann);
        Cube steveMan = new Cube("The_Steve_Man", parentPath + "Cube Lists\\540_high_octane_cube.txt");
        cubeList.add(steveMan);
        Cube lemem = new Cube("Lemem", parentPath + "Cube Lists\\Lemems450UnderpoweredCube.txt");
        cubeList.add(lemem);
        Cube kitsuneCurator = new Cube("KitsuneCurator", parentPath + "Cube Lists\\400LegacyCube.txt");
        cubeList.add(kitsuneCurator);
        Cube steveIce = new Cube("steve_ice", parentPath + "Cube Lists\\LessAggressive360Fair.txt");
        cubeList.add(steveIce);
        Cube cbs2018 = new Cube("cbs2018", parentPath + "Cube Lists\\BrawlStandardV4.txt");
        cubeList.add(cbs2018);
        Cube suri = new Cube("suri", parentPath + "\\Cube Lists\\SurisCube.txt");
        cubeList.add(suri);
        Cube visserdrix = new Cube("Visserdrix", parentPath + "\\Cube Lists\\VisserdrixCube.txt");
        cubeList.add(visserdrix);
        Cube domcosta = new Cube("domcosta", parentPath + "\\Cube Lists\\domcosta.txt");
        cubeList.add(domcosta);
        Cube mapcaster = new Cube("Mapcaster Snage [Vintage]", parentPath + "\\Cube Lists\\540VintageUnpowered.txt");
        cubeList.add(mapcaster);
        Cube mapcaster2 = new Cube("Mapcaster Snage [Fair]", parentPath + "\\Cube Lists\\360LegacyFair.txt");
        cubeList.add(mapcaster2);
        Cube strider212 = new Cube("Strider212", parentPath + "\\Cube Lists\\Strider212sUnpoweredModernishCube.txt");
        cubeList.add(strider212);
        Cube schweinefett = new Cube("schweinefett", parentPath + "\\Cube Lists\\Actual385cubeoldbordered.txt");
        cubeList.add(schweinefett);
        Cube indigoZach = new Cube("indigo.zach", parentPath + "\\Cube Lists\\450Peasant.txt");
        cubeList.add(indigoZach);
        Cube thesidestepkids = new Cube("thesidestepkids", parentPath + "\\Cube Lists\\360Unpowered.txt");
        cubeList.add(thesidestepkids);
        Cube cygnetFreud = new Cube("CygnetFreud", parentPath + "\\Cube Lists\\NicksCube.txt");
        cubeList.add(cygnetFreud);
        Cube tjornan = new Cube("Tjornan", parentPath + "\\Cube Lists\\450_unpowered.txt");
        cubeList.add(tjornan);
        Cube strictlycheese = new Cube("strictlycheese", parentPath + "\\Cube Lists\\cube_of_cheese.txt");
        cubeList.add(strictlycheese);

        // loop findHits and calculateAll method for each cube
        findHitsAll(cubeList, master);
        calculateAll(cubeList);
        
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String dateStamp = date.format(dateFormatter);

        //write the results of each cube to ONE .csv file
        try {
            FileWriter fileWriter = new FileWriter(parentPath + "\\Results\\Results " + dateStamp + ".csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            //create column names
            printWriter.println(
                dateStamp + "\n" 
                + "Name" + "," 
                + "Size " + "," 
                + "Removal %" + "," 
                + "Mass Removal %" + ","
                + "Land-Fixing %" + "," 
                + "Artifact-Fixing %" + "," 
                + "Walker %" + "," 
                + "Bounce %" + ","
                + "Counters %" + "," 
                + "Disruption (hand) %" + "," 
                + "," 
                + "Removal/draft" + "," 
                + "Mass Removal/draft" + ","
                + "Land-Fixing/draft" + "," 
                + "Artifact-Fixing/draft" + "," 
                + "Walker/draft" + "," 
                + "Bounce/draft" + ","
                + "Counters/draft" + "," 
                + "Disruption (hand)/draft");

            NumberFormat numf = new DecimalFormat ("##.##");
            final double perDraft = (360.0/100.0); 

            ///create the records (rows of information)
            for (Cube cube : cubeList) {
                printWriter.print(
                    cube.name + "," 
                    + cube.size + "," 
                    + cube.stats.get("Removal") + ","
                    + cube.stats.get("Mass Removal") + "," 
                    + cube.stats.get("Land-Fixing") + ","
                    + cube.stats.get("Artifact-Fixing") + "," 
                    + cube.stats.get("Walkers") + ","
                    + cube.stats.get("Bounce") + "," 
                    + cube.stats.get("Counters") + ","
                    + cube.stats.get("Disruption (hand)") + "," 
                    + "," 
                    + numf.format(cube.stats.get("Removal")*perDraft) + "," 
                    + numf.format(cube.stats.get("Mass Removal")*perDraft) + "," 
                    + numf.format(cube.stats.get("Land-Fixing")*perDraft) + ","
                    + numf.format(cube.stats.get("Artifact-Fixing")*perDraft) + "," 
                    + numf.format(cube.stats.get("Walkers")*perDraft) + ","
                    + numf.format(cube.stats.get("Bounce")*perDraft) + "," 
                    + numf.format(cube.stats.get("Counters")*perDraft) + ","
                    + numf.format(cube.stats.get("Disruption (hand)")*perDraft)
                    + "\n");
                printWriter.flush();
                }  
            printWriter.close();
               
        } catch (
            Exception exception) {
            System.out.println(exception);
        }

        //write all found cards to SEPARATE cube csv files
        try {
            for (Cube cube : cubeList) {
                FileWriter fileWriter = new FileWriter(parentPath + "\\Results\\" + cube.name + " " + dateStamp+ ".csv");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                
                //create column names
                printWriter.println(
                    cube.name + "," 
                    + dateStamp + "\n" 
                    + "Removal" + "," 
                    + "Mass Removal" + "," 
                    + "Land-Fixing" + "," 
                    + "Artifact-Fixing" + "," 
                    + "Walkers" + "," 
                    + "Bounce" + "," 
                    + "Counters" + "," 
                    + "Disruption (hand)");

                for (int i = 0; i<150; i++ ) {

                    /*150 is just some arbitrary number. Technically it should be based off
                    of the array with the highest number of cards in them.
                    I'm pretty sure there's a better way to control the loop.
                    I might add all the arrays into another arraylist and loop through from there
                    to clean up the code*/

                    if (i < cube.removalArray.size()) {
                        printWriter.print(cube.removalArray.get(i));
                    } else {
                        printWriter.print("");
                    }
                    if (i < cube.massRemovalArray.size()) {
                        printWriter.print("," + cube.massRemovalArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.landArray.size()) {
                        printWriter.print("," + cube.landArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.artifactArray.size()) {
                        printWriter.print("," + cube.artifactArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.walkersArray.size()) {
                        printWriter.print("," + cube.walkersArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.bounceArray.size()) {
                        printWriter.print("," + cube.bounceArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.countersArray.size()) {
                        printWriter.print("," + cube.countersArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    if (i < cube.disruptionArray.size()) {
                        printWriter.print("," + cube.disruptionArray.get(i));
                    } else {
                        printWriter.print("," + "");
                    }
                    printWriter.println();
                    printWriter.flush();
                }
                printWriter.close();

            }

        } catch (
            Exception exception) {
            System.out.println(exception);
        }
    }
}
