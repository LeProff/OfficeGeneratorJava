package tech.lpdev;

import lombok.Getter;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;
import tech.lpdev.components.Building;
import tech.lpdev.components.Company;
import tech.lpdev.components.Floor;
import tech.lpdev.utils.Config;
import tech.lpdev.utils.FileUtils;
import tech.lpdev.utils.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    @Getter
    private static final Config config = new Config("/config.yml");

    private static Company currentCompany;

    static int multiplier = 10;
    private static SVGGraphics2D svg;

    private static List<String> checked;
    private static List<int[][]> rooms;

    public static void main(String[] args) throws IOException {

        FileUtils.addFolder("companies");

        int count = 1;
        if (args.length > 1) {
            Logger.error("Too many arguments");
        } else if (args.length == 1) {
            try {
                count = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                Logger.error("Argument must be a number");
            }
        }

        Logger.info("Generating " + count + " companies");
        for (int i = 0; i < count; i++) {
            generateCompany();
            Logger.info("Generated company " + (i + 1));
        }
        Logger.success("Finished generating " + count + " companies");
    }
    private static void generateCompany() throws IOException {
        Company company = new Company();
        company.generate();

//        System.out.println(company);

        currentCompany = company;
        FileUtils.addFolder("companies/" + currentCompany.getName());

        Building building = company.getBuilding();
        List<Floor> floors = Building.getFloors();

        for (int i = 0; i < floors.size(); i++) {
            checked = new ArrayList<>();
            rooms = new ArrayList<>();

            svg = new SVGGraphics2D(floors.get(i).getLength() * multiplier * 1.5, floors.get(i).getWidth() * multiplier * 1.5);

            drawFloor(floors.get(i));

            separateRooms(floors.get(i));

            for (int[][] room : rooms) {
                drawDoors(floors.get(i), room);
            }

            SVGUtils.writeToSVG(new File(FileUtils.getJarPath() + "/companies/" + currentCompany.getName() + "/floor" + (i + 1) + ".svg"), svg.getSVGElement());
        }
    }

    public static void drawFloor(Floor floor) {
        int width = floor.getWidth(); // floor.length();
        int height = floor.getLength(); // floor[0].length;

        svg.setStroke(new BasicStroke(2));
        svg.setColor(Color.WHITE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int type = floor.getSpace(i, j);
                if (type == 12) {
                    svg.setColor(Color.BLUE);
                    svg.fillRect(j * multiplier, i * multiplier, multiplier, multiplier);
                    svg.setColor(Color.WHITE);
                }
                if (type == 13) {
                    svg.setColor(Color.RED);
                    svg.fillRect(j * multiplier, i * multiplier, multiplier, multiplier);
                    svg.setColor(Color.WHITE);
                }
                if (type == -1) {
                    svg.setColor(Color.GREEN);
                    svg.fillRect(j * multiplier, i * multiplier, multiplier, multiplier);
                    svg.setColor(Color.WHITE);
                }
                // purple for -11
                if (type == -11) {
                    svg.setColor(Color.MAGENTA);
                    svg.fillRect(j * multiplier, i * multiplier, multiplier, multiplier);
                    svg.setColor(Color.WHITE);
                }
                // orange walkway
                if (type == -2) {
                    svg.setColor(Color.ORANGE);
                    svg.fillRect(j * multiplier, i * multiplier, multiplier, multiplier);
                    svg.setColor(Color.WHITE);
                }

                //right | top
                if (i + 1 >= width || floor.getSpace(i + 1, j) != type) {
                    if (!(i + 1 >= width && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine(j * multiplier, (i + 1) * multiplier, (j + 1) * multiplier, (i + 1) * multiplier);

//                        svg.drawLine((x + 1) * multiplier, y * multiplier, (x + 1) * multiplier, (y + 1) * multiplier);
//                        svg.drawLine((x + 1) * multiplier + 1, y * multiplier, (x + 1) * multiplier + 1, (y + 1) * multiplier);
                        svg.setColor(Color.WHITE);
                    }
                }
                //left | bot
                if (i - 1 < 0 || floor.getSpace(i - 1, j) != type) {
                    if (!(i - 1 < 0 && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine(j * multiplier, i * multiplier, (j + 1) * multiplier, i * multiplier);

//                        svg.drawLine(i * multiplier, j * multiplier, i * multiplier, (j + 1) * multiplier);
//                        svg.drawLine(i * multiplier + 1, j * multiplier, i * multiplier + 1, (j + 1) * multiplier);
                        svg.setColor(Color.WHITE);
                    }
                }
//                // down
                if (j + 1 >= height || floor.getSpace(i, j + 1) != type) {
                    if (!(j + 1 >= height && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine((j + 1) * multiplier, i * multiplier, (j + 1) * multiplier, (i + 1) * multiplier);


//                        svg.drawLine(i * multiplier, (j + 1) * multiplier, (i + 1) * multiplier, (j + 1) * multiplier);
//                        svg.drawLine(i * multiplier, (j + 1) * multiplier + 1, (i + 1) * multiplier, (j + 1) * multiplier + 1);
                        svg.setColor(Color.WHITE);
                    }
                }
////                // up
                if (j - 1 < 0 || floor.getSpace(i, j - 1) != type) {
                    if (!(j - 1 < 0 && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine(j * multiplier, i * multiplier, j * multiplier, (i + 1) * multiplier);

//                        svg.drawLine(i * multiplier, j * multiplier, (i + 1) * multiplier, j * multiplier);
//                        svg.drawLine(i * multiplier, j * multiplier + 1, (i + 1) * multiplier, j * multiplier + 1);
                        svg.setColor(Color.WHITE);
                    }
                }
            }
        }
        svg.setColor(Color.BLACK);
        svg.drawLine(0, width * multiplier + 25, 0, width * multiplier + 35);
        svg.drawLine(0, width * multiplier + 30, height * multiplier, width * multiplier + 30);
        svg.drawLine(height * multiplier, width * multiplier + 25, height * multiplier, width * multiplier + 35);
        svg.drawString(height + "'", height * multiplier / 2 - 30, width * multiplier + 45);

        svg.drawLine(height * multiplier + 25, 0, height * multiplier + 35, 0);
        svg.drawLine(height * multiplier + 30, 0, height * multiplier + 30, width * multiplier);
        svg.drawLine(height * multiplier + 25, width * multiplier, height * multiplier + 35, width * multiplier);
        svg.drawString(width + "'", height * multiplier + 40, width * multiplier / 2 - 10);
    }

    // Have map to store how many tiles since the last door on a certain type


    public static void separateRooms(Floor floor) {
        for (int i = 0; i < floor.getWidth(); i++) {
            for (int j = 0; j < floor.getLength(); j++) {
                if (floor.getSpace(i, j) == -3 || floor.getSpace(i, j) == -2 || floor.getSpace(i, j) == -10) continue;
                if (checked.contains(i + "," + j)) continue;
                int[][] room = new int[floor.getWidth()][floor.getLength()];
                buildRoom(floor, i, j, floor.getSpace(i, j), room);


//                for (int k = 0; k < room.length; k++) {
//                    for (int l = 0; l < room[0].length; l++) {
//                        System.out.print(room[k][l]);
//                    }
//                    System.out.println();
//                }
//                System.out.println();
                rooms.add(room);
            }
        }
    }

    public static void buildRoom(Floor floor, int x, int y, int type, int[][] room) {
        // check if out of bounds
        if (x < 0 || y < 0 || x >= floor.getWidth() || y >= floor.getLength()) return;
        // check if already checked
        if (checked.contains(x + "," + y)) return;
        // check if not same type
        if (floor.getSpace(x, y) != type) return;
        checked.add(x + "," + y);
        room[x][y] = type;
        buildRoom(floor, x + 1, y, type, room);
        buildRoom(floor, x - 1, y, type, room);
        buildRoom(floor, x, y + 1, type, room);
        buildRoom(floor, x, y - 1, type, room);
    }

    public static void drawDoors(Floor floor, int[][] room) {


    }

    @Getter
    private static class Edge {

        private final boolean walkway, vertical, left;
        private final Position start, end;

        private Edge(boolean walkway, boolean vertical, boolean left, Position start, Position end) {
            this.walkway = walkway;
            this.vertical = vertical;
            this.left = left;
            this.start = start;
            this.end = end;
        }

        public static List<Edge> getEdges(Floor floor, int[][] room) {
            Position start = null, end = null;
            int type = -10;
            // find top left corner of room
            for (int i = 0; i < room.length; i++) {
                for (int j = 0; j < room[0].length; j++) {
                    if (room[i][j] == 0) continue;
                    start = new Position(i, j);
                    type = room[i][j];
                }
            }
            if (start == null) return null;
            List<Edge> edges = new ArrayList<>();

            return null;
        }

        private static Edge getTopEdge(Position start, Floor floor, int type) {
            int x = start.getX(), y = start.getY();
            while (floor.getSpace(x, y) == type) {
                x++;
            }
            boolean walkway = floor.getSpace(x, y + 1) == -2;
            return new Edge(floor.getSpace(x, y) == -10, false, false, start, new Position(x, y));
        }
    }
}
