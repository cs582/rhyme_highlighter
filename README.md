# Rhymes Highlighter
This Java program analyzes the rhymes in Spanish-language songs, it takes a <code>txt</code> file and finds the words that rhyme with each other, it finally creates an <code>HTML</code> file.

# Algorithm
The program has two main functions called <code>readDocument</code> and <code>mapRhymes</code>.

The function called <code>readDocument</code> reads the text file and stores all the words in a 2D array.

The second function <code>mapRhymes</code> makes three different processes.
 
 First. It goes through each word and finds the sequence of vowels that rhyme in each word. For example, for "varios" the requence is "i-o"
 
 Second. It maps each sequence to a list of words that have such sequence. For example "i-o" is mapped to the list {"varios", "calvario"}. However, the list is implemented as a Priority Queue because it's necessary to find wheter a certain word is already in the list, in such a case nothing changes.
 
 Third. Every time a verse ends, every word in the last verse will be mapped to an integer if and only if the sequence maps to 2 words or more. For example, "varios" will be associated with the sequence "i-o" and such a sequence will be mapped to a unique integer by using a <code>hash function</code>. e.g. "varios" and "calvarios" could map to 45 because they share the same sequence of vowels "i-o".
 
 # Program
 
 Finally, the program calls both functions and then writes an html file.

# How to use?

In order to use it, you should frist compile <code>program.java</code>, and then run the program and giving the <code>txt</code> file you choose, for exmaple <code>program your_lyrics.txt</code>.

After you run the program it should print <code>rhymes//your_lyrics.html has been created.</code> and you can find an html file with the lyrics and the words that rhyme with each other highlighted with different colors.
