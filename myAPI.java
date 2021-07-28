import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

public class myAPI{
	public static int N;
	public static int M = 30;
	
	public static void main(String[] args) throws Exception{
		String docName = args[0];
		
		start(docName);	//find number of lines
		String[][] words = readDocument(docName); //Convert the document to an array of strings
		int[][] map = mapRhymes(words); //map the rhymes into the document
		
		printmap(words, map);
	}
	
	static int start(String docName) throws Exception{
		Path path = Paths.get(docName);
		N = (int)Files.lines(path).count();
		return N;
	}
	
	static String[][] readDocument(String docName) throws Exception{
		File file = new File(docName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String[][] words = new String[N][M];
		
		String str;
		int i = 0;
		while((str = br.readLine()) != null)
		{ words[i++] = str.split("[, ?.:¿¡!]+"); }
		
		return words;
	}
	
	static int[][] mapRhymes(String[][] words){
		int[][] map = new int[N][M];
		HashMap<String, PriorityQueue<String>> hashmap;
		hashmap = new HashMap<String, PriorityQueue<String>>();
		int start = 0;
		for(int i = 0; i < N; i++){
			int m = words[i].length;
			for(int j = 0; j < m; j++){
				if (m != 1) {
					kindOfRhyme(words[i][j], hashmap, false);
					//finder(hashmap, words[i][j]);
				}
				if (m == 1 || (i==N-1 && j==m-1))
				{
					int end = (i==N-1 && j==m-1) ? N : i;
					for(int idx = start; idx < end; idx++){
						for(int jdx = 0; jdx < words[idx].length; jdx++){
							map[idx][jdx] = kindOfRhyme(words[idx][jdx], hashmap, true);
							//map[idx][jdx] = finder(hashmap, words[idx][jdx]);
						}
					}
					start = i+1;
					hashmap.clear();
				}
			}
		}
		return map;
	}
	
	static int kindOfRhyme(String word, HashMap<String, PriorityQueue<String>> hashmap, boolean secondScan){
		//Set word to lower case
		word = word.toLowerCase();
		String str = "";
		int len = word.length();
		//If the word has less than 3 characters then do not count it
		if (len < 3) return -1;
		
		//Find the vowels of this word
		int num = 0;
		boolean stop = false;
		for(int i = len-1; i >= 0 && !stop; i--){
			char cur = word.charAt(i);
			if(isVowel(cur)){
				num++;
				if (num<3) {str=simple(cur)+str; str="-"+str;}
			}
			stop = num == 3;
			/*
				If the accent is in the 3rd-to-last vowel then it's an esdrujula
				this kind of word can only rhyme with other esdrujulas
				Even though some words in spanish do not have the accent written
				esdrujulas must always have the accent written in them
			*/
			if(stop&&isAccent(cur))	{ str=""+cur; break;}
		}
		//Clean the string
		int strLength = str.length();
		String res = str.substring(strLength > 0 ? 1 : 0,strLength);
		
		//Store the word in the hashmap
		int hashValue = -1;
		if (hashmap.get(res) == null){
			PriorityQueue<String> q = new PriorityQueue<String>();
			q.add(word);
			hashmap.put(res, q);
		} else {
			if (!secondScan && !hashmap.get(res).contains(word)) {
				hashmap.get(res).add(word);
			}
			int numWords = hashmap.get(res).size();
			hashValue = numWords > 1 ? hashFunction(res) : -1;
		}
		return hashValue;
	}

	static void printmap(String[][] words, int[][] map) throws Exception{
		for(int i = 0; i < N; i++){
			int m = words[i].length;
			if (m != 1)
			{
				for(int j = 0; j < m; j++)
				{System.out.print("(" + words[i][j] + "," + map[i][j] + ")");}
			}
			System.out.println();
		}
	}
	
	public static int hashFunction(String x) {
		int HASH = 100;
		char ch[];
		ch = x.toCharArray();
		int xlength = x.length();
		
		int i, sum;
		for (sum=0, i=0; i < x.length(); i++)
			sum += ch[i]*(i+1);
		return sum % HASH;
	}

	static boolean isVowel(char c){
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
				c == 'á'| c == 'é' || c == 'í' || c == 'ó' || c == 'ú';
	}

	static boolean isAccent(char c){
		return c == 'á'| c == 'é' || c == 'í' || c == 'ó' || c == 'ú';
	}
	
	static char simple(char c){
		switch (c) {
			case 'á': return 'a';
			case 'é': return 'e';
			case 'í': return 'i';
			case 'ó': return 'o';
			case 'ú': return 'u';
		}
		return c;
	}
}

