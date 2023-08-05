package tech.lpdev;

import tech.lpdev.components.Company;
import tech.lpdev.room.Room;
import tech.lpdev.utils.Logger;
import tech.lpdev.utils.MathUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfficeGenerator {

    // Keys: 0=open, 1=blocked, 2=stairs/elevator, 3=hallway

    List<int[][]> floors = new ArrayList<>();

    private static int counter = 0;
    public static void main(String[] args) throws IOException {
        Company company = new Company();
        System.out.println(company);

        List<Room> rooms = company.generateRooms();

        // TODO: Generate floors using random rooms from the list
        final int width = 60, length = 60;
        Floor floor = new Floor(width, length);

        int x = 0 , y = 0;
        Room room = rooms.get(MathUtil.random(0, rooms.size() - 1));
        while (placeRoomAroundPoint(floor, room, x, y)) {
            if (counter > 50) break;
            x = room.getX();
            y = room.getY();
            room = rooms.get(MathUtil.random(0, rooms.size() - 1));
        }
        System.out.println(floor);
    }

    private static boolean placeRoomAroundPoint(Floor floor, Room room, int x, int y) {
        Position position = dfsFindSuitablePosition(floor, room, x, y, new ArrayList<>());
        if (position != null) {
            floor.drawRoom(room, position.getX(), position.getY());
            counter = 0;
            Logger.success("Placed room at " + position.getX() + ", " + position.getY());
            System.out.println(floor);
            return true;
        }
        return false;
    }

    // TODO: Use breadth-first search to find a suitable point

//    private static ArrayList<String> visited = new ArrayList<>();
    private static Position dfsFindSuitablePosition(Floor floor, Room room, int x, int y, ArrayList<String> visited) {
        if (x < 0 || y < 0 || x + room.getWidth() > floor.getWidth() || y + room.getLength() > floor.getLength()) return null;
        if (visited.contains(x + ":" + y)) return null;
        visited.add(x + ":" + y);
        Logger.log("Checking " + x + ", " + y);
        if (floor.checkSpace(room, x, y)) return new Position(x, y);

        if (floor.checkSpace(room, x, y)) return new Position(x, y);
        room.setHorizontal(!room.isHorizontal());
        if (floor.checkSpace(room, x, y)) return new Position(x, y);
        room.setHorizontal(!room.isHorizontal());

        Position pos = dfsFindSuitablePosition(floor, room, x + 1, y, visited); // down
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x + 1, y + 1, visited); // down right
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x, y + 1, visited); // right
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x - 1, y + 1, visited); // up right
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x - 1, y, visited); // up
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x - 1, y - 1, visited); // up left
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x, y - 1, visited); // left
        if (pos != null) return pos;
        pos = dfsFindSuitablePosition(floor, room, x + 1, y - 1, visited); // down left
        if (pos != null) return pos;

        counter++;
        return null;
    }
}


