package temples;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepetitiveGrouper {

	private static String REPEAT_PATTERN = "(\t)\\?\\{.*?\\}\\?";
	
	public Set<String> getRepeatBlocks(String template){
		Set<String> result = new LinkedHashSet<>();
		Pattern pattern = Pattern.compile(REPEAT_PATTERN, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(template);
		while(matcher.find()){
			result.add(matcher.group());
		}
		return result;
	}
	
}
