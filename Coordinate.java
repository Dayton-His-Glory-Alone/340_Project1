package maze;

public class Coordinate {
    //X and Y coordinate positions
    private int x;
    private int y;

    public Coordinate(int a, int b){
        this.x = a;
        this.y = b;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null){
            if(obj.getClass() == this.getClass()){
                Coordinate tempObj = (Coordinate) obj;

                if(this.x == tempObj.getX() && this.y == tempObj.getY()){
                    return true;
                }
            }
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
