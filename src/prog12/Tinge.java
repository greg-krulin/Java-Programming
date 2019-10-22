package prog12;

import java.util.List;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


public class Tinge implements SearchEngine{
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
		//creates a queue to keep track of all the indexs for each webpage
		Queue<Long> pageIndicies = new ArrayDeque();//ArrayDeque<Long>();
		//for the starting list of URLs, go through each one and if hasn't been hashmapped yet, put it in and que it
		for( String url: startingURLs) {
			if( !url2index.containsKey(url) ) {
				pageIndicies.offer((Long)indexPage(url));
				
			}
			/*
			else {
				Long tempIndex = url2index.get(url);
				PageFile pageFile = pageFiles.get(tempIndex);
				pageFile.setRefCount(pageFile.getRefCount()+1);
				
				
			}
			
		for( String url2: startingURLs) {
			Long tempIndex = url2index.get(url2);
			PageFile pageFile = pageFiles.get(tempIndex);
			System.out.println("inc ref " + pageFile.getRefCount() + pageFile);
		}
		*/
		}
		//for each index in the queue, poll it, get the webpage stored on our hard drive.  if the webpage loads, get all the url links
		//on the webpage.  check if each link on the url has been index before and if not, put in the queue.  Keep doing untill queue is empty
		while( !pageIndicies.isEmpty()) {
			System.out.println("Queue" + pageIndicies);
			Long pageIndex = pageIndicies.poll();
			PageFile pageFile = pageFiles.get(pageIndex);
			System.out.println("Dequeued " + pageFile) ;
			
			
			if( browser.loadPage(pageFile.url)) {
				List<String> urls = browser.getURLs();
				Set<Long> refPageInc = new HashSet<Long>();
				Set<Long> wordSet = new HashSet<Long>();
				System.out.println("urls: " + urls);
				Long urlIndex;
				Long wordIndex;
				
				
				for( String url : urls) {
					if( !url2index.containsKey(url) ) {
						pageIndicies.offer(indexPage(url));	
					}
					urlIndex = url2index.get(url);
					refPageInc.add(urlIndex);
					/*
					else {
						PageFile pageFile2 = pageFiles.get(url2index.get(url));
						pageFile2.setRefCount(pageFile2.getRefCount() + 1);
						//System.out.println("Ref count " + pageFile2.getRefCount() + pageFile2);

					}
					*/
					
				}
				for( Long urlindex: refPageInc) {
					
					pageFiles.get(urlindex).incRefCount();
					System.out.println("inc ref " + pageFiles.get(urlindex));
				}
				
				System.out.println("words " + browser.getWords() );
				
				for( String words: browser.getWords() ) {
					if( !wordTable.containsKey(words)) {
						 wordIndex = indexWord(words);
					}
					else {
						wordIndex = wordTable.get(words);
					}
					if( !wordSet.contains(wordIndex)) {
					wordSet.add(wordIndex);
					wordFiles.get(wordIndex).add(pageIndex);
					System.out.println("add page " + wordIndex +"(" + words + ")" + wordFiles.get(wordIndex) );
					}
				}
				System.out.println("urls" + urls);
				
			}
		}
		System.out.println("pageFiles \n" + pageFiles);
		System.out.println("url2index \n" + url2index);
		System.out.println("wordFiles \n" + wordFiles);
		System.out.println("wordTable \n" + wordTable);
		wordTable.write(wordFiles);
		url2index.write(pageFiles);
		
	}
	
	public String[] search(List<String> keyWords, int numResults) {
		return new String[0];
	}
}