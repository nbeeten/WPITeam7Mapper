package edu.wpi.off.by.one.errors.code;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/* file format
 * one element per line
 * may have whitespace wherever you want
 * one character at the beginning of the line (may have whitespace before) that indicates the type (p for point, e for edge, etc)
 * each element has a list of args, seperated by whitespace, eg: e 10.5 20.3 18.5 james
 * may change this later, so i can include strings that have spaces in them
 *
 */


public class FileIO {
	static void parsepointline(String[] args, Display dpy){
		if(args.length < 5) return;
		//dpy.points.addPoint(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]), etc);
	}
	static void parseedgeline(String[] args, Display dpy){
		if(args.length < 3) return;
		//dpy.edges.addEdge(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Float.parseFloat(args[2]), etc);
	}
	static void parseline(String line, Display dpy){
		//get ready for some obviously dont know how to parse strings in java so im doing it manually stuff
		int i;
		int len = line.length();
		char s = Character.toLowerCase(line.charAt(0));
		for(i = 0; i < len && Character.isWhitespace(s); i++) s = Character.toLowerCase(line.charAt(i)); // get rid of whitespace in front
		switch(s){
			case 'p':	// point;
				parsepointline(line.substring(i+1).split("\\s"), dpy);
				break;
			case 'e':	//edge;
				parseedgeline(line.substring(i+1).split("\\s"), dpy);
				break;
			default:	//some sorta error, or unrecognized element type
				break;
		}
	}

	//when calling load, you should ALWAYS keep track of the return display. It may create a new one.
	public static Display load(String inpath, Display indpy){
		Display curdpy = indpy;
		if(curdpy == null) curdpy = new Display(); //CONTRUCTOOOOOOOOOR needed plz

		//read in all lines
		Path pty = Paths.get(inpath);
		if(!Files.exists(pty)){
			System.out.printf("File %s does not exist, unable to load\n", inpath);
		}
		List<String> lines = null;
		//todo should fix this try catch BS
		try {
			lines = Files.readAllLines(pty, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		for(String line : lines){
			parseline(line, curdpy);
			i++;
		}
		System.out.printf("Read %i lines\n", i);
		return curdpy;
	}

}
