package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Assign4Driver
{
	// main is expecting 2 arguments
	// dictionary file name first (args[0])
	// word pairs file name second (args[1])
    public static void main(String[] args)
    {
    	if (args.length != 2)
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
    	processLinesInFile(args);
    }

    public static void processLinesInFile (String args[]) 
	{
    	Assignment4Interface wordLadderSolver = new WordLadderSolver(args[0]);
    	
    	try 
		{
			FileReader freader = new FileReader(args[1]);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				String words[] = s.split("\\s+");
				if(checkErrors(words, wordLadderSolver))
				{
					System.err.println("At least one of the words \"" +words[0]+ "\" and \"" +words[1]+"\" are not legitimate 5-letter words from the dictionary.");
					continue;
				}
				
				try
		        {
		            List<String> result = wordLadderSolver.computeLadder(words[0], words[1]);
		            
		            if(wordLadderSolver.validateResult(words[0], words[1], result)){
		            	System.out.println("For the input words \"" +words[0]+ "\" and \"" +words[1]+"\" the following word ladder was found:" );
			            Iterator<String> a = result.iterator();
			            while(a.hasNext())
			            {
			            	System.out.println(a.next());
			            }
			            System.out.println("The number of words in this ladder is: " + result.size());
			            System.out.println("**********");
		            }
		            else
		            	System.err.println("The word ladder between \"" +words[0]+ "\" and \"" +words[1]+"\" is invalid." );
		            
		        } 
		        catch (NoSuchLadderException e) 
		        {
		        	System.err.println(e.getMessage());
		        }
			}
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) 
		{
			System.err.println ("Error: iO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}
    
    // checks if the input was valid word pairs
    public static boolean checkErrors(String[] input, Assignment4Interface wordLadderSolver)
    {
    	if (input.length != 2)	// only want 2 input words, e.g. "money" and "honey"
    		return true; 		// true means there are errors
    	if ((input[0].length() != 5) || (input[1].length() != 5)) //want only 5 letter words
    		return true;
    	if(!((WordLadderSolver) wordLadderSolver).isWord(input[0]) || !((WordLadderSolver) wordLadderSolver).isWord(input[1]))
    		return true;
    	return false; 			// false means no errors
    }
}
