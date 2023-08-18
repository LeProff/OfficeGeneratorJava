package tech.lpdev.drawing;

import org.jfree.svg.SVGGraphics2D;

import java.awt.*;

public class Door {

    public static void drawLeft(SVGGraphics2D svg, int x, int y, int multiplier) {
        svg.setColor(Color.WHITE);
        svg.drawLine(x, y, x, y + (multiplier * 2));
        svg.setColor(Color.BLACK);
        svg.drawLine(x, y + (multiplier * 2), x + multiplier, y + (multiplier * 2));
        svg.drawArc(x + multiplier, y + (multiplier * 2), multiplier, multiplier, 180, 90);
    }

    public static void drawRight(SVGGraphics2D svg, int x, int y, int multiplier) {

    }

    public static void drawTop(SVGGraphics2D svg, int x, int y, int multiplier) {

    }

    public static void drawBottom(SVGGraphics2D svg, int x, int y, int multiplier) {

    }
}
