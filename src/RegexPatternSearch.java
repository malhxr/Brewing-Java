import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternSearch {

	// Method to search for a pattern in a file
	public static List<String> lookPatinFile(String fp, String ptr) {
		List<String> similar = new ArrayList<>();

		// Read the file line by line
		try (BufferedReader r1 = new BufferedReader(new FileReader(fp))) {
			String l1;
			while ((l1 = r1.readLine()) != null) {
				// Match the pattern in each line
				Matcher mth1 = Pattern.compile(ptr).matcher(l1);

				while (mth1.find()) {
					// Add the matched pattern to the list
					similar.add(mth1.group());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return similar;
	}

	// Method to search for a pattern in all files in a folder
	public static Map<String, List<String>> lookPatinFolder(String fp, String ptr) {
		Map<String, List<String>> similarfile = new HashMap<>();

		File fd = new File(fp);
		File[] fls1 = fd.listFiles();

		if (fls1 != null) {
			for (File fl1 : fls1) {
				if (fl1.isFile()) {
					// Search for the pattern in each file
					List<String> mtch = lookPatinFile(fl1.getAbsolutePath(), ptr);
					if (!mtch.isEmpty()) {
						// Add the file and the matched patterns to the map
						similarfile.put(fl1.getName(), mtch);
					}
				}
			}
		}

		return similarfile;
	}

	// Method to get the regex pattern for a phone number
	public static String phoneNumberPattern() {
		return "\\b\\d{3}[-.\\s]?\\d{3}[-.\\s]?\\d{4}\\b";
	}

	// Method to get the regex pattern for an email
	public static String emailPattern() {
		return "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
	}

	// Method to get the regex pattern for a URL
	public static String urlPattern() {
		return "\\bhttps?://\\S+\\b";
	}

	// Method to get the regex pattern for a date
	public static String datePattern() {
		return "\\b\\d{1,2}/\\d{1,2}/\\d{2,4}\\b";
	}

	// Method to get the regex pattern for an IP address
	public static String ipPattern() {
		return "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
	}
}
