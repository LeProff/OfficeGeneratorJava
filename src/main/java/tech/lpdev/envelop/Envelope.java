package tech.lpdev.envelop;

import lombok.Getter;
import tech.lpdev.Floor;
import tech.lpdev.utils.Logger;

public class Envelope {

    @Getter
    private final int[][] inner, outer;

    public Envelope(int[][] inner, int[][] outer) {
        this.inner = inner;
        this.outer = outer;
    }

//    public Floor getInnerFloor() {
//        return new Floor(inner);
//    }
//
//    public Floor getOuterFloor() {
//        return new Floor(outer);
//    }


    public void display() {
        Logger.log("ENV --> Width: " + outer[0].length + " Height: " + outer.length);
//        System.out.println("Inner:");
//        for (int[] row : inner) {
//            for (int i : row) {
//                System.out.print(i + " ");
//            }
//            System.out.println("\n");
//        }
//
//        System.out.println("Outer:");
//        for (int[] row : outer) {
//            for (int i : row) {
//                System.out.print(i + " ");
//            }
//            System.out.println("\n");
//        }
    }
}
