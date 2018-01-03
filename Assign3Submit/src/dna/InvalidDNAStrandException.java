package dna;

import java.io.Serializable;

/**
 * An unchecked Exception to be thrown when an illegal 
 * instance of DNA is attempted to be created. Can occur 
 * from either a strand String is too long or contains 
 * illegal characters.
 * @author Main
 *
 */
public class InvalidDNAStrandException extends java.lang.Exception implements Serializable 
{
	/**
	 * InvalidDNAStrandException constructor.
	 */
	public InvalidDNAStrandException()
	{
		super("Strand too long");
	}
}
