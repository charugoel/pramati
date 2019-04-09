package com.typeahead.web.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
 
@Service
public class Trie {
	
	ArrayList<String> keys;
    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean endOfWord;
        public TrieNode() {
            children = new HashMap<Character,TrieNode>();
            endOfWord = false;
        }
    }

    private  TrieNode root=null;
     Trie() {
        root = new TrieNode();
        loadCityName( );
    }

 
    public  void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        //mark the current nodes endOfWord as true
        current.endOfWord = true;
    }

   

 
    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            //if node does not exist for given char then return false
            if (node == null) {
                return false;
            }
            current = node;
        }
        //return true of current's endOfWord is true else return false.
        return current.endOfWord;
    }

    
   
       
	 int autoSuggestions(final String query, ArrayList<String> result) { 
	     TrieNode pCrawl = root; 
	  
	    // Check if prefix is present and find the 
	    // the node (of last level) with last character 
	    // of given string. 
	    int level; 
	    int n = query.length(); 
	    for (level = 0; level < n; level++) 
	    { 
	    	 char ch = query.charAt(level);
	    	 TrieNode node = pCrawl.children.get(ch);
	    	 if (node == null) {
	    		// System.out.println("null return");
	                return -1;
	            }
	  
	        pCrawl = node; 
	    } 
	  
	    // If prefix is present as a word. 
	    boolean isWord = (pCrawl.endOfWord == true); 
	
	  
	    // If there are are nodes below last 
	    // matching character. 
	    if (!isWord) 
	    { 
	        String prefix = query; 
	        suggestionsRec(pCrawl, prefix,result); 
	        return 1; 
	    }
		return -1; 
	}
	
	 void suggestionsRec(TrieNode root, String currPrefix, ArrayList<String> result) { 
	    // found a string in Trie with the given prefix 
	    if (root.endOfWord) 
	    { 
	       System.out.println(currPrefix);
	       result.add(currPrefix);
	    } 
	  
	    for ( Character ch:root.children.keySet()) 
	    { 
	    	 TrieNode node = root.children.get(ch);
	    	
	        if (node!=null) 
	        { 
	            currPrefix=currPrefix+ch; 
	            // recur over the rest 
	            suggestionsRec(node, currPrefix,result);
	            currPrefix=removelastChar(currPrefix);
	        } 
	    } 
	}
	 public String removelastChar(String str) {
		    if (str != null && str.length() > 0) {
		    	return str.substring(0, str.length() - 1);
		    }
		    return str;
		}
	
	 public  void loadCityName( )  {
		 String fileName = "city.txt";
		 ClassLoader classLoader = getClass().getClassLoader();
		 File file = new File(classLoader.getResource(fileName).getFile());
		// System.out.println("file name -->"+file);
		 BufferedReader br = null;
		 FileReader fr = null;;
		 ArrayList<String> keys=new ArrayList<String>();

		 try {
			 fr = new FileReader(file);
			 br = new BufferedReader(fr);

			 String sCurrentLine;

			 while ((sCurrentLine = br.readLine()) != null) {
				 keys.add(sCurrentLine.toLowerCase());
			 }

		 } catch (IOException e) {

			 e.printStackTrace();

		 } finally {

			 try {

				 if (br != null)
					 br.close();

				 if (fr != null)
					 fr.close();

			 } catch (IOException ex) {

				 ex.printStackTrace();

			 }

		 }
		 for (int i = 0; i < keys.size() ; i++) {
			 insert(keys.get(i));
		 }
		 ArrayList<String> result=new ArrayList<String>();
		 autoSuggestions("M",result);

	 }
	 
	 public ArrayList<String> getAutoSuggestions(String tagName) 
		{ 
			
		    System.out.println("tagName -->"+tagName);
		    if(root==null) {
		    	System.out.println("Root is null");
		    }
			ArrayList<String> result=new ArrayList<String>();
			if(-1==autoSuggestions(tagName, result)) {
				System.out.println("No city found");
			} 
			return result;
			
		}	
	
}