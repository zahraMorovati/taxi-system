package model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Coordinate {

    @Column(insertable = false,updatable = false,name = "coordinate_lenght")
    private int length;
    @Column(insertable = false,updatable = false,name = "coordinate_width")
    private int width;

    @Override
    public String toString() {
        return length+","+width;
    }

    public static Coordinate stringToCoordinate(String str){
        try{
            String[] strArray=str.split(",");
            int length=Integer.parseInt(strArray[0]);
            int width=Integer.parseInt(strArray[1]);
            Coordinate coordinate=Coordinate.CoordinateBuilder.aCoordinate().setLength(length).setWidth(width).build();
            return coordinate;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public static Coordinate defaultCoordinate(){
        return new Coordinate();
    }

    public static double calculateDistance(Coordinate c1,Coordinate c2){
        int x1=c1.length;
        int y1=c1.width;
        int x2=c2.length;
        int y2=c2.width;

        double distance=((y2-y1)*(y2-y1))+((x2-x1)*(x2-x1));
        if(distance<0){
            distance=distance*(-1);
        }
        distance=Math.sqrt(distance);
        return distance;
    }


    public static final class CoordinateBuilder {
        private Coordinate coordinate;

        private CoordinateBuilder() {
            coordinate = new Coordinate();
        }

        public static CoordinateBuilder aCoordinate() {
            return new CoordinateBuilder();
        }

        public CoordinateBuilder setLength(int length) {
            coordinate.setLength(length);
            return this;
        }

        public CoordinateBuilder setWidth(int width) {
            coordinate.setWidth(width);
            return this;
        }

        public Coordinate build() {
            return coordinate;
        }
    }
}
