package assignment4;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class WordLadderSolverTest {
	// main is expecting 2 arguments
	// dictionary file name first (args[0])
    public void main(String[] args)
    {
    	if (args.length != 1)
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		wordLadderSolver = new WordLadderSolver(args[0]);
		testComputeLadder();
    }
    
	Assignment4Interface wordLadderSolver;
    
	@Test
	public void testComputeLadder()
	{
		try
		{
			List<String> result = wordLadderSolver.computeLadder("money", "honey");
			Iterator<String> a = result.iterator();
			while(a.hasNext())
			{
				System.out.println(a.next());
			}
			System.out.println("**********");
		} 
		catch (NoSuchLadderException e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
