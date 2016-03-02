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
    	if (dictionary.isMember(startWord) && dictionary.isMember(endWord))
    	{
    		List<String> pastWords = new ArrayList<String>();
    		pastWords.add(startWord);
    		MakeLadder(startWord, endWord, -1, result, pastWords); // -1 because we havent changed any letters,
    															   // so, we want to start changing from index 0 (frist letter)
    	}
    	return result;
        //throw new NoSuchLadderException("No ladder found.");
    }
    
    private void MakeLadder(String fromWord, String toWord, int positionLastChanged, List<String> result, List<String> pastWords) throws NoSuchLadderException
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
    					//next part is commented because it doesnt impact the size of the result in anyway
    					//
    					/*if(result.size() > 2){

	    		    		if (letterDifference(fromWord, result.get(result.indexOf(fromWord)-2)) == 1)
	    		    		{
	    		    			result.remove(result.indexOf(fromWord)-1);
	    		    		}
    					}*/
    					MakeLadder(newWord.toString(), toWord, i, result, pastWords);
    					//I changed ^ by replacing positionLastChanged + 1 with i, because i will always accurately
    					// reflect the position of the last changed word, whereas positionLastChanged + 1 would exceed
    					// the bounds [0,4], which is incorrect (this would possibly allow for the last letter position
    					// that was changed to be changed again (this is why I used the commented code above.).
    					
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
    
    public int letterDifference(String fromWord, String toWord){
    	int result = 0;
    	char[] from = fromWord.toCharArray();
    	char[] to = toWord.toCharArray();
    	
    	for (int i = 0; i <from.length; i++){
    		if (from[i] != to[i])
    		{
    			result++;
    		}
    	}
    	return result;
    }
}
