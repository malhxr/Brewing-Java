// This class provides editDistance
public class Sequences {

    public static int editDistance(String w1, String w2) {
        int size1 = w1.length();
        int size2 = w2.length();

        // Initialize a 2D array to store edit distance values
        int[][] arrofdis1 = new int[size1 + 1][size2 + 1];

        // Initialize the first row with incremental values
        for (int z = 0; z <= size1; z++) {
        	arrofdis1[z][0] = z;
        }

        // Initialize the first column with incremental values
        for (int g = 0; g <= size2; g++) {
        	arrofdis1[0][g] = g;
        }

        // Iterate though, and check last char
        for (int h = 0; h < size1; h++) {
            char ch1 = w1.charAt(h);
            for (int j1 = 0; j1 < size2; j1++) {
                char ch2 = w2.charAt(j1);

                if (ch1 == ch2) 
                {
                	arrofdis1[h + 1][j1 + 1] = arrofdis1[h][j1];
                } else 
                {
                    int readd = arrofdis1[h][j1] + 1;
                    int add = arrofdis1[h][j1 + 1] + 1;
                    int remve = arrofdis1[h + 1][j1] + 1;

                    int least = Math.min(readd, add);
                    least = Math.min(remve, least);
                    arrofdis1[h + 1][j1 + 1] = least;
                }
            }
        }
        return arrofdis1[size1][size2];
    }

    public static void main(String[] args) {
        String word1 = "Grate";
        String word2 = "Great";
        int updates = Sequences.editDistance(word1, word2);

        System.out.println("\nThe word \"" + word1 + "\" takes " + updates
                + " update(s) to convert to \"" + word2 + "\"");
    }
}
