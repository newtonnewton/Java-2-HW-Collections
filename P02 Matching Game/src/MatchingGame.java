import java.io.File;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

public class MatchingGame {
	// Congratulations message
	private final static String CONGRA_MSG = "CONGRATULATIONS! YOU WON!";
	// Cards not matched message
	private final static String NOT_MATCHED = "CARDS NOT MATCHED. Try again!";
	// Cards matched message
	private final static String MATCHED = "CARDS MATCHED! Good Job!";
	// 2D-array which stores cards coordinates on the window display
	private final static float[][] CARDS_COORDINATES = new float[][] { { 170, 170 }, { 324, 170 }, { 478, 170 },
			{ 632, 170 }, { 170, 324 }, { 324, 324 }, { 478, 324 }, { 632, 324 }, { 170, 478 }, { 324, 478 },
			{ 478, 478 }, { 632, 478 } };
	// Array that stores the card images filenames
	private final static String[] CARD_IMAGES_NAMES = new String[] { "apple.png", "ball.png", "peach.png",
			"redFlower.png", "shark.png", "yellowFlower.png" };

	private static PApplet processing; // PApplet object that represents
	// the graphic display window
	private static Card[] cards; // one dimensional array of cards
	private static PImage[] images; // array of images of the different cards
	private static Random randGen; // generator of random numbers
	private static Card selectedCard1; // First selected card
	private static Card selectedCard2; // Second selected card
	private static boolean winner; // boolean evaluated true if the game is won,
	// and false otherwise
	private static int matchedCardsCount; // number of cards matched so far
	// in one session of the game
	private static String message; // Displayed message to the display window
	private static boolean on1;

	/**
	 * Defines the initial environment properties of this game as the program starts
	 */
	public static void setup(PApplet processing) {

		MatchingGame.processing = processing;

		// Set the color used for the background of the Processing window
		processing.background(245, 255, 250); // Mint cream color

		
		images =  new PImage[CARD_IMAGES_NAMES.length];

		for (int i = 0; i < CARD_IMAGES_NAMES.length; i++) {
			images[i] = processing.loadImage("images" + File.separator + CARD_IMAGES_NAMES[i]);
		}
		initGame();

	}

	/**
	 * Initializes the Game
	 */
	public static void initGame() {
		// Initializing static fields
		randGen = new Random(Utility.getSeed());
		selectedCard1 = null;
		selectedCard2 = null;
		matchedCardsCount = 0;
		winner = false;
		message = "";
//		
		// Create cards array.
		Card[] card = new Card[CARD_IMAGES_NAMES.length * 2];
		cards = card;

		int[] Randomsequence = geneRan(CARD_IMAGES_NAMES.length * 2);

		for (int i = 0; i < CARD_IMAGES_NAMES.length; i++) {
			int l = Randomsequence[2 * i];
			int k = Randomsequence[2 * i + 1];

			cards[l] = new Card(images[i], CARDS_COORDINATES[l][0], CARDS_COORDINATES[l][1]);
			cards[k] = new Card(images[i], CARDS_COORDINATES[k][0], CARDS_COORDINATES[k][1]);
		}

		for (int s = 0; s < CARD_IMAGES_NAMES.length * 2; s++) {
			cards[s].setVisible(false);
			cards[s].draw();
		}

	}

	/**
	 * Callback method called each time the user presses a key
	 */
	public static void keyPressed() {
		if (processing.key == 'N' || processing.key == 'n')
			initGame();
	}

	/**
	 * Callback method draws continuously this application window display
	 */
	public static void draw() {
		// Set the color used for the background of the Processing window
		processing.background(245, 255, 250); // Mint cream color

		// Create cards array.
//		cards = new Card[CARD_IMAGES_NAMES.length * 2];

//			    int[] Randomsequence = geneRan(CARD_IMAGES_NAMES.length*2);

//			    for (int i=0; i<CARD_IMAGES_NAMES.length; i++) {
//			    	int l = Randomsequence [2*i];
//			    	int k = Randomsequence [2*i+1];
//			    	
//			        cards[l] = new Card(images[i], CARDS_COORDINATES[l][0], CARDS_COORDINATES[l][1]);
//			    	cards[k] = new Card(images[i], CARDS_COORDINATES[k][0], CARDS_COORDINATES[k][1]);
//			    	}

		for (int s = 0; s < CARD_IMAGES_NAMES.length * 2; s++) {
//			    cards[s].setVisible(false);
			cards[s].draw();
		}
		displayMessage(message);

	}

	/**
	 * Displays a given message to the f window
	 * 
	 * @param message to be displayed to the display window
	 */
	public static void displayMessage(String message) {
		processing.fill(0);
		processing.textSize(20);
		processing.text(message, processing.width / 2, 50);
		processing.textSize(12);
	}

