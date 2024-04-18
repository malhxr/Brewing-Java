import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Driver {
    // Displays Main menu
    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        // Main menu loop
        boolean menu = true;
        while (menu) {
            System.out.println("\n\n-----------------------------------------\nFitness Club Analysis\n-----------------------------------------");
            System.out.println("Press 1 to search for a keyword");
            System.out.println("Press 2 to to see membership deals of 3 fitness clubs");
            System.out.println("Press 3 to find best fitness memberhip deal");
            System.out.println("Press 4 to search for a pattern");
            System.out.println("Press 5 to complete a partial keyword");
            System.out.println("Press 0 to exit");
            System.out.println("-----------------------------------------");
            System.out.print("Select an option: ");
            String ans = null;
            if(sc.hasNextLine())
            {
            	ans = sc.nextLine();
            }
            
            // Switch case for main menu options
            switch (ans) {
                case "1":
                    System.out.print("Enter search keyword: ");
                    Search.searchPhrase(sc2.nextLine(), 10);
                    break;
                case "2":
                	CompareDeals.dataExtraction();
                	crawlerMenu();
                    menu = false;
                    break;
                case "3":
                	CompareDeals.comparePrices();
                    break;
                case "4":
                    patternSearchMenu();
                    break;
                case "5":
                	wordCompetion.completeWordInsert();
                    break;
                case "0":
                    System.out.println("Exiting, thanks for using our search engine");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong Input, Try again.");
            }
        }
        sc.close();
        sc2.close();
        }
        

    // Displays Crawl Menu.
    public static void crawlerMenu() {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        
        // Crawler menu loop
        boolean menu = true;
        while (menu) 
        {
            System.out.println("\n\n-----------------------------------------\nWeb Crawling\n-----------------------------------------");
            System.out.println("Press 1 to view detail of Fitness World GYM");
            System.out.println("Press 2 to view detail of Dwell GYM");
            System.out.println("Press 3 to view detail of Fit4Less GYM");
            System.out.println("Press 0 to exit");
            System.out.println("-----------------------------------------");
            System.out.print("Select an option: ");
            String ans = sc.nextLine();
            switch (ans) {
            case "0":
            	menu = false;
            default:
	            int userInput = 0;
	            try {
	                userInput = Integer.parseInt(ans);
	            } catch (NumberFormatException e) {
	                // Handle the case where userInput is not a valid integer
	                e.printStackTrace();
	            }
	            CompareDeals.readAndPrintDetails(userInput);
	            }
            
        }
        sc.close();
        sc2.close();
    }
    
    // Displays PatternSearch Menu.
    public static void patternSearchMenu() {
        Scanner sc = new Scanner(System.in);
        
        // Pattern search menu loop
        boolean menu = true;
        while (menu) {
            System.out.println("\n\n-----------------------------------------\nPattern Search\n-----------------------------------------");
            System.out.println("Press 1 to Search Pattern for Phone Number ");
            System.out.println("Press 2 to Search Pattern for Email ");
            System.out.println("Press 3 to Search Pattern for URL ");
            System.out.println("Press 4 to Search Pattern for Date ");
            System.out.println("Press 5 to Search Pattern for Credit Card");
            System.out.println("Press 6 to Search Pattern for IP ");
            System.out.println("Press 0 to exit");
            System.out.println("-----------------------------------------");
            System.out.print("Select an option: ");
            String ans = sc.nextLine();
            Map<String, List<String>> result;
            
            // Switch case for pattern search menu options
            switch (ans) {
                case "1":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.phoneNumberPattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;
                case "2":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.emailPattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;

                case "3":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.urlPattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;
                case "4":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.datePattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;

                case "5":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.creditCardPattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;
                case "6":
                    result = RegexPatternSearch.lookPatinFolder("WebPages", RegexPatternSearch.ipPattern());
                    System.out.println("Matches for given pattern:");
                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                        System.out.println("File: " + entry.getKey());
                        System.out.println("Matches: " + entry.getValue());
                        System.out.println();
                    }
                    break;
                        case "0":
                            System.out.println("Exiting, thanks for using our search");
//                            System.exit(0);
                            mainMenu();
                            break;
                        default:
                            System.out.println("Wrong Input!");
                    }
            }
            System.out.println("Exiting Program.");
            sc.close();
        }
    public static void main(String[] args) {
        mainMenu();
    }
}
