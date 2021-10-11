package model;

public class Coordinate {

    private int length;
    private int width;

    public Coordinate(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return length+","+width;
    }

    public static Coordinate stringToCoordinate(String str){
        try{
            String[] strArray=str.split(",");
            int length=Integer.parseInt(strArray[0]);
            int width=Integer.parseInt(strArray[1]);
            Coordinate coordinate=new Coordinate(length,width);
            return coordinate;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public static Coordinate defaultCoordinate(){
        return new Coordinate(0,0);
    }


}
