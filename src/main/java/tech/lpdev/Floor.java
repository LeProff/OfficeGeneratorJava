package tech.lpdev;

import tech.lpdev.room.Room;
import tech.lpdev.utils.ConsoleColour;

import java.awt.*;

public class Floor {

    private int[][] floor;

    public Floor(int width, int height) {
        this.floor = new int[width][height];
    }

    public Floor(int size) {
        this.floor = new int[size][size];
    }

    public boolean drawRoom(Room room, int x, int y) {
        if (!checkSpace(room, x, y)) return false;
        for (int i = x; i < x + room.getWidth(); i++) {
            for (int j = y; j < y + room.getLength(); j++) {
                floor[i][j] = room.getDepartment().getId();
            }
        }
        return true;
    }

    public boolean checkSpace(Room room, int x, int y) {
        if (x < 0 || y < 0 || x + room.getWidth() > floor.length || y + room.getLength() > floor[0].length) return false;
        for (int i = x; i < x + room.getWidth(); i++) {
            for (int j = y; j < y + room.getLength(); j++) {
                if (floor[i][j] != 0) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : floor) {
            for (int i : row) {
                String color = "";
                switch (i) {
                    case -1 -> color = ConsoleColour.custom(new Color(0, 153, 51));
                    case 1 -> color = ConsoleColour.custom(new Color(255, 0, 102));
                    case 2 -> color = ConsoleColour.custom(new Color(0, 153, 255));
                    case 3 -> color = ConsoleColour.custom(new Color(204, 51, 0));
                    case 4 -> color = ConsoleColour.custom(new Color(255, 255, 0));
                    case 5 -> color = ConsoleColour.custom(new Color(0, 255, 0));
                    case 6 -> color = ConsoleColour.custom(new Color(0, 102, 255));
                    case 7 -> color = ConsoleColour.custom(new Color(0, 255, 255));
                    case 8 -> color = ConsoleColour.custom(new Color(153, 255, 51));
                    case 9 -> color = ConsoleColour.custom(new Color(128, 0, 0));
                    case 10 -> color = ConsoleColour.custom(new Color(153, 0, 153));
                    case 11 -> color = ConsoleColour.custom(new Color(102, 0, 204));
                }
                String space = "";
                if (i > -1 && i < 10) space += " ";
                sb.append(color + "x " + ConsoleColour.RESET);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getWidth() {
        return floor.length;
    }

    public int getLength() {
        return floor[0].length;
    }
}
