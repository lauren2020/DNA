package dna;
import java.util.Scanner;

/**
 * Driver class for utilizing a DNA matcher.
 * Retrieves Master DNA strand from user and as many match candidates as user wants to test.
 * @author Mr. Cavanaugh
 *
 */
public class DNADriver
{
	/**
	 * Scanner used for user input.
	 */
	private static Scanner in = new Scanner(System.in);
	/**
	 * Variable that contains current position.
	 */
	private static char pos = 0;
	/**
	 * variable that holds entered DNA.
	 */
	private static DNA newDNA = null;
	
	/**
	 * Constructor.
	 */
	public DNADriver()
	{
		
	}
	//You must write the inputDNAString method. See DNADriver documentation for details.
	//DO NOT make changes to main.
	/**
	 * Prompts user to enter a strand String for a new instance of DNA. 
	 * Attempts to create DNA based upon user's input. If failed due to 
	 * an InvalidDNAStrandException, reports the Exception's message and 
	 * prompts the user to enter a new strand String (see example output) 
	 * until a valid strand String is entered or "EXIT" is entered.
	 * @return instance of DNA created from entered strand String, 
	 * or null if "EXIT" entered.
	 */
	private static DNA inputDNAString() //throws InvalidDNAStrandException
	{
		System.out.print("Molecule Sring DNA Strand: ");
		String userInput = in.next().substring(0);
		//userInput = userInput.toUpperCase();
		if (userInput.length() > 15)
		{
			System.out.println("Strand too long, try again.");
			inputDNAString();
		}
		else if (userInput.equals("EXIT"))
		{
			newDNA = null;
		}
		else
		{
			try 
			{
				checkValid(userInput);
				newDNA = new DNA(userInput);
			} 
			catch (InvalidDNAStrandException e)
			{
				System.out.println("Invalid molecule: " + pos + ", try again.");
				inputDNAString();
			}
		}
		return newDNA;
	}
	
	/**
	 * Checks if passed string is valid.
	 * @param s string to be checked
	 * @throws InvalidDNAStrandException e
	 */
	public static void checkValid(String s) throws InvalidDNAStrandException
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) != 'A' && s.charAt(i) != 'G' && s.charAt(i) != 'T' && s.charAt(i) != 'C')
			{
				pos = s.charAt(i);
				throw new InvalidDNAStrandException();
			}
		}
	}
	
	/**
	 * Entry point of execution. Runs DNA matching application.
	 * @param args Not used.
	 */
	public static void main(String[] args)
	{	
		//Create DNA and DNAMatcher references
		DNA master;
		DNA candidate;
		DNAMatcher matcher;
		
		System.out.println("***Enter Master DNA***");
		//Get DNA for the master strand
		//Master DNA cannot be null, so continue until valid DNA is entered
		do
		{
			master = inputDNAString();
		}
		while (master == null);

		//Create the DNAMatcher with master DNA
		matcher = new DNAMatcher(master);
      
		System.out.println("***Enter DNA match candidates, EXIT to quit***");
		//Accept candidate DNA until user quits
		//inputDNAString will return null when "EXIT" is entered, so continue while null is not returned
		while ((candidate = inputDNAString()) != null)
		{
			matcher.checkMatch(candidate);
		}

		//Output results!
		System.out.println("\n" + matcher);
	}
}
