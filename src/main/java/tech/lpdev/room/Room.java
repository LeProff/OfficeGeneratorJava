package tech.lpdev;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Room {

    private final RoomType type;
    private final Department department;
    @Setter
    // 0 = north, 1 = east, 2 = south, 3 = west
    private int doorDirection;
    @Setter
    private boolean horizontal;
    @Setter
    private int x, y;

    public Room(RoomType type, Department department) {
        this.type = type;
        this.department = department;
        this.doorDirection = -1;
        this.horizontal = false;
    }

    public int getWidth() {
        return horizontal ? type.getLength() : type.getWidth();
    }

    public int getLength() {
        return horizontal ? type.getWidth() : type.getLength();
    }
}
