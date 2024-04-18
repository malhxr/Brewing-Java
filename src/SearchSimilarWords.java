import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;


public class SearchSimilarWords {
    // Creates the HashMap for distance of given word in the PreSearch database.
    private static HashMap<String, Integer> sortByEditDistance(String keyword, PreSearch ps) {
        HashMap<String, Integer> indexDistance = new HashMap<>();
        // Iterate through each word in the PreSearch index
        for (Entry<String, HashSet<Integer>> me : ps.index.entrySet()) {
            String word = me.getKey();
            
            // Calculate the edit distance between the keyword and the current word
            int distance = Sequences.editDistance(word, keyword);
            
            // Add words with edit distance less than 3 (excluding distance 0) to the HashMap
            if (distance < 3 && distance != 0) {
                indexDistance.put(word, distance);
            }
        }
        // Return a HashMap sorted by edit distance
        return SortResultsByRank.sortValues(indexDistance);
    }

    // Searches for similar words in the database.
    public static void searchSimilar(String keyword, int numberOfResults, PreSearch ps) {
        // Get a sorted HashMap of words and their edit distances
        HashMap<String, Integer> sortedDistance = sortByEditDistance(keyword, ps);
        // Iterate through the sorted HashMap
        for (Entry<String, Integer> me : sortedDistance.entrySet()) {
            String word = me.getKey();
            System.out.println("Instead showing results for : " + word);

            // Searching for the word with the lowest distance.
            Map<String, Integer> sortedMap;
            ArrayList<String> as = ps.find(word);
            String phrase = word.toLowerCase();

            // Sort the results by rank and print them
            sortedMap = SortResultsByRank.sortByRank(as, phrase);
            Search.printResult(sortedMap, numberOfResults);
            // Break after processing the word with the lowest edit distance
            break;
        }


    }
}
