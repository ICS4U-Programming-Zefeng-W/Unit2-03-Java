/*
 * This program reads the input.txt file and uses a recursive binary search function to
 * find the index of a specific element. It will then output the index to output.txt.
 *
 * By Zefeng Wang
 * Created on January 17, 2022
 *
*/

// import classes
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// class RecBinarySearch
class RecBinarySearch {

  public static void main(String[] args) throws IOException {

    // Reads each line of the file and separate elements and lists
    List<String> lines = Files.readAllLines(Paths.get("input.txt"),
                               StandardCharsets.UTF_8);
    Iterator<String> itr = lines.iterator();
    List<List<String>> arrayList = new ArrayList<List<String>>();
    List<Integer> elementList = new ArrayList<Integer>();
    List<String> outputList = new ArrayList<String>();
    
    // Store the inputs in two separate arrays
    for (int i = 0; itr.hasNext(); i++) {
      if (i % 2 == 0) {
        arrayList.add(Arrays.asList(itr.next().split(",")));
      } else {
        elementList.add(Integer.parseInt(itr.next()));
      }
    }
     
    // Convert 2D lists to 2D arrays 
    String[][] strArray; 
    int[][] numArray;

    strArray = new String[arrayList.size()][]; 
    numArray = new int[arrayList.size()][];

    for (int i = 0; i < arrayList.size(); i++) {
      List<String> row = arrayList.get(i);
      String[] copy = new String[row.size()];
      for (int j = 0; j < row.size(); j++) {
        copy[j] = row.get(j).trim();
      }
      strArray[i] = copy;
    }
    
    // Convert 2D String array to 2D int array
    for (int row = 0; row < numArray.length; row++) {
      numArray[row] = new int[strArray[row].length];
      for (int col = 0; col < numArray[row].length; col++) {
        try {
          numArray[row][col] = Integer.parseInt(strArray[row][col]);
        } catch (Exception e) {
          numArray[row] = new int[0];
        }
      }
    }

    // Add outputs and error messages to the outputList
    for (int row = 0; row < numArray.length; row++) {
      if (numArray[row].length == 0) {
        outputList.add("Please ensure that all values are integers."); 
      } else {
        outputList.add(binarySearch(numArray[row], elementList.get(row), numArray.length, 0));
      }
    }

    // Writes the contents of outputList to output.txt
    FileWriter writer = new FileWriter("output.txt");
    if (outputList.size() == 0) {
      writer.write("Please enter at least one number in input.txt");
    } else {
      for (String output : outputList) {
        writer.write(output + "\n");
      }
    }
    writer.close();
  }

  // Checks if array is sorted. If so, do binary search for a specific element in an array
  private static String binarySearch(int[] array, int ele, int high, int low) {
    
    // Checks if array is sorted 
    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] > array[i + 1]) {
        return "Please sort this list.";
      }
    }
    
    // Implements binary search 
    int mid = low + (high - low) / 2;

    if (high >= low && array.length != 0) {
      if (array[mid] == ele) {
        return Integer.toString(mid);
      } else if (ele < array[mid]) {
        return binarySearch(array, ele, mid - 1, low);
      } else if (ele > array[mid]) {
        return binarySearch(array, ele, high, mid + 1);
      }   
    } 
    return "Can't find the element in the list.";
  }
}
