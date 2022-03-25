package trie;

import java.util.*;

public class Trie {

	private class Node{
		private HashMap<Character, Node> children;
		boolean end;

		public Node(){
			children = new HashMap<>();
			end = false;
		}
	}
	private Node root;
	public String s;

	public Trie(){
		root = new Node();
	}

	/**
	 * Inserts the specified string into the Trie. The Last node associated to the
	 * of the last char of the specified string will be marked as an end-of-word node.
	 * @param word
	 */
	public void insert(String word){
		Node current = root;
		for (int i=0; i < word.length(); i++){
			char c = word.charAt(i);
			Node node = current.children.get(c);
			if(node == null){
				node = new Node();
				current.children.put(c, node);
			}
			current = node;
		}
		current.end = true;
	}

	/**
	 * Returns true if this Trie contains the specified string. In other words
	 * the last char of the specified string is a node that is marked as
	 * a end-of-word node.
	 * @param word
	 * @return
	 */
	public boolean contains(String word){
		Node current = root;
		for(int i =0; i < word.length(); i++){
			char c = word.charAt(i);
			Node node = current.children.get(c);
			if(node == null)
				return false;
			current = node;
		}
		return current.end;
	}
	

	/**
	 * Returns true if this Trie contains the specified string as a word or a
	 * word in the Trie begins with the specified string.
	 * @param prefix
	 * @return
	 */
	public boolean containsPrefix(String prefix){
		Node current = root;
		for(int i =0; i < prefix.length(); i++){
			char c = prefix.charAt(i);
			Node node = current.children.get(c);
			if(node == null)
				return false;
			current = node;
		}
			return true;

	}

	/**
	 * Returns all words that begin with the specified prefix.
	 * @param prefix
	 * @return
	 */
	public Set<String> getAllWords(String prefix){

		char[] arr = prefix.toCharArray();
		Node current = root;
		Set<String> a = new HashSet<>();
		for (char c : arr) {
			current = current.children.get(c);
			if (current == null)
				break;
		}
		if (current != null) {
			getWords(current, prefix, a);
		}
		return a;

	}
	private void getWords(Node node, String prefix, Set<String> a) {
		if (node.end) {
			a.add(prefix);
		}
		for (Map.Entry<Character, Node> e : node.children.entrySet()) {
			getWords(e.getValue(), prefix + e.getKey(), a);
		}
	}


	/**
	 * Returns all words of odd length (odd number of characters) that begin
	 * with the specified prefix.
	 * @param prefix
	 * @return
	 */
	public Set<String> getAllOddWords(String prefix){
		Set<String> allWords = getAllWords(prefix);
		Set<String> allOddWords = new HashSet<>();
		for(String word:allWords) {
			if(word.length()%2 != 0) {
				allOddWords.add(word);
			}
		}
		return allOddWords;
	}

	/**
	 * Returns all words of even length (even number of characters) that begin
	 * with the specified prefix.
	 * @param prefix
	 * @return
	 */
	public Set<String> getAllEvenWords(String prefix){
		Set<String> allWords = getAllWords(prefix);
		Set<String> allEvenWords = new HashSet<>();
		for(String word:allWords) {
			if(word.length()%2 == 0) {
				allEvenWords.add(word);
			}
		}
		return allEvenWords;

	}
	
}
