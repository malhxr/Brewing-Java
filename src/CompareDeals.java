import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;

public class CompareDeals {
//	dataExtraction
    public static void dataExtraction() {
        // URLs and CSS selectors for the three gyms
        String[] gymUrls = {
                "https://www.fitnessworld.ca/explore-memberships/",
                "https://dwellgym.ca/membership/",
                "https://www.fit4less.ca/membership"
        };

        // CSS selectors for the three gyms
        String[] cssSelectors = {
                ".club-option__benefits",
                ".pricing-table .pricing-table-1",
                "span.membership-name"
        };


        try {
            // Iterate through gyms
            for (int i = 0; i < gymUrls.length; i++) 
            {
                Document doc = Jsoup.connect(gymUrls[i]).get();
                
                // Different processing for web scraping of each gym
                if (i == 0) 
                {
                    saveDetailsToFile("FitnessWorldDetails.txt", "Fitness World Membership Details:", doc);
                    Elements planElements = doc.select(cssSelectors[i]);
                    saveFitnessWorldPlansToFile("FitnessWorldDetails.txt", planElements);
                } else if (i == 1) 
                {
                    Elements pricingTables = doc.select(".pricing-table");
                    saveDetailsToFile("DwellGymDetails.txt", "Dwell Gym Membership Details:", doc);
                    savePricingTablesToFile("DwellGymDetails.txt", pricingTables);
                } else 
                {
                    Elements name = doc.select(cssSelectors[i]);
                    Elements price = doc.select("span.membership-price");
                    saveDetailsToFile("Fit4LessDetails.txt", "Fit4Less Gym Membership Details:", doc);
                    saveFit4LessMembershipsToFile("Fit4LessDetails.txt", name, price);
                }
            }          

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // comparePrices method to display and compare gym details
    public static void comparePrices(){
    	System.out.println("Fitness World GYM:");
        System.out.println("\n");
        printFile("FitnessWorldDetails.txt");
        System.out.println("\n");
        System.out.println("----------------------");
        System.out.println("\n");
        System.out.println("Dwell GYM:");
        System.out.println("\n");
        printFile("DwellGymDetails.txt");
        System.out.println("\n");
        System.out.println("----------------------");
        System.out.println("\n");
        System.out.println("Fit4Less GYM:");
        System.out.println("\n");
        printFile("Fit4LessDetails.txt");
        System.out.println("\n");
        System.out.println("=======================");
        printFile("cheapestdeal.txt");
    }
    
    // Method to save general details to a file
    private static void saveDetailsToFile(String fileName, String header, Document doc) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(header);
            writer.newLine();
            writer.write("Gym website: " + doc.location());
            writer.newLine();
            writer.newLine();
        }
    }

    // Method to save Fitness World plans to file
    private static void saveFitnessWorldPlansToFile(String fileName, Elements planElements) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Element planElement : planElements) {
                Element imageOverlay = planElement.select(".image_overlay-text").first();
                String planName = imageOverlay.select("h3").text();
                String planPrice = planName.split(":")[1].trim();

                Elements amenities = planElement.select(".club-option__amenities");
                writer.write("Plan: " + planName);
                writer.newLine();
                writer.write("Price: " + planPrice);
                writer.newLine();
                writer.write("Amenities:");
                writer.newLine();

                for (Element amenity : amenities) {
                    String amenityText = amenity.select("span").text();
                    writer.write("- " + amenityText);
                    writer.newLine();
                }

                writer.newLine(); // Separate plans
            }
        }
    }

    // Method to save Dwell Gym pricing tables to file
    private static void savePricingTablesToFile(String fileName, Elements pricingTables) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Element pricingTable : pricingTables) {
                String title = pricingTable.select(".pricing-table__title").text();
                String price = pricingTable.select(".pricing-table__price").text();

                writer.write("Membership Time: " + title);
                writer.newLine();
                writer.write("Membership Price: " + price);
                writer.newLine();

                // Additional content
                Element contentElement = pricingTable.select(".pricing-table-row__content").first();
                if (contentElement != null) {
                    String content = contentElement.text();
                    writer.write("Additional Content: " + content);
                    writer.newLine();
                }

                // Purchase link
                String purchaseLink = pricingTable.select("a.btn-a").attr("href");
                writer.write("Purchase Link: " + purchaseLink);
                writer.newLine();

                writer.write("-----");
                writer.newLine();
            }
            writer.newLine();
        }
    }

    // Method to save Fit4Less memberships to file
    private static void saveFit4LessMembershipsToFile(String fileName, Elements name, Elements price) throws IOException {
        // Variables to store the cheapest deal information
        String cheapestDealName = "";
        String cheapestDealPrice = "";
        String cheapestDealUrl = "";
        double minPrice = Double.MAX_VALUE;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (int j = 0; j < name.size(); j++) {
                String membershipName = name.get(j).text();
                String membershipPrice = price.get(j).text();
                writer.write("Membership Name: " + membershipName);
                writer.newLine();
                writer.write("Membership Price: " + membershipPrice);
                writer.newLine();
                double numericPrice = extractNumericPrice(membershipPrice);
                if (numericPrice < minPrice) {
                    minPrice = numericPrice;
                    cheapestDealName = membershipName;
                    cheapestDealPrice = membershipPrice;
                    cheapestDealUrl = "https://www.fit4less.ca/membership";
                }

                writer.write("-----");
                writer.newLine();
            }
            writer.newLine();
            try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("cheapestdeal.txt"))) {
            // Print the cheapest deal information
            writer1.write("Cheapest Deal:");
            writer1.newLine();
            writer1.write("Membership Name: " + cheapestDealName);
            writer1.newLine();
            writer1.write("Membership Price: " + cheapestDealPrice);
            writer1.newLine();
            writer1.write("URL: " + cheapestDealUrl);
            writer1.newLine();
            }
        }
    }


    public static void readAndPrintDetails(int userInput) {
        String fileName = "";
        switch (userInput) {
            case 1:
                fileName = "FitnessWorldDetails.txt";
                break;
            case 2:
                fileName = "DwellGymDetails.txt";
                break;
            case 3:
                fileName = "Fit4LessDetails.txt";
                break;
            case 0:
                System.out.println("Exiting Program.");
            	return;
            default:
                System.out.println("Invalid input");
                return;
        }
        printFile(fileName);

    }
    private static void printFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    private static double extractNumericPrice(String price) {
        String numericString = price.replaceAll("[^\\d.]", "");
        try {
            return Double.parseDouble(numericString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}

