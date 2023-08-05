import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;
import tech.lpdev.components.Building;
import tech.lpdev.components.Company;
import tech.lpdev.components.Floor;
import tech.lpdev.utils.Config;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Config config = new Config("/config.yml");

    static int multiplier = 10;
    private static SVGGraphics2D svg;

    public static void main(String[] args) throws IOException {
        // Do file setup



        Company company = new Company();
        company.generate();
        Building building = company.getBuilding();
        List<Floor> floors = Building.getFloors();

        for (int i = 0; i < floors.size(); i++) {
            svg = new SVGGraphics2D(floors.get(i).getLength() * multiplier * 1.5, floors.get(i).getWidth() * multiplier * 1.5);
            drawFloor(floors.get(i));

            drawDoors(floors.get(i));

            SVGUtils.writeToSVG(new File("floor" + i + ".svg"), svg.getSVGElement());
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
    public static void drawDoors(Floor floor) {
        int width = floor.getWidth(); // floor.length();
        int height = floor.getLength(); // floor[0].length;

        Map<Integer, Integer> spaceCount = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int type = floor.getSpace(i, j);
                if (floor.getSpace(i, j) != 3) continue;

            }
        }
    }
}
