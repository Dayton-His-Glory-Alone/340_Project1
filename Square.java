package maze;

public class Square {

	public String character;
	public Square (char ch) throws Exception {
		
		if (ch=='#') {
			//wall
			character= "wall";
		}
		else if(ch=='.') {
			//open space
			character= "open";
		}
		else if(ch=='o') {
			//start
			character= "start";
		}
		else if(ch=='*') {
			//finish
			character= "finish";
		}
		else {
			throw new IllegalAccessException("invalid character");
		}
		
	}
	
}
