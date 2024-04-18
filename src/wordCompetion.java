import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//TrieNode1 class represents a node in the Trie data structure
class NodeofTrie1 {
    char data;
    boolean isEndOfWord;
    NodeofTrie1[] children;

    public NodeofTrie1(char data) {
        this.data = data;
        this.isEndOfWord = false;
        this.children = new NodeofTrie1[26]; // Assuming only lowercase alphabetical characters
    }
}

//Trie class implements the Trie data structure
class DSTrie {
    private NodeofTrie1 rt;

    public DSTrie() {
        rt = new NodeofTrie1('\0');
    }

    // Insert a word into the Trie
    public void wordinsert(String word) {
    	NodeofTrie1 current = rt;

        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (current.children[idx] == null) {
                current.children[idx] = new NodeofTrie1(ch);
            }
            current = current.children[idx];
        }

        current.isEndOfWord = true;
    }
    
    // Search for a complete word in the Trie
    public boolean search(String word) {
    	NodeofTrie1 node = nodefinding(word);
        return node != null && node.isEndOfWord;
    }

    // Suggest words based on a given prefix
    public void suggestWords(String prefix) {
    	NodeofTrie1 prefixNode = nodefinding(prefix);

        if (prefixNode != null) {
            suggestWordsHelper(prefix, prefixNode);
        } else {
            System.out.println("No suggestions for the given prefix.");
        }
    }

    // Helper method to search for a node in the Trie
    private NodeofTrie1 nodefinding(String wd) {
    	NodeofTrie1 current = rt;

        for (char ch : wd.toCharArray()) {
            int index = ch - 'a';
            current = current.children[index];
            if (current == null) {
                return null; // Prefix not found
            }
        }

        return current;
    }

    // Helper method to recursively suggest words
    private void suggestWordsHelper(String prefix, NodeofTrie1 node) {
        if (node.isEndOfWord) {
            System.out.println(prefix);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                suggestWordsHelper(prefix + node.children[i].data, node.children[i]);
            }
        }
    }
} 

//Main class for word completion functionality
public class wordCompetion 
{
    // Method to perform word completion
    public static void completeWordInsert() 
    {
    	DSTrie objoftrie = new DSTrie();

        // Read words from text files and build the trie
        readWordsFromFiles("C:\\Users\\Mahir\\Downloads\\fitnessclub-search-engine\\fitnessclub-search-engine\\fitnessclub-search-engine\\WebPages", objoftrie);
        Scanner sc1 = new Scanner(System.in);
        
        // Continuous user input
        boolean menu = true;
        while(menu)
        {
        	System.out.println("Enter exit to exit the feature ");
            System.out.print("Enter partial word: ");
            String inp1 = sc1.nextLine();

            if ("exit".equalsIgnoreCase(inp1)) {
                System.out.println("Exiting the application.");
                menu = false;
            }

            if (!inp1.isEmpty()) {
            	objoftrie.suggestWords(inp1);
            }

        }
//        sc1.close();
    }

    // Method to read words from text files and insert them into the Trie
    private static void readWordsFromFiles(String folderPath, DSTrie trie) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] words = line.split("\\s+");
                            for (String word : words) {
                                // For simplicity, remove non-alphabetic characters
                                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                                if (!word.isEmpty()) {
                                    trie.wordinsert(word);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}