package tech.lpdev.room;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomType {

    OFFICE(8, 10),
    EXECUTIVE_OFFICE(12, 15),
    MEETING_ROOM(10, 20),
    BOARD_ROOM(12, 24),
    BATHROOM(3, 6);

    private final int width, length;
}
