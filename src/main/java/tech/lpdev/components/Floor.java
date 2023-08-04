package tech.lpdev.floor;

import tech.lpdev.room.Room;
import tech.lpdev.utils.ConsoleColour;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Floor {

    private final int[][] floor;
    private final List<Room> rooms;

    /**
     * -10 = blocked space
     * -3 = access point
     * -2 walkway
     * -1 = NONE
     * 0 = open space
     * 1 = EXECUTIVE
     * 2 = HR
     * 3 = FINANCE
     * 4 = PURCHASING
     * 5 = SALES_AND_MARKETING
     * 6 = SECURITY
     * 7 = COMMUNICATIONS
     * 8 = LEGAL
     * 9 = IT
     * 10 = SPECIALTY_ONE
     * 11 = SPECIALTY_TWO
     * 12 = Bathroom
     * 13 = Storage
     */

    // 6' x 6' min
    public Floor(int width, int height) {
        this.floor = new int[width][height];
        this.rooms = new ArrayList<>();
    }

    public Floor(int[][] layout) {
        int[][] floor = new int[layout.length][layout[0].length];
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {
                if (layout[i][j] == 1) floor[i][j] = -10;
                else if (layout[i][j] == 2) floor[i][j] = -3;
                else if (layout[i][j] == 3) floor[i][j] = -2;
                else floor[i][j] = layout[i][j];
            }
        }
        this.floor = floor;
        this.rooms = new ArrayList<>();
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
                    case -10 -> color = ConsoleColour.custom(new Color(0, 0, 0));
                    case -3 -> color = ConsoleColour.custom(new Color(173, 49, 77));
                    case -2 -> color = ConsoleColour.custom(new Color(153, 153, 153));
                    case -1 -> color = ConsoleColour.custom(new Color(0, 153, 51));
                    case 1-> color =  ConsoleColour.custom(new Color(255, 0, 102));
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

    public int getSpace(int x, int y) {
        return floor[x][y];
    }

    public void setSpace(int x, int y, int type) {
        floor[x][y] = type;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
