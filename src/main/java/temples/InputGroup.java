package temples;

import java.util.List;

public class InputGroup implements Comparable<InputGroup>{		
	
	private int index;
	private List<String> inputs;
	private String repetitiveBlock;
	private int repetitives;
	
	private InputGroup(){}
	
	public static InputGroup getInstance(){
		return new InputGroup();
	}
	
	public int getIndex() {
		return index;
	}
	public InputGroup setIndex(int index) {
		this.index = index;
		return this;
	}
	public List<String> getInputs() {
		return inputs;
	}
	public InputGroup setInputs(List<String> inputs) {
		this.inputs = inputs;
		return this;
	}
	public String getRepetitiveBlock() {
		return repetitiveBlock;
	}
	public InputGroup setRepetitiveBlock(String repetitiveBlock) {
		this.repetitiveBlock = repetitiveBlock;
		return this;
	}
	public boolean isRepetitiveBlock(){
		return this.repetitiveBlock != null;
	}
	public int getRepetitives() {
		return repetitives;
	}
	public void setRepetitives(int repetitives) {
		this.repetitives = repetitives;
	}
	
	@Override
	public String toString() {
		return this.index + " [" + this.inputs + "] " + this.isRepetitiveBlock();
	}

	@Override
	public int compareTo(InputGroup other) {
		if(other.getIndex() < this.getIndex()) return 1;
		return -1;
	}
	 
}