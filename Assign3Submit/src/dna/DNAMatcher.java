package dna;

/**
 * Used to find the best sequential match for a DNA strand. 
 * Holds a master DNA strand and accepts candidate 
 * strands, storing the best match that has been tested. 
 * Best matches are determined by the longest common substring. 
 * Longest common substrings are determined by using a recursive 
 * approach to the Rabin-Karp algorithm.
 * @author Main
 *
 */
public class DNAMatcher 
{
	/**
	 * Current best DNA match found.
	 */
	private DNA	bestMatch;
	/**
	 * Master strand of DNA to be matched.
	 */
	private DNA	masterStrand;
	/**
	 * Longest common substring match between master 
	 * strand and current best match DNA.
	 */
	private DNA	substrandMatch;
	/**
	 * Sets base value.
	 */
	private final int BASE = 4;
	
	/**
	 * DNAMatcher constructor.
	 * @param masterStrandIn - Incoming master DNA strand for this DNAMatcher.
	 */
	public DNAMatcher(DNA masterStrandIn)
	{
		masterStrand = masterStrandIn;
	}
	/**
	 * Given a DNA match candidate, checks if the passed DNA 
	 * candidate has a longer common substring than is currently 
	 * stored. Uses the Rabin-Karp algorithm for substring 
	 * matching. Performs this by breaking the candidate strand 
	 * into all possible substrings and sending them to the 
	 * RabinKarp method. This is a bit, “Brute Force” 
	 * (there are more efficient but complex ways of doing this), 
	 * however the work is mitigated by eliminating many possible 
	 * substrings. A substring from the candidate that is longer 
	 * than master strand can be skipped. All substrings whose 
	 * length is shorter than the current best match are also 
	 * skipped. Additionally, substrings of the candidate are 
	 * processed from longest to shortest and stops once a match 
	 * is found and doesn't bother with the shorter substrings.
	 * @param matchCandidate - Candidate for matching master strand.
	 */
	public void checkMatch(DNA matchCandidate)
	{
		DNA temp;
		String[] substrings = getAllPossibleSubstrings(matchCandidate.getStrand());
		substrings = sortLongestToShortest(substrings);
		int hash = -1;
		int k = 0;
		do
		{
			temp = new DNA(substrings[k]);
			hash = rabinKarp(temp);
			k++;
		}
		while (hash == -1 && k < substrings.length);
		if ((substrandMatch == null || temp.getStrandLength() > substrandMatch.getStrandLength()) && hash != -1)
		{
			bestMatch = matchCandidate;
			substrandMatch = temp;
		}
	}
	/**
	 * Method to get all the possible substrings within passed string s.
	 * @param s - passed string to get all substrings for
	 * @return substring array
	 */
	public String[] getAllPossibleSubstrings(String s)
	{
		int subContains = 0;
		for (int i = 1; i <= s.length(); i++)
		{
			subContains += i;
		}
		String[] substrings = new String[subContains];
		int k = 0;
		for (int i = s.length() - 1; i >= 0; i--)
		{
			for (int j = s.length() - i - 1; j >= 0; j--)
			{
				if (i == j + i)
				{
					substrings[k] = s.substring(i);
				}
				else
				{
					substrings[k] = s.substring(i, j + i);
				}
				k++;
			}
		}
		return substrings;
	}
	/**
	 * Method sorts the passed string array from longest to shortest.
	 * @param s - passed string array to be sorted.
	 * @return ordered set of strings
	 */
	public String[] sortLongestToShortest(String[] s)
	{
		int n = s.length;
		String temp;
		for (int i = 0; i < n; i++)
		{
			for (int j = 1; j < (n - i); j++)
			{
				if (s[j - 1].length() < s[j].length())
				{
					temp = s[j - 1];
					s[j - 1] = s[j];
					s[j] = temp;
				}
			}
		}
		return s;
	}
	/**
	 * Current best DNA match found.
	 * @return best match
	 */
	public DNA getBestMatch()
	{
		return bestMatch;
	}
	/**
	 * Returns the DNAMatcher's masterStrand.
	 * @return DNAMatcher's masterStrand.
	 */
	public DNA getMaster()
	{
		return masterStrand;
	}
	/**
	 * Returns the DNAMatcher's substrandMatch.
	 * @return DNAMatcher's substrandMatch.
	 */
	public DNA getSubstrandMatch()
	{
		return substrandMatch;
	}
	/**
	 * Implements Rabin-Karp alogrithm for finding longest common substring. 
	 * Does the preprocessing of finding the hash for the candidate's 
	 * substrand using the stringHash method and the hashes of substrings 
	 * in the master strand String of the same length as the candidate 
	 * substrand using the recursive RabinKarpHashes method. Uses a search 
	 * to determine if the substring hash is in the collection of hashes 
	 * and returns the result.
	 * @param substrandCandidate - DNA substrand to find in the master strand.
	 * @return Location in master strand if candidate substrand is found. If not found returns -1.
	 */
	private int rabinKarp(DNA substrandCandidate)
	{
		int substrandHash = substrandCandidate.dnaHash();
		int strandPos = masterStrand.getStrandLength() - substrandCandidate.getStrandLength() + 1;
		int location = -1;
		if (strandPos > 0)
		{
			int[] masterStrandHashes = new int[strandPos];
		
			rabinKarpHashes(masterStrandHashes, masterStrandHashes.length - 1, 
					substrandCandidate.getStrandLength());
			for (int i = 0; i < masterStrandHashes.length; i++)
			{
				DNA temp = new DNA(masterStrand.getStrand().substring(i, 
						i + substrandCandidate.getStrandLength()));
				masterStrandHashes[i] = temp.dnaHash();
				
				if (substrandHash == masterStrandHashes[i])
				{
					location = i;
					return location;
				}
			}
		}
		return location;
	}
	/**
	 * Finds and stores the hash values of all substrings of passed size 
	 * in the master strand. Starts at the passed position, and works 
	 * backwards through the master strand's String. The hash values are 
	 * stored in the passed array. The position in the array corresponds 
	 * to the position of the substring in the master strand's String. 
	 * THIS METHOD MUST BE RECURSIVE USING THE TECHNIQUE AS DESCRIBED 
	 * IN THE RABIN-KARP LECTURE.
	 * @param hashes - Array of hashes to be populated.
	 * @param pos - Current position in master strand's String to be hashed.
	 * @param length - Length of the substrings in the master strand to be hashed.
	 * @return Returns the hash of the substring in the master strand at the 
	 * given position of the given length. This must be returned so that a general 
	 * case hashing a substring can recursively call upon the method to get the 
	 * hash of the substring that is found before it.
	 */
	private int rabinKarpHashes(int[] hashes, int pos, int length)
	{
		int hashNumber = 0;
		
		if (pos == 0)
		{
			for (int i = 0; i < length; i++)
			{
				int posNumber = DNA.charNumericValue(masterStrand.getStrand().charAt(i));
				hashNumber += posNumber * Math.pow(BASE, length - i - 1);
			}
		}
		else
		{
			int previousCharachter = DNA.charNumericValue(masterStrand.getStrand().charAt(pos - 1));
			int newCharachter = DNA.charNumericValue(masterStrand.getStrand().charAt(pos + length - 1));
			hashes[pos] = (int)  Math.pow(BASE, -1) * (rabinKarpHashes(hashes, pos - 1, length)) 
					- previousCharachter + newCharachter;
			hashNumber = hashes[pos];
		}
		return hashNumber;
	}
	/**
	 * Formats this DNAMatcher into a String. If a match has been found returns a String in the format:
	 * Master: MASTERSTRAND
	 * Best Match: BESTMATCH
	 * Matching Substring: SUBSTRANDMATCH
	 * If a match has not been found returns a String in the format:
	 * Master: MASTERSTRAND
	 * NO MATCH FOUND
	 * @overrides toString in class java.lang.Object
	 * @return Formatted String of this DNAMatcher.
	 */
	public java.lang.String toString()
	{
		String toReturn;
		if (bestMatch != null)
		{
			toReturn = String.format("Master: DNA Strand: %s \nBest Match: DNA Strand: "
					+ "%s \nMatching Substring: DNA Strand: %s", masterStrand, 
					bestMatch, substrandMatch);
		}
		else 
		{
			toReturn = String.format("Master: DNA Strand: %s \nNO MATCH FOUND", masterStrand);
		}
		return toReturn;
	}
}
