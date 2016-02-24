package dooyit.logic;
import java.util.ArrayList;
import java.util.Random;

public class ColourManager {
	Colour[] availableColours;
	ArrayList<Colour> usedColours;
	Random random;
	
	public ColourManager(){
		random = new Random();
		availableColours = new Colour[11];
		
		availableColours[0] = Colour.BLACK;
		availableColours[1] = Colour.CYAN;
		availableColours[2] = Colour.GREY;
		availableColours[3] = Colour.GREY;
		availableColours[4] = Colour.GREEN;
		availableColours[5] = Colour.MAGENTA;
		availableColours[6] = Colour.PINK;
		availableColours[7] = Colour.RED;
		availableColours[8] = Colour.WHITE;
		availableColours[9] = Colour.YELLOW;
		availableColours[10] = Colour.BLUE;
	}
	
	public Colour pickRandomColour(){
		return availableColours[random.nextInt(availableColours.length)];
	}
	
}
