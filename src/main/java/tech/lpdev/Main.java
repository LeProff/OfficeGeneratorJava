package tech.lpdev;

import lombok.Getter;
import org.apache.poi.xwpf.usermodel.*;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        generateDocument();

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
                addLabels(floors.get(i), room);
            }

            SVGUtils.writeToSVG(new File(FileUtils.getJarPath() + "/companies/" + currentCompany.getName() + "/floor" + (i + 1) + ".svg"), svg.getSVGElement());
        }
    }

    public static void generateDocument() throws IOException {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph p = document.createParagraph();
        XWPFRun r = null;

        r = p.createRun();
        // 73A0DD as rgb string
        r.setColor("73A0DD");
        r.setFontSize(26);
        r.setBold(true);
        r.setText(currentCompany.getName());
        r.addBreak();
        r.addBreak();

        r = p.createRun();
        r.setColor("73A0DD");
        r.setFontSize(18);
        r.setBold(true);
        r.setText("Description");
        r.addBreak();

        r = p.createRun();
        r.setText("A " + currentCompany.getAge() + "-year-old firm that " + currentCompany.getDescription() + ".");
        r.addBreak();
        r.addBreak();

        r = p.createRun();
        r.setText("Business has been good over the last few years, so the head office for ");
        r = p.createRun();
        r.setBold(true);
        r.setText(currentCompany.getName());
        r = p.createRun();
        r.setText(" has been adding staff to handle the new business. A few months ago, they realized that they have now outgrown their current offices and are now planning a move into a new suburban office building. They will occupy" + currentCompany.getBuilding().getFloorCount() + "floors of the new building as they now have a need for " + currentCompany.getTotalEmployees() + "offices for the various members of the following departments:");
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Executives: " + currentCompany.getExecutives());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Human Resources: " + currentCompany.getHr());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Finance: " + currentCompany.getFinance());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Purchasing: " + currentCompany.getPurchasing());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Sales and Marketing: " + currentCompany.getSalesAndMarketing());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Security: " + currentCompany.getSecurity());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Communications: " + currentCompany.getCommunications());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• Legal: " + currentCompany.getLegal());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• IT: " + currentCompany.getIt());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• " + currentCompany.getSpecialtyOneName() + ": " + currentCompany.getSpecialtyOne());
        r.addBreak();

        r = p.createRun();
        r.setText("\t• " + currentCompany.getSpecialtyTwoName() + ": " + currentCompany.getSpecialtyTwo());
        r.addBreak();

        FileOutputStream out = new FileOutputStream(FileUtils.getJarPath() + "/companies/" + currentCompany.getName() + "/company.docx");
        document.write(out);
        out.close();
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
                        svg.setColor(Color.WHITE);
                    }
                }
                //left | bot
                if (i - 1 < 0 || floor.getSpace(i - 1, j) != type) {
                    if (!(i - 1 < 0 && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine(j * multiplier, i * multiplier, (j + 1) * multiplier, i * multiplier);
                        svg.setColor(Color.WHITE);
                    }
                }
//                // down
                if (j + 1 >= height || floor.getSpace(i, j + 1) != type) {
                    if (!(j + 1 >= height && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine((j + 1) * multiplier, i * multiplier, (j + 1) * multiplier, (i + 1) * multiplier);
                        svg.setColor(Color.WHITE);
                    }
                }
////                // up
                if (j - 1 < 0 || floor.getSpace(i, j - 1) != type) {
                    if (!(j - 1 < 0 && floor.getSpace(i, j) == -10)) {
                        svg.setColor(Color.BLACK);
                        svg.drawLine(j * multiplier, i * multiplier, j * multiplier, (i + 1) * multiplier);
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

    public static void separateRooms(Floor floor) {
        for (int i = 0; i < floor.getWidth(); i++) {
            for (int j = 0; j < floor.getLength(); j++) {
                if (floor.getSpace(i, j) == -3 || floor.getSpace(i, j) == -2 || floor.getSpace(i, j) == -10) continue;
                if (checked.contains(i + "," + j)) continue;
                int[][] room = new int[floor.getWidth()][floor.getLength()];
                buildRoom(floor, i, j, floor.getSpace(i, j), room);

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

        List<Wall> walls = getWalls(room);

        svg.setColor(Color.CYAN);
        for (Wall wall : walls) {
            int p1X = wall.getP1().getX();
            int p1Y = wall.getP1().getY();
            int p2X = wall.getP2().getX();
            int p2Y = wall.getP2().getY();

            if (wall.isVertical()) {
                // check if the line top walkway
                if (p2Y - p1Y <= 4) continue;
                try {
                    if (floor.getSpace(p1X - 1, p1Y) == -2 && floor.getSpace(p2X - 1, p2Y) == -2) {
                        int center = (p1Y + p2Y) / 2;
//                        if (floor.getSpace(p1X, center) != floor.getSpace(p1X, p1Y)) continue;
                        boolean pass = false;
                        for (int i = p1Y; i <= p2Y; i++) {
                            if (floor.getSpace(p1X, i) != floor.getSpace(p1X, p1Y)) pass = true;
                        }
                        if (pass) continue;
                        svg.setColor(Color.WHITE);
                        svg.drawLine(center * multiplier, p1X * multiplier, (center + 2) * multiplier, p2X * multiplier);
                        svg.setColor(Color.BLACK);
                        svg.drawLine(center * multiplier, p1X * multiplier, center * multiplier, (p2X + 2) * multiplier);
                        svg.drawArc((center - 2) * multiplier, (p2X - 2) * multiplier, 4 * multiplier,  4 * multiplier, 270, 90);

                    } else if (floor.getSpace(p1X + 1, p1Y) == -2 && floor.getSpace(p2X + 1, p2Y) == -2) {
                        int center = (p1Y + p2Y) / 2;
                        svg.setColor(Color.WHITE);
                        svg.drawLine(center * multiplier, (p1X + 1) * multiplier, (center + 2) * multiplier, (p2X + 1) * multiplier);
                        svg.setColor(Color.BLACK);
                        svg.drawLine(center * multiplier, (p1X + 1) * multiplier, center * multiplier, (p2X - 1) * multiplier);
                        svg.drawArc((center - 2) * multiplier, (p2X - 1) * multiplier, 4 * multiplier,  4 * multiplier, 360, 90);
                    }
                    continue;
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
            if (p2X - p1X <= 4) continue;
            try {
                if (floor.getSpace(p1X, p1Y - 1) == -2 && floor.getSpace(p2X, p2Y - 1) == -2) {
                    int center = (p1X + p2X) / 2;

//                    if (floor.getSpace(center, p1Y) != floor.getSpace(p1X, p1Y)) continue;
                    boolean pass = false;
                    for (int i = p1X; i <= p2X; i++) {
                        if (floor.getSpace(i, p1Y) != floor.getSpace(p1X, p1Y)) pass = true;
                    }
                    if (pass) continue;

                    svg.setColor(Color.WHITE);
                    svg.drawLine(p1Y * multiplier, center * multiplier, p2Y * multiplier, (center + 2) * multiplier);
                    svg.setColor(Color.BLACK);
                    svg.drawLine(p1Y * multiplier, center * multiplier, (p2Y + 2) * multiplier, center * multiplier);
                    svg.drawArc((p2Y - 2) * multiplier, (center - 2) * multiplier, 4 * multiplier,  4 * multiplier, 270, 90);
                } else if (floor.getSpace(p1X, p1Y + 1) == -2 && floor.getSpace(p2X, p2Y + 1) == -2) {
                    int center = (p1X + p2X) / 2;
                    svg.setColor(Color.WHITE);
                    svg.drawLine((p1Y + 1) * multiplier, center * multiplier, (p2Y + 1) * multiplier, (center + 2) * multiplier);
                    svg.setColor(Color.BLACK);
                    svg.drawLine((p1Y + 1) * multiplier, center * multiplier, (p2Y - 1) * multiplier, center * multiplier);
                    svg.drawArc((p2Y - 1) * multiplier, (center - 2) * multiplier, 4 * multiplier,  4 * multiplier, 180, 90);
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public static List<Wall> getWalls(int[][] room) {
        List<Position> corners = new ArrayList<>();

        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[0].length; j++) {
                if (room[i][j] == 0) {
                    continue;
                }
                if (checkIfCorner(room, i, j)) {
                    corners.add(new Position(i, j));
                }
            }
        }

//        for (Position p : corners) {
//            svg.setColor(Color.MAGENTA);
//            svg.fillOval(p.getY() * multiplier, p.getX() * multiplier, multiplier, multiplier);
//        }

        List<Wall> walls = new ArrayList<>();
        for (int i = 0; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                Position p1 = corners.get(i);
                Position p2 = corners.get(j);

                if (p1.getX() == p2.getX()) walls.add(new Wall(p1, p2, true));
                if (p1.getY() == p2.getY()) walls.add(new Wall(p1, p2, false));
            }
        }

        return walls;
    }

    private static boolean checkIfCorner(int[][] room, int x, int y) {
        int topLeft, top, topRight, left, right, botLeft, bottom, botRight;

        try {topLeft = room[x - 1][y - 1];} catch (ArrayIndexOutOfBoundsException e) {topLeft = 0;}
        try {top = room[x][y - 1];} catch (ArrayIndexOutOfBoundsException e) {top = 0;}
        try {topRight = room[x + 1][y - 1];} catch (ArrayIndexOutOfBoundsException e) {topRight = 0;}
        try {left = room[x - 1][y];} catch (ArrayIndexOutOfBoundsException e) {left = 0;}
        try {right = room[x + 1][y];} catch (ArrayIndexOutOfBoundsException e) {right = 0;}
        try {botLeft = room[x - 1][y + 1];} catch (ArrayIndexOutOfBoundsException e) {botLeft = 0;}
        try {bottom = room[x][y + 1];} catch (ArrayIndexOutOfBoundsException e) {bottom = 0;}
        try {botRight = room[x + 1][y + 1];} catch (ArrayIndexOutOfBoundsException e) {botRight = 0;}


        // check top left outer corner
        if (topLeft == 0 && top == 0 && topRight == 0 && left == 0 && right != 0 && bottom != 0 && botRight != 0) return true;
        // check top right outer corner
        if (topLeft == 0 && top == 0 && topRight == 0 && left != 0 && right == 0 && botLeft != 0 && bottom != 0) return true;
        // check bottom left outer corner
        if (topLeft == 0 && top != 0 && topRight != 0 && left == 0 && right != 0 && botLeft == 0 && bottom == 0 && botRight == 0) return true;
        // check bottom right outer corner
        if (topLeft != 0 && top != 0 && topRight == 0 && left != 0 && right == 0 && botLeft == 0 && bottom == 0 && botRight == 0) return true;

        // check top left inner corner
        if (topLeft == 0 && top != 0 && topRight != 0 && left != 0 && right != 0 && botLeft != 0 && bottom != 0 && botRight != 0) return true;
        // check top right inner corner
        if (topLeft != 0 && top != 0 && topRight == 0 && left != 0 && right != 0 && botLeft != 0 && bottom != 0 && botRight != 0) return true;
        // check bottom left inner corner
        if (topLeft != 0 && top != 0 && topRight != 0 && left != 0 && right != 0 && botLeft == 0 && bottom != 0 && botRight != 0) return true;
        // check bottom right inner corner
        if (topLeft != 0 && top != 0 && topRight != 0 && left != 0 && right != 0 && botLeft != 0 && bottom != 0 && botRight == 0) return true;

        return false;
    }

    public static void addLabels(Floor floor, int[][] room) {
        Position point = null;
        int count = 0, type = 0;

        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[0].length; j++) {
                if (room[i][j] != 0 && point == null) {
//                    Logger.success("Found start: " + i + " - " + j);
                    point = new Position(i, j);
                    type = floor.getSpace(i, j);
                }
                if (room[i][j] != 0) count++;
            }
        }

        String label = "";
        switch (type) {
            case -1 -> label = "Meeting Room";
            case 2 -> label = "HR (" + count / 80 + ")";
            case 3 -> label = "Finance (" + count / 80 + ")";
            case 4 -> label = "Purchasing (" + count / 80 + ")";
            case 5 -> label = "Sales (" + count / 80 + ")";
            case 6 -> label = "Security (" + count / 80 + ")";
            case 7 -> label = "Communications (" + count / 80 + ")";
            case 8 -> label = "Legal (" + count / 80 + ")";
            case 9 -> label = "IT (" + count / 80 + ")";
            case 10 -> label = currentCompany.getSpecialtyOneName() + " (" + count / 80 + ")";
            case 11 -> label = currentCompany.getSpecialtyTwoName() + " (" + count / 80 + ")";
            case 12 -> label = "Bathroom";
        }

        svg.setColor(Color.BLACK);
        svg.drawString(label, (point.getY() + 1) * multiplier, (point.getX() + 3) * multiplier);
    }

    @Getter
    private static class Wall {

        private final Position p1, p2;
        private final boolean vertical;

        public Wall(Position p1, Position p2, boolean vertical) {
            this.p1 = p1;
            this.p2 = p2;
            this.vertical = vertical;
        }
    }
}
