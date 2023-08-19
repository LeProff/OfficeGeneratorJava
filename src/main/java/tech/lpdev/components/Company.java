package tech.lpdev.components;

import lombok.Getter;
import tech.lpdev.Main;
import tech.lpdev.room.Department;
import tech.lpdev.room.Room;
import tech.lpdev.room.RoomType;
import tech.lpdev.utils.ConsoleColour;
import tech.lpdev.utils.FileUtils;
import tech.lpdev.utils.MathUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Company {

    private Building building;

    private String name, description, specialtyOneName, specialtyTwoName;
    private int age, totalEmployees, executives, hr, finance, purchasing, salesAndMarketing, security, communications, legal, it, specialtyOne, specialtyTwo;
    private List<Room> rooms;

    public Company() throws IOException {
        this.generateData();
    }

    public void generate() throws IOException {
        this.building = new Building(this);
    }

    public void generateData() throws IOException {
        this.age = MathUtil.random(8, 16);
        this.executives = 11;

        int cursor = MathUtil.random(300, 550);
        this.hr = MathUtil.random(16, cursor / 11);
        this.finance = MathUtil.random(16, cursor / 6);
        this.purchasing = MathUtil.random(13, cursor / 10);
        this.salesAndMarketing = MathUtil.random(10, cursor / 9);
        this.security = MathUtil.random(11, cursor / 13);
        this.communications = MathUtil.random(12, cursor / 15);
        this.legal = MathUtil.random(12, cursor / 11);
        this.it = MathUtil.random(14, cursor / 12);
        this.specialtyOne = MathUtil.random(10, cursor / 10);
        this.specialtyTwo = MathUtil.random(10, cursor / 10);
        this.totalEmployees = this.executives + this.hr + this.finance +
                this.purchasing + this.salesAndMarketing + this.security +
                this.communications + this.legal + this.it + this.specialtyOne + this.specialtyTwo;

//        BufferedReader file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/companynames.txt")));
        BufferedReader file = new BufferedReader(new FileReader(FileUtils.getFileFromResource("companynames.txt")));

        List<String> lines = file.lines().toList();
        this.name = lines.get(MathUtil.random(0, lines.size() - 1));
        file.close();

//        file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/companytypes.txt")));
        file = new BufferedReader(new FileReader(FileUtils.getFileFromResource("companytypes.txt")));

        lines = file.lines().toList();
        String[] temp = lines.get(MathUtil.random(0, lines.size() - 1)).split(",");
        this.name += " " + temp[0];
        this.description = temp[1];
        this.specialtyOneName = temp[2];
        this.specialtyTwoName = temp[3];
        file.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColour.DARK_BLUE + "Name: " + ConsoleColour.GREEN + this.name + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Description: " + ConsoleColour.GREEN + this.description + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Age: " + ConsoleColour.GREEN + this.age + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Total Employees: " + ConsoleColour.GREEN + this.totalEmployees + "\n");

        sb.append(ConsoleColour.DARK_BLUE + "Floors: " + ConsoleColour.GREEN + this.building.getFloorCount() + "\n");

        sb.append(ConsoleColour.DARK_BLUE + "Executives: " + ConsoleColour.GREEN + this.executives + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "HR: " + ConsoleColour.GREEN + this.hr + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Finance: " + ConsoleColour.GREEN + this.finance + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Purchasing: " + ConsoleColour.GREEN + this.purchasing + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Sales and Marketing: " + ConsoleColour.GREEN + this.salesAndMarketing + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Security: " + ConsoleColour.GREEN + this.security + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Communications: " + ConsoleColour.GREEN + this.communications + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "Legal: " + ConsoleColour.GREEN + this.legal + "\n");
        sb.append(ConsoleColour.DARK_BLUE + "IT: " + ConsoleColour.GREEN + this.it + "\n");
        sb.append(ConsoleColour.DARK_BLUE + this.specialtyOneName + ": " + ConsoleColour.GREEN + this.specialtyOne + "\n");
        sb.append(ConsoleColour.DARK_BLUE + this.specialtyTwoName + ": " + ConsoleColour.GREEN + this.specialtyTwo + "\n");
        sb.append(ConsoleColour.RESET);
        return sb.toString();
    }

    public List<Room> generateRooms() {
        if (this.rooms != null) return this.rooms;
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(roomsPerDept(this.hr, Department.HR));
        rooms.addAll(roomsPerDept(this.finance, Department.FINANCE));
        rooms.addAll(roomsPerDept(this.purchasing, Department.PURCHASING));
        rooms.addAll(roomsPerDept(this.salesAndMarketing, Department.SALES_AND_MARKETING));
        rooms.addAll(roomsPerDept(this.security, Department.SECURITY));
        rooms.addAll(roomsPerDept(this.communications, Department.COMMUNICATIONS));
        rooms.addAll(roomsPerDept(this.legal, Department.LEGAL));
        rooms.addAll(roomsPerDept(this.it, Department.IT));
        rooms.addAll(roomsPerDept(this.specialtyOne, Department.SPECIALTY_ONE));
        rooms.addAll(roomsPerDept(this.specialtyTwo, Department.SPECIALTY_TWO));
//        rooms.addAll(generateMeetingRooms());
        rooms.add(new Room(RoomType.EXECUTIVE_OFFICE, Department.EXECUTIVE));
        return rooms;
    }

    private List<Room> roomsPerDept(int num, Department dept) {
        int peoplePerRoom = Main.getConfig().getAsInteger("peoplePerOffice");
        int overflowMin = Main.getConfig().getAsInteger("overflowMin");

        int roomCount = num / peoplePerRoom;
        if (num % overflowMin > 0) roomCount++;
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < roomCount; i++) {
            rooms.add(new Room(RoomType.OFFICE, dept));
        }
        rooms.add(new Room(RoomType.EXECUTIVE_OFFICE, dept));
        return rooms;
    }
}
