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
	
	default boolean isLeapYear(int year) {
		int[] listOfLeapYears = new int[] { 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096 };

		boolean ans = false;
		for (int i = 0; i < listOfLeapYears.length; i++) {
			if (year < listOfLeapYears[i]) {
				break;
			}
			if (year == listOfLeapYears[i]) {
				ans = true;
				break;
			}
		}
		return ans;
	}
	
}
