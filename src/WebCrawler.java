import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Uses Jsoup to crawl through web and creates text files with parsed information.
public class WebCrawler {
	
    // Crawls the given link, extracts content, and creates a text file
    public static String crawl(String link) {

        String html = urlToHTML(link);
        Document doc = Jsoup.parse(html);
        String text = doc.text();
        String title = doc.title();
        createFile(title, text, link);

        Elements e = doc.select("a");
        StringBuilder links = new StringBuilder();

        for (Element e2 : e) {
            String href = e2.attr("abs:href");
            if (href.length() > 3) {
                links.append("\n").append(href);
            }
        }
        return links.toString();
    }

    // Creates a text file with the title, text, and link
    public static void createFile(String title, String text, String link) {
        StringBuilder newTitle = new StringBuilder();
        try {
            String[] titleSplit = title.split("\\|");
            for (String s : titleSplit) {
                newTitle.append(" ").append(s);
            }
            File f = new File("WebPages//" + newTitle.toString()
                    .replaceAll("[\\\\/:*?\"<>|]", "").trim() + ".txt");
            f.createNewFile();
            PrintWriter pw = new PrintWriter(f);
            pw.println(link);
            pw.println(text);
            pw.close();

        } catch (IOException e) {
            System.err.println("Error creating file: " + newTitle.toString().trim() + ".txt");
        }

    }

    // Converts URL content to HTML
    public static String urlToHTML(String link) {
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(" ").append(sc.next());
            }

            String result = sb.toString();
            sc.close();
            return result;
        } catch (IOException e) {
            System.out.println(e);
        }
        return link;
    }

    // Crawls through a list of links and creates text files
    public static void crawlPages(String links) {

        try {
            File f = new File("CrawledPages.txt");
            f.createNewFile();
            FileWriter fwt = new FileWriter(f);
            fwt.close();

            StringBuilder links2 = new StringBuilder();
            for (String link : links.split("\n")) {
                links2.append(crawl(link));
                System.out.println(link);
                FileWriter fw = new FileWriter(f, true);
                fw.write(link + "\n");
                fw.close();
            }

            StringBuilder links3 = new StringBuilder();
            for (String link : links2.toString().split("\n")) {
                In in = new In(f);
                String linksRead = in.readAll();
                if (!linksRead.contains(link)) {
                    links3.append(crawl(link));
                    System.out.println(link);
                    FileWriter fw = new FileWriter(f, true);
                    fw.write(link + "\n");
                    fw.close();
                }
            }

            for (String link : links3.toString().split("\n")) {
                In in = new In(f);
                String linksRead = in.readAll();
                if (!linksRead.contains(link)) {
                    crawl(link);
                    System.out.println(link);
                    FileWriter fw = new FileWriter(f, true);
                    fw.write(link + "\n");
                    fw.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Crawls default web pages
    public static void crawlDefault() {
    	 String links = "https://www.fit4less.ca/media/windsor-central?&source=&utm_source=google&utm_medium=paid-search&utm_campaign=&utm_term=fit4less&adpos=&gad_source=1&gclid=CjwKCAiAmZGrBhAnEiwAo9qHiQBxAcE7S5oe-SlCOgPEEzWbPiPwjHdNC0wVznt7uA2ivn4X7d6-pRoCv6MQAvD_BwE&gclsrc=aw.ds"+"\n"+"https://www.anytimefitness.com/"+"\n"+"https://www.fitnessworld.ca/"+"\n"+"https://dwellgym.ca/";
        crawlPages(links);
    }

    
    
    // Crawls custom user-provided web pages
    public static void crawlCustom(String line) {
        Scanner scanner = new Scanner(System.in);
        String[] links = line.split(" ");
        StringBuilder newLine = new StringBuilder();

        for (String link : links) {
            while (!isValidURL(link) || !isAccessibleURL(link)) {
                if (!isValidURL(link)) {
                    System.out.println("Invalid URL format. Please enter a valid URL:");
                } else if (!isAccessibleURL(link)) {
                    System.out.println("Unable to access the URL. Please enter a different URL:");
                }
                link = scanner.nextLine();
            }
            newLine.append(link).append("\n");
        }
        crawlPages(newLine.toString());
        scanner.close();
    }
    
    // Checks if the provided URL is valid
    public static boolean isValidURL(String url) {
        // Regular expression for a simple URL validation
        String urlRegex = "^(https?|ftp)://\\S+";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    // Checks if the provided URL is accessible
    public static boolean isAccessibleURL(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            // Set User-Agent header to mimic a browser
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    // Wipes out the content of the "WebPages" directory
    public static void wipeWebPages() {
        File directory = new File("WebPages");
        File[] files = directory.listFiles();
        assert files != null;

        for (File f : files) {
            if (!f.delete()) {
                System.out.println("Unable to delete file: " + f.getName());
            }
        }
        System.out.println("WebPages wiped!");
    }

    // Main method for testing default web crawling
    public static void main(String[] args) {
        crawlDefault();
    }
}
