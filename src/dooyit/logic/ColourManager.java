package dooyit.logic;
import java.util.ArrayList;
import java.util.Random;

public class ColourManager {
	//Colour[] availableColours;
	ArrayList<Colour> usedColours;
	Random random;
	ArrayList<Colour> availableColours;
	
	
	public ColourManager(){
		random = new Random();
		//availableColours = new Colour[10];
		
		availableColours = new ArrayList<Colour>();
		
		availableColours.add(Colour.BLUE);
		availableColours.add(Colour.CYAN);
		availableColours.add(Colour.GREEN);
		availableColours.add(Colour.MAGENTA);
		availableColours.add(Colour.PINK);
		availableColours.add(Colour.RED);
		availableColours.add(Colour.YELLOW);
		availableColours.add(Colour.GREY);
		availableColours.add(Colour.BLUE);
		


	}
	
	public Colour pickRandomColour(){
		return availableColours.get(random.nextInt(availableColours.size()));
	}
	
}
