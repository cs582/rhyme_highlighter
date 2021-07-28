import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class program{
	public static int indexColor = 0;
	public static String[] map = new String[100];
	public static void main(String[] args) throws Exception {
		//NAME FILE
		String textFile = args[0];
		String name_words[] = textFile.split("[.]");
		String name = name_words[0];
		//TITLE THE FILE
		String title = "<h1>"+ name.toUpperCase() + "</h1>";
		
		String line = "";
		//CREATE FILE
		String htmlFile = "rhymes//" + name + "_RHYMES.html";
		File file = new File(htmlFile);

		/*-------------Initialize the Algorithm---------------*/
		int N = myAPI.start(textFile);
		String[][] words = myAPI.readDocument(textFile);
		int[][] map = myAPI.mapRhymes(words);
		
		try  
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(title); //WRITE HEADER
			//WRITE LINE BY LINE
			bw.write("<body>\n");

			Pattern p = Pattern.compile("^[ ]*$");
			for(int i = 0; i < N; i++){
				bw.write("<p>\n");
				int M = words[i].length;
				for(int j = 0; j < M; j++){
					String cur = words[i][j];
					
					Matcher m = p.matcher(cur);
					if (M == 1 && m.matches()){bw.write("<br>\n"); break;}
					
					if (map[i][j] != -1){
						cur = "<span style=\"background-color:";
						cur += toColor(map[i][j]);
						cur += "\">" + words[i][j] + "</span>\n";
					}
					
					bw.write(cur + " ");
				}
				bw.write("</p>\n");
			}
			bw.write("</body>\n");
			bw.close();
			System.out.println(htmlFile + " has been created.");
		}
		//catch the exception if any   
		catch (Exception e)   
		{e.printStackTrace();}

	}
	static String toColor(int x){
		String[] colors = {"#409cff", "#b59469", "#64d3ff","#30d158",
							"#5e5ce6", "#66d4cf", "#ff9f0a",
							"#ff375f", "#bf5af2", "#ff453a",
							"#40c8e0", "#ffd60a"};
		if (indexColor >= colors.length) indexColor = 0;
		if (map[x]==null) map[x] = colors[indexColor++];
		return map[x];
	}
}  
