package tech.lpdev;

import lombok.Getter;

@Getter
public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position position) {
        return position.getX() == this.x && position.getY() == this.y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
