package temples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputGrouper {
	
	private Map<String, InputGroup> mapGroups = new HashMap<>();
	
	public InputGrouper(String template, String pattern){
		RepetitiveGrouper repetitiveGrouper = new RepetitiveGrouper();
		Set<String> repetitiveBlocks = repetitiveGrouper.getRepeatBlocks(template);
		List<String> searched = new ArrayList<>();
		Pattern pattern_ = Pattern.compile(pattern);
		Matcher matcher = pattern_.matcher(template);
		int index = 1;
		while(matcher.find()){
			String search = matcher.group();
			if(searched.contains(search)) continue;
			searched.add(search);
			String block = getRepeatBlock(search, repetitiveBlocks);
			InputGroup group = mapGroups.get(block);
			if(group==null){
				List<String> inputs = new ArrayList<>();
				
				block = search.equals(block)? null : block;
				group = InputGroup.getInstance().setInputs(inputs)
						.setRepetitiveBlock(block).setIndex(index);
				mapGroups.put(block, group);
				index++;
			}
			group.getInputs().add(search);
		}
	}
	
	public List<InputGroup> getListGroup(){
		List<InputGroup> list = new ArrayList<>(mapGroups.values());
		Collections.sort(list);
		return list;
	}
	
	public List<InputGroup> getListRepetitiveGroup(){
		List<InputGroup> result = new ArrayList<>();
		for(InputGroup inputGroup : getListGroup()){
			if(inputGroup.isRepetitiveBlock()) result.add(inputGroup);
		}
		Collections.sort(result);
		return result;
	}
	
	public List<InputGroup> getListNonRepetitiveGroup(){
		List<InputGroup> result = new ArrayList<>();
		for(InputGroup inputGroup : getListGroup()){
			if(!inputGroup.isRepetitiveBlock()) result.add(inputGroup);
		}
		Collections.sort(result);
		return result;
	}
	
	private String getRepeatBlock(String search, Set<String> repeatBlocks){
		for(String block : repeatBlocks){
			if(block.contains(search)){
				return block;
			};
		}
		return search;
	}
	
}
