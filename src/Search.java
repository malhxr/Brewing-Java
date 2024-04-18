import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

// This class provides functionality for searching a phrase in files using an InvertedIndex created by PreSearch.java.
public class Search {
    // Keeps track of word search frequency
	private static Map<String, Integer> wordFrequency = new HashMap<>();

    // Searches for a phrase in the files using InvertedIndex created by PreSearch.java.
    public static void searchPhrase(String keyword, int numberOfResults) {
        // Create a PreSearch instance to access the InvertedIndex
    	PreSearch ps = new PreSearch();
        String phrase = keyword;
        
        // Find files containing the given phrase in the InvertedIndex
        ArrayList<String> as = ps.find(phrase);
        phrase = phrase.toLowerCase();
        
        // If the phrase is not found, perform a similar word search
        if (as == null) {
            SearchSimilarWords.searchSimilar(phrase, numberOfResults, ps);
        } else {
            // Sort and print the results based on rank
            Map<String, Integer> sortedMap = SortResultsByRank.sortByRank(as, phrase);
            printResult(sortedMap, numberOfResults);
        }
        // Track the frequency of word searches and display the count
        trackWord(phrase);
        showWordSearchFrequency(phrase);
    }

    //Tracks the frequency of word searches.
    public static void trackWord(String word) {
        int count = wordFrequency.getOrDefault(word, 0);
        wordFrequency.put(word, count + 1);
    }

    //Displays the frequency of word searches.
    public static void showWordSearchFrequency(String word) {
        Integer frequency = wordFrequency.getOrDefault(word, 0);
        System.out.println("the number of time this word"+"'"+ word + "'"+"has been searched previously is :"+ frequency);
    }
    
    // Prints given number of results from the provided Map
    public static void printResult(Map<String, Integer> result, int numberOfResults) {
        Iterator<Entry<String, Integer>> iterator = result.entrySet().iterator();
        int i = 0;
        // Iterate through the results and print the details
        while (iterator.hasNext() && numberOfResults > i++) {
            Map.Entry<String, Integer> me = iterator.next();
            String fileName = me.getKey();
            File f = new File("WebPages/" + fileName);
            In in = new In(f);
            String url = in.readLine();
            
            // Display the result details
            System.out.println("-----------------------------------------");
            System.out.println(fileName.substring(0, fileName.length() - 4) + "\t\tOccurrences : " + me.getValue());
            System.out.println(url);
        }
    }

    public static void main(String[] args) {
        Search.searchPhrase("voice", 5);
    }
}
