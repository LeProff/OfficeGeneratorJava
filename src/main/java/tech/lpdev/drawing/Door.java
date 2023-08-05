package tech.lpdev.drawing;

import org.jfree.svg.SVGGraphics2D;

import java.awt.*;

public class Door {

    public static void drawLeft(SVGGraphics2D svg, int x, int y, int multiplier) {
        svg.setColor(Color.WHITE);
        svg.drawLine(x * multiplier, y * multiplier, (x + 3) * multiplier, y * multiplier);
        svg.setColor(Color.BLACK);
        svg.drawLine(x * multiplier, y * multiplier, (x + 3) * multiplier, (y + 3) * multiplier);
    }

    public static void drawRight(SVGGraphics2D svg, int x, int y, int multiplier) {
        svg.setColor(Color.WHITE);
        svg.drawLine(x * multiplier, y * multiplier, (x - 3) * multiplier, y * multiplier);
        svg.setColor(Color.BLACK);
        svg.drawLine(x * multiplier, y * multiplier, (x - 3) * multiplier, (y + 3) * multiplier);
    }

    public static void drawTop(SVGGraphics2D svg, int x, int y, int multiplier) {
        svg.setColor(Color.WHITE);
        svg.drawLine(x * multiplier, y * multiplier, x * multiplier, (y + 3) * multiplier);
        svg.setColor(Color.BLACK);
        svg.drawLine(x * multiplier, y * multiplier, (x + 3) * multiplier, (y + 3) * multiplier);
    }

    public static void drawBottom(SVGGraphics2D svg, int x, int y, int multiplier) {
        svg.setColor(Color.WHITE);
        svg.drawLine(x * multiplier, y * multiplier, x * multiplier, (y - 3) * multiplier);
        svg.setColor(Color.BLACK);
        svg.drawLine(x * multiplier, y * multiplier, (x + 3) * multiplier, (y - 3) * multiplier);
    }
}
