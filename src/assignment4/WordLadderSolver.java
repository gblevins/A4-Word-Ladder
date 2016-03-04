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
    	}
    	catch (NoSuchLadderException e)
    	{
    		throw new NoSuchLadderException("There is no word ladder between the words \"" +startWord+ "\" and \"" +endWord+"\"!");
    	}
    	
    	// if the code reaches here then a ladder was found so add the first word to the top of the ladder
    	result.add(0, startWord);
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
	    		throw new NoSuchLadderException("There is no word ladder between the words \"" +pastWords.get(0)+ "\" and \"" +toWord+"\"!");
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

    // ensure that that a ladder is full of words that are one letter apart and in the dictionary and 
    // start and end with the correct word
    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        if (wordLadder.get(0) != startWord || wordLadder.get(wordLadder.size()-1) != endWord)
		{
			return false;
		}
        
        // this next case is for two of the same words as startWord and endWord :
        // 1. make sure the word ladder is only 2 elements long
        // 2. the first and second elements should be equal and they each should equal both elements in the word ladder
        // 3. startWord and endWord should be equal in this case
        if(wordLadder.size() == 2 && wordLadder.get(1).equals(startWord) && wordLadder.get(0).equals(endWord) && startWord.equals(endWord)){
        	if(!dictionary.isMember(wordLadder.get(0)))
        		return false;
        	return true;
        }
        
        Iterator<String> validation = wordLadder.iterator();
        String prevWord = validation.next();
        
        if(!dictionary.isMember(prevWord))
        	return false;
        
        while (validation.hasNext())
        {
        	String word = validation.next();
        	
        	if(!dictionary.isMember(word))
        		return false;
        	
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
