package dna;

//import dna.InvalidDNAStrandException;

/**
 * A DNA strand. Internally represented by a String containing 
 * only valid DNA molecules as follows:
A: Adenine

C: Cytosine

G: Guanine

T: Thymine

Capable of handling a DNA strand of a maximum of 15 molecules.
 * @author Main
 *
 */
public class DNA extends java.lang.Object
{
	/**
	 * Holds the molecule strand characters.
	 */
	private java.lang.String strandString;
	/**
	 * Contains base value.
	 */
	private int BASE = 4;
	
	/**
	 * DNA constructor.
	 * @param strandIn passed strand
	 */
	public DNA(java.lang.String strandIn)
	{
		strandString = strandIn;
	}
	/**
	 * Returns the molecule sequence String.
	 * @return strandString String.
	 */
	public java.lang.String getStrand()
	{
		return strandString;
	}
	/**
	 * Returns the number of molecules in the DNA strand.
	 * @return Length of the DNA strand.
	 */
	public int getStrandLength()
	{
		return strandString.length();
	}
	/**
	 * Sets the strand String for this DNA. Ensures the requested stand 
	 * does not exceed maximum length or contain invalid molecules.
	 * @param strandIn - Desired strand String.
	 * @throws InvalidDNAStrandException - Throws an InvalidDNAStrandException 
	 * if incoming strand is invalid.
	 */
	void setStrand(java.lang.String strandIn) throws InvalidDNAStrandException
	{
		//boolean match = false;
		int match = 0;
		char molecule = 'a';
		if (strandIn.length() > strandString.length())
		{
			throw new InvalidDNAStrandException();
		}
		for (int i = 0; i < strandIn.length(); i++)
		{
			molecule = strandIn.charAt(i);
			if (molecule != 'a' || molecule != 't' || molecule != 'g' || molecule != 'c')
			{
				throw new InvalidDNAStrandException();
			}
			else
			{
				match++;
				//strandString.charAt(i) = strandIn.charAt(i);
			}
		}
		if (match == strandIn.length() - 1)
		{
			strandString = strandIn;
		}
	}
	/**
	 * Returns the hash value of the entire string for this DNA 
	 * object. Since there are only four possible characters in 
	 * the strand, it uses a base 4 number system to create the 
	 * hash as described in the Rabin-Karp lecture. This will be 
	 * needed only to find the hash of the substring that is 
	 * being searched for and the base case for finding all 
	 * substring hashes in the master strand string. This should 
	 * not be used to find ALL substring hashes of the master 
	 * strand.
	 * @return Hash of the string for this DNA object.
	 */
	public int dnaHash()
	{
		int hashNumber = 0;
		for (int i = 0; i < strandString.length(); i++)
		{
			int posNumber = DNA.charNumericValue(strandString.charAt(i));
			hashNumber += posNumber * Math.pow(BASE, strandString.length() - i - 1);
		}
		return hashNumber;
	}
	/**
	 * Given the character of a molecule, returns its numeric mapping.
'A' - 0

'C' - 1

'G' - 2

'T' - 3
	 * @param molecule - Character of molecule.
	 * @return Numeric mapping of passed molecule character.
	 */
	public static int charNumericValue(char molecule)
	{
		int value = -1;
		if (molecule == 'A')
		{
			value = 0;
		}
		if (molecule == 'C')
		{
			value = 1;
		}
		if (molecule == 'G')
		{
			value = 2;
		}
		if (molecule == 'T')
		{
			value = 3;
		}
		return value;
	}
	/**
	 * Generates and returns a new instance of DNA that is a 
	 * substrand of this DNA. The DNA generated starts at the 
	 * given position and is of the given length.
	 * @param pos - Beginning of the desired substrand of DNA.
	 * @param length - Length of the desired substrand of DNA.
	 * @return New DNA that is a substrand of this DNA.
	 */
	public DNA dnaSubstrand(int pos, int length)
	{
		return new DNA(getStrand().substring(pos, length));
	}
	/**
	 * Constructs and returns a String representation of DNA. 
	 * The format returned is "DNA Strand: " followed by the 
	 * molecule sequence String.
	 * @overrides toString in class java.lang.Object
	 * @return String representation of DNA.
	 */
	public java.lang.String toString()
	{
		return strandString;
	}
}
