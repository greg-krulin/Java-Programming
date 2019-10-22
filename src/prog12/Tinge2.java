package prog12;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Tinge2 implements SearchEngine{
	//give me the url, i can give you the index. give me the index, i can give you the page
	HardDisk<PageFile> pageFiles= new HardDisk<PageFile>();
	HardDisk<List<Long>> wordFiles = new HardDisk<List<Long>>();
	PageTrie url2index = new PageTrie();
	WordTable wordTable = new WordTable();
	
	Long indexPage(String url) {
		
		Long index = pageFiles.newFile();
		PageFile pageFile = new PageFile( index, url);
		
		System.out.println("Indexing page " + pageFile);
		pageFiles.put(index, pageFile);
		url2index.put(url, index);
		return index;
	}
	
	Long indexWord( String word) {
		Long wordIndex = wordFiles.newFile();
		List<Long> wordList = new ArrayList<Long>();
		System.out.println("indexing word: " + wordIndex+ "(" + word + ")" + wordList );
		wordFiles.put(wordIndex, wordList);
		wordTable.put(word, wordIndex);
		return wordIndex;
		
	}
	
	

	
	public void gather(Browser browser, List<String> startingURLs) {
		url2index.read(pageFiles);
		wordTable.read(wordFiles);
		
	}
	
	  /** If all the currentPageIndices are the same (because are just
    starting or just found a match), get the next page index for
    each word: call next() for each word file iterator and put the
    result into current page indices.

    If they are not all the same, only get the next index if the
    current index is smaller than the largest.

    Return false if hasNext() is false for any iterator.

    @param currentPageIndices array of current page indices
    
    @param wordFileIterators array of iterators with next page indices
    @return true if all minimum page indices updates, false otherwise
*/
	private boolean getNextIndices(long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {
		if( allEqual(currentPageIndices)) {
			for( int i = 0; i< currentPageIndices.length; i++) {
				if( !wordFileIterators[i].hasNext())
					return false;
				currentPageIndices[i] = wordFileIterators[i].next();
				return true;
			}
		}
		else {
		
			long maxValue = -1;
			for( int j = 0; j< currentPageIndices.length; j++) {
				if( currentPageIndices[j] > maxValue )
					maxValue = currentPageIndices[j];
			}
			for( int k = 0; k < currentPageIndices.length; k++) {
				if(!wordFileIterators[k].hasNext())
					return false;
				if(currentPageIndices[k] < maxValue)
					currentPageIndices[k] = wordFileIterators[k].next();
			}
		return true;
		}
		return true;
	}
	
	 /** Check if all elements in an array are equal.
    @param array an array of numbers
    @return true if all are equal, false otherwise
*/
	private boolean allEqual (long[] array) {
		for( int i = 0; i< array.length; i++) {
			if( array[i] != array[0])
				return false;
			
		}
		return true;
		
	}
	
	public String[] search(List<String> keyWords, int numResults) {
		
		// Iterator into list of page ids for each key word.
		 Iterator<Long>[] wordFileIterators = (Iterator<Long>[]) new Iterator[keyWords.size()];
		 
		    // Current page index in each list, just ``behind'' the iterator.
		  long[] currentPageIndices = new long[keyWords.size()];
		  
		   // LEAST popular page is at top of heap so if heap has numResults
		    // elements and the next match is better than the least popular page
		    // in the queue, the least popular page can be thrown away.

		    PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new PageCompator());
		    
		    int i = 0;
		   // int matchCount = 0;
		    for(String word: keyWords) {
		    	if(wordTable.containsKey(word)) {
		    		Long wordIndex = wordTable.get(word);
		    		Iterator<Long> wordIterator = wordFiles.get(wordIndex).listIterator();
		    		wordFileIterators[i] = wordIterator;
		    		i++;
		    	}
		    	else {
		    		//System.out.println(word + " not found.");
		    		return new String[0];
		    	}
		    }
		    while(getNextIndices(currentPageIndices, wordFileIterators)) {
		    	if(allEqual(currentPageIndices)) {
		    		bestPageIndices.offer(currentPageIndices[0]);
		    		//matchCount++;
		    	}
		    }
		    String[] resultArray = new String[bestPageIndices.size()];
		    for( int p = resultArray.length - 1; p >= 0; p--) {
		    	resultArray[p] = pageFiles.get(bestPageIndices.poll()).url;
		    }

		return resultArray;
	}
	
	protected class PageCompator implements Comparator<Long>{
		 
		public int compare(Long page1, Long page2) {
			int index1 = pageFiles.get(page1).getRefCount();
			int index2 = pageFiles.get(page2).getRefCount();
			return index1 - index2;
		 }
		/*
		boolean equals(Long page1, Long page2) {
			return compare(page1, page2) == 0;
		}
		*/
		
	}
}