package assignment4;

import java.util.List;
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
//    	Dictionary dictionary = new Dictionary();
//    	dictionary.convertFileToDictionary(args[0]);
//    	
//    	ArrayList<String> dict = new ArrayList<String>();	//will store the transaction inputs, line by line
//		processLinesInFile (args[0], dict); 				// putting the transaction inputs in input
//
//        // Create a word ladder solver object
//        Assignment4Interface wordLadderSolver = new WordLadderSolver();
//
//        try 
//        {
//            List<String> result = wordLadderSolver.computeLadder("money", "honey");
//            boolean correct = wordLadderSolver.validateResult("money", "honey", result);
//        } 
//        catch (NoSuchLadderException e) 
//        {
//            e.printStackTrace();
//        }
//    }

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
				try 
		        {
		            List<String> result = wordLadderSolver.computeLadder(words[0], words[1]);
		            boolean correct = wordLadderSolver.validateResult(words[0], words[1], result);
		        } 
		        catch (NoSuchLadderException e) 
		        {
		            e.printStackTrace();
		        }
				//if (correct == true)
				//{
				//	System.out.println(result);
				//}
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
}
