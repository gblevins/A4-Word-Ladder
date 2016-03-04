/*
    Farhan Ali and Garret Blevins
    TA: Niraj Th: 2-3:30pm
 */

package assignment4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WordLadderSolver implements Assignment4Interface
{
    // dictionary of all valid 5 letter words
	Dictionary dictionary;

    // constructor, builds the dictionary from the input dictionary file
	WordLadderSolver(String dictionaryFile)
	{
		dictionary = new Dictionary(dictionaryFile);
	}

    // build ladder between two words or throw that there is no ladder possible
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
    	List<String> result = new ArrayList<String>();
    	
    	try
    	{
    		List<String> pastWords = new ArrayList<String>();
    		
    		pastWords.add(startWord);
    		result = MakeLadder(startWord, endWord, 0, pastWords);
    		// removed here is our first attempt at MakeLadder
    		//MakeLadder(startWord, endWord, -1, result, pastWords);
    		// -1 because we haven't changed any letters,
    		// so, we want to start changing from index 0 (first letter)
    	}
    	catch (NoSuchLadderException e)
    	{
    		throw new NoSuchLadderException("No ladder found.");
    	}
    	
    	// if the code reaches here then a ladder was found so add the first word to the top of the ladder
    	result.add(0, startWord);
    	// this next part searches for unnecessary pegs on the ladder (steps that can be skipped)
//    	if(result.size() > 2)
//    	{
//			for (int i = 2; i < result.size(); i++)
//			{
//	    		if (letterDifference(result.get(i), result.get(i - 2)) == 1)
//	    			result.remove(i - 1);
//			}
//		}
    	return result;
    }
    
    private List<String> MakeLadder(String fromWord, String toWord, int positionLastChanged, List<String> pastWords) throws NoSuchLadderException
    {
    	List<String> result = new ArrayList<String>();

    	int letterDif = letterDifference(fromWord, toWord);
    	if (letterDif <= 1)
    	{
    		result.add(toWord);
			return result;
    	}
    	
    	String currentBestGuess = new String();
		int i;
		int lastPositionChanged = 0;
    	
    	while (true)
    	{
	    	for (i = 0; i < 5; i++)
	    	{
	    		if (i == positionLastChanged)
	    			continue;
	    		
	    		StringBuilder newWord = new StringBuilder(fromWord);
	    		
	    		for (int k = 0; k < 25; k++)
	    		{
	    			newWord.setCharAt(i, (char) ('a'+k));
	
	    			if (dictionary.isMember(newWord.toString()) && !pastWords.contains(newWord.toString()))
	    			{
	    				if (currentBestGuess.isEmpty() || (letterDifference(currentBestGuess, toWord) > letterDifference(newWord.toString(), toWord)))
	    				{
	    					currentBestGuess = newWord.toString();
	    					lastPositionChanged = i;
	    					if (letterDifference(currentBestGuess, toWord) == 0)
	    					{
	    						result.add(toWord);
	    						return result;
	    					}
	    				}
	    			}
	    		}
	    	}
	    	if (currentBestGuess.isEmpty())
	    	{
	    		throw new NoSuchLadderException("No ladder found.");
	    	}
	    	pastWords.add(currentBestGuess);
	    	result.add(currentBestGuess);
			try
			{
				result.addAll(MakeLadder(currentBestGuess, toWord, lastPositionChanged, pastWords));
			}
			catch (NoSuchLadderException e)
			{
				result.remove(currentBestGuess);
				currentBestGuess = "";
				continue;
			}
			return result;
    	}
    }

    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        if (wordLadder.get(0) != startWord && wordLadder.get(wordLadder.size()-1) != endWord)
		{
			return false;
		}
        
        Iterator<String> validation = wordLadder.iterator();
        String prevWord = validation.next();
        while (validation.hasNext()){
        	String word = validation.next();
        	if (letterDifference(prevWord, word) != 1)
        		return false;
        	prevWord = word;
        }
        return true;
    }

    // check if a word is in the dictionary, for use of classes other than this one
    public boolean isWord(String word)
    {
    	if (dictionary.isMember(word))
    	{
    		return true;
    	}
    	return false;
    }

    // count the number of different letters between to words
    private int letterDifference(String word1, String word2)
    {
    	int result = 0;
    	char[] chars1 = word1.toCharArray();
    	char[] chars2 = word2.toCharArray();

    	for (int i = 0; i <chars1.length; i++)
    	{
    		if (chars1[i] != chars2[i])
    		{
    			result++;
    		}
    	}
    	return result;
    }
}

// this is our first attempt at MakeLadder
/*  private void MakeLadder(String fromWord, String toWord, int positionLastChanged, List<String> result, List<String> pastWords) throws NoSuchLadderException
{
	//base case 1, if fromWord is equal to toWord
	if (fromWord.equals(toWord))
	{
		result.add(toWord);
		return;
	}
	// base case 2: The following base case checks if the current word is only one letter away from the toWord
	//	if so then all we need to do is change that one letter and we get toWord, so we just add the fromWord to the ladder
	//	and then add toWord (e.g. honey -> money all we need to do is change the h to m, so we can just add both to
	//  the result and finish
	int letterDif = letterDifference(fromWord, toWord);
	if (letterDif <= 1)
	{
		result.add(fromWord);
		result.add(toWord);
		return;
	}
	for (int i = 0; i < 5; i++)
	{
		if (i == positionLastChanged){
			continue;
		}
		StringBuilder newWord = new StringBuilder(fromWord);
		for (int k = 0; k < 25; k++)
		{
			newWord.setCharAt(i, (char) ('a'+k));

			if (dictionary.isMember(newWord.toString()) && !pastWords.contains(newWord.toString()))

			{
				pastWords.add(newWord.toString());
				try
				{
					result.add(fromWord);
					MakeLadder(newWord.toString(), toWord, i, result, pastWords);
				}
				catch (NoSuchLadderException e)
				{
					result.remove(fromWord);
					continue;
				}
				return;
			}
		}
	}
	throw new NoSuchLadderException("There is no word ladder between the words \"" +pastWords.get(0)+ "\" and \"" +toWord+"\".");
}*/
