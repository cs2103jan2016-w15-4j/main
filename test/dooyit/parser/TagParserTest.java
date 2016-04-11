//@@author A0133338J
package dooyit.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import dooyit.common.exception.IncorrectInputException;

public class TagParserTest {
	TagParser tagParser = new TagParser();
	
	@Test
	public void tagSingleID() {
		String input = "1";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
		ArrayList<Integer> expectedArrayList = new ArrayList<Integer>(Arrays.asList(1));
		assertEquals(expectedArrayList, tagParser.taskIdsForTagging);
	}
	
	@Test
	public void tagMultipleID() {
		String input = "1 3 4 12 25";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
		ArrayList<Integer> expectedArrayList = new ArrayList<Integer>(Arrays.asList(1, 3, 4, 12, 25));
		assertEquals(expectedArrayList, tagParser.taskIdsForTagging);
	}
	
	@Test
	public void tagIntervalID() {
		String input = "1-8";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
		ArrayList<Integer> expectedArrayList = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
		assertEquals(expectedArrayList, tagParser.taskIdsForTagging);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void tagInvalidIntervalID() {
		String input = "a-10";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void tagInvalidMultipleID() {
		String input = "a hu 9 10";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void tagInvalidSingleID() {
		String input = "a";
		tagParser.setAttributesForTagging(input);
		tagParser.parseTaskIds();
	}
	
}
