package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary
{
	// list of all of the words from the dictionary file
	ArrayList<String> words;

	// put words from an input file into the dictionary
	// only works with 5 letter word dictionaries
	Dictionary(String filename)
	{
		words = new ArrayList<String>();
		this.convertFileToDictionary(filename);
	}
	
	// take a file that should be a dictionary of 5 letter words and put the words in the ArrayList
	private void convertFileToDictionary(String filename)
	{
		try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				if (s.charAt(0) != '*'){
					words.add(s.substring(0,5).toLowerCase());
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
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// check if a word is in the dictionary
	protected boolean isMember(String word)
	{
		if (words.contains(word.toLowerCase()))
		{
			return true;
		}
		return false;
	}
}
