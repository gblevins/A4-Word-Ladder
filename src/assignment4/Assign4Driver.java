package assignment4;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Assign4Driver
{
    public static void main(String[] args)
    {
    	if (args.length != 1) //checking if any file name was passed through, if it wasn't print error and end
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			
			System.exit(-1);
		}
    	ArrayList<String> dict = new ArrayList<String>();	//will store the transaction inputs, line by line
		processLinesInFile (args[0], dict); 				// putting the transaction inputs in input

        // Create a word ladder solver object
        Assignment4Interface wordLadderSolver = new WordLadderSolver();

        try 
        {
            List<String> result = wordLadderSolver.computeLadder("money", "honey");
            boolean correct = wordLadderSolver.validateResult("money", "honey", result);
        } 
        catch (NoSuchLadderException e) 
        {
            e.printStackTrace();
        }
    }
    
    public static void processLinesInFile (String filename, ArrayList<String> input) 
	{ 

		try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				if (s.charAt(0) != '*'){
					input.add(s.substring(0,5));
				}
				
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) 
		{
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}

    
}