	/**
	 * Checks whether two cards match or not
	 * 
	 * @param card1 reference to the first card
	 * @param card2 reference to the second card
	 * @return true if card1 and card2 image references are the same, false
	 *         otherwise
	 */
	public static boolean matchingCards(Card card1, Card card2) {

		boolean test;

		processing.core.PImage im1 = card1.getImage();
		processing.core.PImage im2 = card2.getImage();

		if (im1.equals(im2)) {
			test = true;
		} else {
			test = false;
		}

		return test;
	}

	/**
	 * Checks whether the mouse is over a given Card
	 * 
	 * @return true if the mouse is over the storage list, false otherwise
	 */
	public static boolean isMouseOver(Card card) {
		boolean inside;

		int x = processing.mouseX;
		int y = processing.mouseY;

		int Xcoord = (int) card.getX();
		int Ycoord = (int) card.getY();

		int height = card.getHeight();
		int width = card.getWidth();

		if (x > Xcoord - width / 2 && x < Xcoord + width / 2 && y > Ycoord - height / 2 && y < Ycoord + height / 2) {
			inside = true;
		}

		else {
			inside = false;
		}

//		System.out.println("I clicked at ("+x +","+y+")");

		return inside;

	}

	/**
	 * Callback method called each time the user presses the mouse
	 */
	public static void mousePressed() {

		boolean on2 = false;

		if (selectedCard1 == null && selectedCard2 == null) {
			int k = 0;
			System.out.println("Starting to pick pair.");
			aa: for (int i = 0; i < CARD_IMAGES_NAMES.length * 2; i++) {

				if (isMouseOver(cards[i])) {
					on1 = cards[i].isVisible();
//			    System.out.println("on is "+ on);

					cards[i].setVisible(true);
					cards[i].select();
					cards[i].draw();

					break aa;
				}
				k++;
				// System.out.println("k: "+k);
			}
			selectedCard1 = cards[k];
			System.out.println("Stored: " + k);
		}
		// if the first card has been flipped, go flip the next one and set both tracker
		// be null after comparison
		else {
			int s = 0;

			aa: for (int j = 0; j < CARD_IMAGES_NAMES.length * 2; j++) {

				if (isMouseOver(cards[j])) {
					on2 = cards[j].isVisible();
//					cards[j].setVisible(true);
					cards[j].select();
					cards[j].draw();

					break aa;
				}
				s++;
			}
			selectedCard2 = cards[s];
			// Compare the image of two clicked-on cards to see if they need to be fliped
			// back

			if (selectedCard1.getImage().equals(selectedCard2.getImage()) && !(selectedCard1 == selectedCard2)) {
				selectedCard1.deselect();
				selectedCard1.draw();

				selectedCard2.setVisible(true);
				selectedCard2.deselect();
				selectedCard2.draw();

				matchedCardsCount += 2;

				message = MATCHED;
			} else {
				if (selectedCard1 == selectedCard2) {
					selectedCard1.setVisible(on1);
					selectedCard1.deselect();
					selectedCard1.draw();
//	        	System.out.println("I AM executed");
//	        	 System.out.println("on1 is "+ on1);

					message = NOT_MATCHED;
				} else {
//		        	
					selectedCard1.setVisible(on1);
					selectedCard1.deselect();
					selectedCard1.draw();
//		        	System.out.println("I am executed");
//		        	 System.out.println("on1 is "+ on1);

					selectedCard2.setVisible(on2);
					selectedCard2.deselect();
					selectedCard2.draw();

					message = NOT_MATCHED;
				}
			}

//		        }
			selectedCard1 = null;
			selectedCard2 = null;
		}
//
		if (matchedCardsCount == CARD_IMAGES_NAMES.length * 2) {
			message = CONGRA_MSG;
		}
	}

	public static int[] geneRan(int Gridlength) {
// create an array of random integer ranging from 0 to Gridlength - 1

		randGen = new Random(Utility.getSeed());

		int[] A = new int[Gridlength];
		int[] B = new int[Gridlength];

		int j = 0;

		for (int i = Gridlength; i > 0; i--) {
			A[j] = randGen.nextInt(i) + 1;
			j++;
		}

		B[0] = A[0] % Gridlength;

		for (int k = 1; k < Gridlength; k++) {

			int count = 0;
			int move = 0;

			for (int i = 1; move - count < A[k]; i++) {

				for (int s = 0; s < k - 1; s++) {

					if ((B[k - 1] + i) % Gridlength == B[s])
						count++;
				}
				move++;
			}

			B[k] = (B[k - 1] + move) % Gridlength;
		}

		return B;
	}

	public static void main(String[] args) {
		Utility.runApplication(); // starts the application
	}

}