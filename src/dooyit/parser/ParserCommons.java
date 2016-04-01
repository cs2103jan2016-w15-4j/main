package dooyit.parser;

public interface ParserCommons {
	public static final int UNINITIALIZED_INT = -1;
	public static final String UNINITIALIZED_STRING = "-1";
	public static final String EMPTY_STRING = "";
	
	default boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}
	
	default boolean isUninitialized(int number) {
		return number == UNINITIALIZED_INT;
	}
	
	default boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
	
}
