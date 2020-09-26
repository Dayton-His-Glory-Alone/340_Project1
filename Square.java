package maze;

public class Square {

	private Coordinate coordinate;
	private Marker type;
	private Square parent;

	
	public Square (Marker m, Coordinate c) {
		this.type = m;
		this.coordinate = c;
	}
	
	public Marker getMarker() {
		return type;
	}

	public Coordinate getCoordinate(){
		return this.coordinate;
	}

	public String printCoordinate(){
		return "(" + this.coordinate.getX() + ", " + this.coordinate.getY() + ")";
	}

	public String getChar(){
		String returnString;

		switch (type){
			case WALL:
				returnString = "#";
				break;

			case OPEN_SPACE:
				returnString = ".";
				break;

			case START:
				returnString = "o";
				break;

			case FINISH:
				returnString = "*";
				break;

			default:
				returnString = "MARKER_RETURN_ERROR";
		}

		return returnString;
	}

	public void setParent(Square s){
		this.parent = s;
	}

	public Square getParent() {
		return this.parent;
	}
}