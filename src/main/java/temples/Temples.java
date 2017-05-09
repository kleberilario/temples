package temples;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Temples {
	
	private static String INPUT_PATTERN = "\\.\\:\\{.*?\\}";
	private static String INPUT_AND_DEFINE_START_PATTERN = "(\\.\\:|\\:\\:)\\{";
	private static String INPUT_AND_DEFINE_END_PATTERN = "}";
	private static String REPEAT_START_PATTERN = "\\?\\{";
	private static String REPEAT_END_PATTERN = "\\}\\?";
	private static String INPUT_START_TEXT = ".:{";
	private static String DEFINE_START_TEXT = "::{";

	public void build(InputStream stream){
		String template = new String();
		try(Scanner scan = new Scanner(stream).useDelimiter("\\A")){
			template = scan.hasNext() ? scan.next() : "";
			build(template);
		}
	}
	
	public void build(String template){
		template = read(template);
		print(System.out, template);
	}
	
	
	private String read(String template){
		Map<String,Map<Integer, String>> settedValues = new HashMap<>();
		InputGrouper grouper = new InputGrouper(template, INPUT_PATTERN);
		template = readInputs(template, settedValues, grouper);
		template = writeRepetitivesBlock(template, grouper,settedValues) ;
		template = writeValues(template, grouper, settedValues);
		return template;
		
	}
	
	private String readInputs(String template, Map<String,Map<Integer, String>> settedValues, InputGrouper grouper) {
		Scanner in = new Scanner(System.in);
		List<InputGroup> groups = grouper.getListGroup();
		for(InputGroup group : groups){
			int inputId = 1;
			REPEAT: do{
				for(String input : group.getInputs()){
					String value = readValue(input,in);
					if(value.equals("!")) break REPEAT;
					String key = input;
					putInputValue(settedValues, key, inputId, value);
					group.setRepetitives(inputId);
				}
				inputId++;
			}
			while(group.isRepetitiveBlock());
		}
		return template;
	}
	
	private String writeRepetitivesBlock(String template, InputGrouper grouper, Map<String,Map<Integer, String>> settedValues){
		RepetitiveGrouper repetitiveGrouper = new RepetitiveGrouper();
		Set<String> repetitives = repetitiveGrouper.getRepeatBlocks(template);
		for(String repetitive : repetitives){
			for(InputGroup group : grouper.getListGroup()){
				String cleanInput = cleanText(group.getInputs().get(0));
				if(repetitive.contains(cleanInput)){
					int repeat = 1;
					int repeats = group.getRepetitives();
					while(repeat < repeats){
						int offset = template.indexOf(repetitive) + repetitive.length();
						template = insertRepeatBlock(template, repetitive, offset);
						repeat++;
					}
				}
			}
		}
		return template;
	}
	
	private String writeValues(String template, InputGrouper grouper, Map<String,Map<Integer, String>> settedValues){
		template = replace(template, DEFINE_START_TEXT, INPUT_START_TEXT);
		
		RepetitiveGrouper repetitiveGrouper = new RepetitiveGrouper();
		Set<String> repetitives = repetitiveGrouper.getRepeatBlocks(template);

		for(String repetitive : repetitives){
			for(InputGroup group : grouper.getListRepetitiveGroup()){
				String cleanInput = cleanText(group.getInputs().get(0));
				if(repetitive.contains(cleanInput)){
					int repeat = 1;
					int repeats = group.getRepetitives();
					while(repeat <= repeats){
						String newRepetitive = new String(repetitive);
						for(String input : group.getInputs()){
							String value = getInputValue(settedValues, input, repeat);
							newRepetitive = replace(newRepetitive, input, value);
						}
						template = replaceFirst(template, repetitive, newRepetitive);
						repeat++;
					}
				}
			}
		}
		
		
		for(InputGroup group : grouper.getListNonRepetitiveGroup()){
			for(String input : group.getInputs()){
				String value = getInputValue(settedValues, input, 1);
				template = replace(template, input,value);
			}
		}

		template = cleanText(template,REPEAT_START_PATTERN,REPEAT_END_PATTERN);
		return template;
	}
	
	private String insertRepeatBlock(String template, String block, int offset){
		StringBuilder builder = new StringBuilder(template);
		builder.insert(offset, "\n" + block);
		return builder.toString();
	}
	
	private String replace(String text, String search, String value){
		return text.replace(search, value);
	}
	
	private String replaceFirst(String text, String search, String value){
		search = EscapeRegexUtil.escapeSpecialRegexChars(search);
		return text.replaceFirst(search, value);
	}
	
	private String readValue(String search, Scanner in){
		String desc = cleanText(search);
		System.out.print(desc + ".:");
		String value = in.nextLine();
		return value;
	}
	
	private String cleanText(String search){
		return cleanText(search, INPUT_AND_DEFINE_START_PATTERN, INPUT_AND_DEFINE_END_PATTERN);
	}
	
	private String cleanText(String search, String... regexs){
		for(String regex : regexs) search = search.replaceAll(regex,"");
		return search;
	}
	
	private void print(PrintStream stream, String templateResult){
		stream.println(templateResult);
	}
	
	private void putInputValue(Map<String,Map<Integer, String>> settedValues, String key, Integer id, String value){
		Map<Integer,String> row  = settedValues.get(key);
		if(row==null) row = new HashMap<>();
		row.put(id, value);
		settedValues.put(key, row);
		
	}
	
	private String getInputValue(Map<String,Map<Integer, String>> settedValues, String key, Integer id){
		Map<Integer,String> row  = settedValues.get(key);
		return row.get(id);
	}
}
