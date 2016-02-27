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
		
		availableColours[0] = Colour.BLUE;
		availableColours[1] = Colour.CYAN;
		availableColours[2] = Colour.GREEN;
		availableColours[3] = Colour.MAGENTA;
		availableColours[4] = Colour.PINK;
		availableColours[5] = Colour.RED;
		availableColours[6] = Colour.YELLOW;
		availableColours[7] = Colour.BLACK;
		availableColours[8] = Colour.GREY;
		availableColours[9] = Colour.WHITE;

	}
	
	public Colour pickRandomColour(){
		return availableColours[random.nextInt(availableColours.length)];
	}
	
}
