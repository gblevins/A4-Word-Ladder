/*
    Farhan Ali and Garret Blevins
    TA: Niraj Th: 2-3:30pm
 */

package assignment4;

import java.util.ArrayList;
import java.util.Iterator;
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
        // implement this method
    	if (dictionary.isMember(startWord) && dictionary.isMember(endWord))
    	{
    		List<String> pastWords = new ArrayList<String>();
    		List<String> result = MakeLadder(startWord, endWord, 0, pastWords);
    		/*Iterator a = result.iterator();
    		while(a.hasNext()){
    			String wor = (String) a.next();
    			System.out.println(wor);
    		}*/
    	}
        //throw new UnsupportedOperationException("Not implemented yet!");
        throw new NoSuchLadderException("No ladder found.");
    }
    
    private List<String> MakeLadder(String fromWord, String toWord, int positionLastChanged, List<String> pastWords) throws NoSuchLadderException
    {
    	List<String> result = new ArrayList<String>();
    	if (fromWord.equals(toWord))
    		return result;
    	for (int i = positionLastChanged; i < 5; i++)
    	{
    		StringBuilder newWord = new StringBuilder(fromWord);
    		//newWord.setCharAt(i, 'a');
    		for (int k = 0; k < 25; k++)
    		{
    			newWord.setCharAt(i, (char) ('a'+k));
    			/*if (newWord.toString().equals("conks")){
    				int z = 0;
    				z++;
    			}*/
    			if (dictionary.isMember(newWord.toString()) && !pastWords.contains(newWord.toString()))
    			{
    				pastWords.add(newWord.toString());
    				System.out.println(newWord.toString());
    				result.add(newWord.toString());
    				try
    				{
    					result = MakeLadder(newWord.toString(), toWord, positionLastChanged + 1, pastWords);
    				}
    				catch (NoSuchLadderException e)
    				{
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
    public boolean isWord(String word){		//this probably doesn't need its own function, since it implements the dictionary function
    	if (dictionary.isMember(word)){
    		return true;
    	}
    	return false;
    }
}
