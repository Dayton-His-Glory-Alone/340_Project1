package maze;

public class Square {

	public String location;
	public boolean visited;	
	public int x; 
	public int y;
	
	public Square (char ch, int i, int j) throws Exception {
		
		visited = false;
		x = i;
		y = j;
		
		if (ch=='#') {
			//wall
			location= "wall";
		}
		else if(ch=='.') {
			//open space
			location= "open";
		}
		else if(ch=='o') {
			//start
			location= "start";
		}
		else if(ch=='*') {
			//finish
			location= "finish";
		}
		else {
			throw new IllegalAccessException("invalid character");
		}
		
	}
	
	public String getChar() {
		
		if (location.equals("wall")) {
			
			return "#";
		}
		else if (location.equals("open")) {
			
			return ".";
		}
		else if (location.equals("start")) {
			return "o";
		}
		else if (location.equals("finish")) {
			return "*";
		}
		else {
			return null;
		}	
		
	}
	
	public void setVisited(boolean value) {
		
		visited = value;
		
	} 
	
	
	public String toString() {
		
		if (location.equals("wall")) {
			
			return "#";
		}
		else if (location.equals("open")) {
			
			return ".";
		}
		else if (location.equals("start")) {
			return "o";
		}
		else if (location.equals("finish")) {
			return "*";
		}
		else {
			return null;
		}		
	}
}