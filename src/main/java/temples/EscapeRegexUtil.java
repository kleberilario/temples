package temples;

import java.util.regex.Pattern;

public class EscapeRegexUtil {
	
	private static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
	
	public static String escapeSpecialRegexChars(String str) {
	    return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
	}

}
