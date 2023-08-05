package tech.lpdev.components;

import lombok.Getter;
import tech.lpdev.Position;
import tech.lpdev.envelop.EnvelopManager;
import tech.lpdev.envelop.Envelope;
import tech.lpdev.room.Department;
import tech.lpdev.room.Room;
import tech.lpdev.room.RoomType;
import tech.lpdev.utils.ListUtils;
import tech.lpdev.utils.Logger;
import tech.lpdev.utils.MathUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Building {

    // TODO: Move main class functionality here and use main class as the batch generation class
    private static List<Room> rooms;
    @Getter
    private static List<Room> usedRooms;
    @Getter
    private static List<Floor> floors;

    public Building(Company company) throws IOException {
        rooms = ListUtils.duplicateList(company.generateRooms());
        usedRooms = new ArrayList<>();
        floors = new ArrayList<>();

        Envelope e = EnvelopManager.getRandomEnvelop();
        while (rooms.size() != 0) {
            // TODO: Pick at random either inner or outer
//            e.display();
            Floor floor = null;
            int choice = MathUtil.random(0, 1);
            if (choice == 0) floor = new Floor(e.getOuter());
            else floor = new Floor(e.getInner());

            fillFloor(floor);
            boardRoomsPlaced = 0;

            floors.add(floor);
        }

        // Draws Floor

//        System.out.println(floors.size());
//        for (Floor floor : floors) {
//            System.out.println(floor);
//            System.out.println("\n\n");
//        }

        // TODO: make file ouput
    }

    private static void fillFloor(Floor floor) {
        // Add boardrooms
        // place all the rooms
        placeRooms(floor);
        // Double check to fill all spaces
        placeRooms(floor);
//        System.out.println(floor);
        //Stretch the rooms down to fill all spaces
        fillEmptySpace(floor);
        fillEmptySpace(floor);
    }


    private static int boardRoomsPlaced = 0;
    private static void placeRooms(Floor floor) {

        double boardRoomChanceMod = 0.0;
        int counter = 0;

        while (true) {
            Room room = null;

            if (counter >= rooms.size()) {
                counter = 0;
                break;
            }

            room = rooms.get(counter);

//            if (rooms.isEmpty()) {
//                room = new Room(RoomType.OFFICE, Department.UN_ASSIGNED);
//            } else {
//                room = rooms.get(counter);
//            }





            if (Math.random() <= 0.1 + boardRoomChanceMod && boardRoomsPlaced < 2) {
                room = new Room(RoomType.BOARD_ROOM, Department.NONE);
                boardRoomsPlaced++;
                boardRoomChanceMod += 0.05;
            }
            counter++;
//            Room room = rooms.get(MathUtil.random(0, rooms.size() - 1));

            Position position = findOpenSpace(floor, room);
            if (position != null) {
                floor.drawRoom(room, position.getX(), position.getY());
                room.setX(position.getX());
                room.setY(position.getY());
                usedRooms.add(room);
                rooms.remove(room);
            } else {
                break;
            }
        }
    }

    //    private static List<String> checked = new ArrayList<>();
    private static Position findOpenSpace(Floor floor, Room room) {
        int width = floor.getWidth(), length = floor.getLength();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (!floor.checkSpace(room, i, j)) continue;
                if (floor.checkSpace(room, i, j)) return new Position(i, j);
            }
        }
        return null;
    }

    private static void fillEmptySpace(Floor floor) {

        for (int i = 0; i < floor.getWidth(); i++) {
            for (int j = 0; j < floor.getLength(); j++) {
                if (floor.getSpace(i, j) != 0 || j - 1 < 0) continue;
                if (floor.getSpace(i, j) == 12 || floor.getSpace(i, j) == 13 || floor.getSpace(i, j) == -1) continue;
                int spaceLeft = floor.getSpace(i, j - 1);
                if (spaceLeft >= -1 && spaceLeft <= 11) {
                    floor.setSpace(i, j, spaceLeft);
                }
            }
        }

        for (int i = 0; i < floor.getWidth(); i++) {
            for (int j = 0; j < floor.getLength(); j++) {
                if (floor.getSpace(i, j) != 0 || i - 1 < 0) continue;
                if (floor.getSpace(i, j) == 12 || floor.getSpace(i, j) == 13 || floor.getSpace(i, j) == -1) continue;
                int spaceAbove = floor.getSpace(i - 1, j);
                if (spaceAbove >= -1 && spaceAbove <= 11) {
                    floor.setSpace(i, j, spaceAbove);
                }
            }
        }

        for (int i = floor.getWidth() - 1; i >= 0; i--) {
            for (int j = floor.getLength() - 1; j >= 0; j--) {
                if (floor.getSpace(i, j) != 0 || j + 1 >= floor.getLength()) continue;
                if (floor.getSpace(i, j) == 12 || floor.getSpace(i, j) == 13 || floor.getSpace(i, j) == -1) continue;
                int spaceRight = floor.getSpace(i, j + 1);
                if (spaceRight >= -1 && spaceRight <= 11) {
                    floor.setSpace(i, j, spaceRight);
                }
            }
        }

        for (int i = floor.getWidth() - 1; i >= 0; i--) {
            for (int j = floor.getLength() - 1; j >= 0; j--) {
                if (floor.getSpace(i, j) != 0 || i + 1 >= floor.getWidth()) continue;
                if (floor.getSpace(i, j) == 12 || floor.getSpace(i, j) == 13 || floor.getSpace(i, j) == -1) continue;
                int spaceBelow = floor.getSpace(i + 1, j);
                if (spaceBelow >= -1 && spaceBelow <= 11) {
                    floor.setSpace(i, j, spaceBelow);
                }
            }
        }
    }

    public int getFloorCount() {
        return floors.size();
    }
}
