package assignment4;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class WordLadderSolverTest
{
	// test the computeLadder method in the WordLadderSolver class
	@Test
	public void testComputeLadder()
	{
		// test1: atlas and zebra should have no ladder
		Assignment4Interface wordLadderSolver = new WordLadderSolver("A4words.dat");
		List<String> result = new ArrayList<String>();
		boolean correct = false;
		
		try
		{
			result = wordLadderSolver.computeLadder("atlas", "zebra");
		}
		catch (NoSuchLadderException e)
		{
			correct = true;
		}
		
		if (correct == false)
		{
			System.err.println("Test 1 failed.");
			fail();
		}
		
		// test2: heads and tails should have a ladder
		correct = false;
		
		try
		{
			result = wordLadderSolver.computeLadder("heads", "tails");
			correct = wordLadderSolver.validateResult("heads", "tails", result);
		}
		catch (NoSuchLadderException e)
		{
			System.err.println("Test 2 failed.");
			fail();
		}
		
		if (correct == false)
		{
			System.err.println("Test 2 failed.");
			fail();
		}
	}
}
