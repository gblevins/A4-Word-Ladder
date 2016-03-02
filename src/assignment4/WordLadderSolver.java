/*
    Farhan Ali and Garret Blevins
    TA: Niraj Th: 2-3:30pm
 */

package assignment4;

import java.util.ArrayList;
import java.util.List;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface
{
    // dictionary of all valid 5 letter words
	Dictionary dictionary;

    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	WordLadderSolver(String dictionaryFile)
	{
		dictionary = new Dictionary(dictionaryFile);
	}

    // do not change signature of the method implemented from the interface
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
    	List<String> result = new ArrayList<String>();
    	try
    	{
   			List<String> pastWords = new ArrayList<String>();
    		result.addAll(MakeLadder(startWord, endWord, 0, pastWords));
    	}
    	catch (NoSuchLadderException e)
    	{
    		throw e;
    	}
    	return result;
    }

    private List<String> MakeLadder(String fromWord, String toWord, int positionLastChanged, List<String> pastWords) throws NoSuchLadderException
    {
    	List<String> result = new ArrayList<String>();
    	if (fromWord.equals(toWord))
    	{
    		result.add(toWord);
    		return result;
    	}
    	int letterDif = letterDifference(fromWord, toWord);
    	if (letterDif <= 1)
    	{
    		result.add(fromWord);
    		List<String> temp = MakeLadder(toWord, toWord, positionLastChanged + 1, pastWords);
			result.addAll(temp);
			return result;
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
    					List<String> temp = MakeLadder(newWord.toString(), toWord, positionLastChanged + 1, pastWords);
    					result.add(fromWord);
    					result.addAll(temp);
    				}
    				catch (NoSuchLadderException e)
    				{
    					result.remove(fromWord);
    					continue;
    				}
    				return result;
    			}
    		}
    	}
    	throw new NoSuchLadderException("No ladder found.");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    public boolean isWord(String word)
    {
    	if (dictionary.isMember(word))
    	{
    		return true;
    	}
    	return false;
    }

    // count the number of different letter between to words
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
