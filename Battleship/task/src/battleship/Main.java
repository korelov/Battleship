package battleship;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public Scanner scanner = new Scanner(System.in);
    public String name;
    protected String[][] field;
    private String position;
    private String CoordinatesOne;
    private String CoordinatesTwo;

    private String rowChar1;
    private String columnsDigit1;
    private String rowChar2;
    private String columnsDigit2;

    private int row;
    private int columns;

    public int getRow() {
        return row;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumns() {
        return columns;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }

    static String vertical = "ABCDEFGHIJ";

    public String getCoordinatesOne() {
        return CoordinatesOne;
    }

    public void setCoordinatesOne(String coordinatesOne) {
        this.CoordinatesOne = coordinatesOne;
    }

    public String getCoordinatesTwo() {
        return CoordinatesTwo;
    }

    public void setCoordinatesTwo(String coordinatesTwo) {
        this.CoordinatesTwo = coordinatesTwo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Main() {
    }

    public void makeField() {
        field = new String[10][10];
        for (String[] strings : field) {
            Arrays.fill(strings, "~");
        }
    }

    public void printField() {
        int c = 65;
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (String[] strings : field) {
            System.out.print((char) c++ + " ");
            for (int j = 0; j < strings.length; j++) {
                if (j < field.length - 1) {
                    System.out.print(strings[j] + " ");
                } else {
                    System.out.print(strings[j]);
                }
            }
            System.out.println();
        }
    }

    public void printFogOfWarField() {
        int c = 65;
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (String[] strings : field) {
            System.out.print((char) c++ + " ");
            for (int j = 0; j < strings.length; j++) {
                if (j < field.length - 1) {
                    if (strings[j].equals("O")) {
                        System.out.print("~" + " ");
                    } else {
                        System.out.print(strings[j] + " ");
                    }
                } else {
                    if (strings[j].equals("O")) {
                        System.out.print("~");
                    } else
                        System.out.print(strings[j]);
                }
            }
            System.out.println();
        }
    }

    public void checkCoordinates() {

        setCoordinatesOne(scanner.next());
        setCoordinatesTwo(scanner.next());

        rowChar1 = getCoordinatesOne().substring(0, 1);
        columnsDigit1 = getCoordinatesOne().substring(1);
        rowChar2 = getCoordinatesTwo().substring(0, 1);
        columnsDigit2 = getCoordinatesTwo().substring(1);

        if (getCoordinatesOne().length() < 2 || getCoordinatesTwo().length() < 2 && getCoordinatesOne().length() > 3 ||
                getCoordinatesTwo().length() > 3) {
            System.out.println("coordinate format A-J 1-10");
            checkCoordinates();
        }
        if (rowChar1.charAt(0) < 'A' || rowChar1.charAt(0) > 'J' || rowChar2.charAt(0) < 'A' ||
                rowChar2.charAt(0) > 'J' || Integer.parseInt(columnsDigit1) < 1 || Integer.parseInt(columnsDigit1) > 10
                || Integer.parseInt(columnsDigit2) < 1 || Integer.parseInt(columnsDigit2) > 10) {
            System.out.println("coordinate format A-J 1-10");
            checkCoordinates();
        }
        if (rowChar1.charAt(0) == rowChar2.charAt(0) && Integer.parseInt(columnsDigit1) != Integer.parseInt(columnsDigit2)) {
            setRow(vertical.indexOf(rowChar1.charAt(0)));
            setColumns(Math.min(Integer.parseInt(columnsDigit1), Integer.parseInt(columnsDigit2)) - 1);
            setPosition("Horizontal");
        } else if (rowChar1.charAt(0) != rowChar2.charAt(0) &&
                Integer.parseInt(columnsDigit1) == Integer.parseInt(columnsDigit2)) {
            setRow(Math.min(vertical.indexOf(rowChar1), vertical.indexOf(rowChar2)));
            setColumns(Integer.parseInt(columnsDigit1) - 1);
            setPosition("Vertical");
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            checkCoordinates();
        }
    }

    public void shipOnField(Ship ship) {

        System.out.printf("Enter the coordinates of the %s (%d cells):", ship.getName(), ship.getRow());
        checkCoordinates();

        if (getPosition().equals("Horizontal")) {
            if (Math.abs(Integer.parseInt(columnsDigit1) - Integer.parseInt(columnsDigit2)) + 1 != ship.getRow()) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName());
                shipOnField(ship);
            } else {
                if (isAvailable(ship, position)) {
                    for (int i = 0; i < ship.getRow(); i++) {
                        field[getRow()][getColumns() + i] = "O";
                    }
                    printField();
                } else {
                    System.out.println(("Error! You placed it too close to another one."));
                    shipOnField(ship);
                }
            }
        } else {
            if (Math.abs(vertical.indexOf(rowChar1) - vertical.indexOf(rowChar2)) + 1 != ship.getRow()) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName());
                shipOnField(ship);
            } else {
                if (isAvailable(ship, position)) {
                    for (int i = 0; i < ship.getRow(); i++) {
                        field[getRow() + i][getColumns()] = "O";
                    }
                    printField();
                } else {
                    System.out.println(("Error! You placed it too close to another one."));
                    shipOnField(ship);
                }
            }
        }
    }

    public void checkShot(String[][] field) {

        if (field[getRow()][getColumns()].equals("O") || field[getRow()][getColumns()].equals("X")) {
            field[getRow()][getColumns()] = "X";
            if (!sankShip(field)) {
                if (checkGameEmd(field)) {
                    printFogOfWarField();
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else {
                    System.out.println("You sank a ship! Specify a new target:");
                }
            } else {
                System.out.println("You hit a ship! Try again:");
            }
        }
        if (field[getRow()][getColumns()].equals("~")) {
            field[getRow()][getColumns()] = "M";
            System.out.println("You missed. Try again:");
        }
    }

    public boolean checkGameEmd(String[][] field) {
        boolean result = true;
        for (String[] strings : field) {
            for (String string : strings) {
                if (string.equals("O")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public void shotCoordinate() {

        setCoordinatesOne(scanner.next());

        rowChar1 = getCoordinatesOne().substring(0, 1);
        columnsDigit1 = getCoordinatesOne().substring(1);
        setRow(vertical.indexOf(rowChar1.charAt(0)));
        setColumns(Integer.parseInt(columnsDigit1) - 1);

        if (rowChar1.charAt(0) < 'A' || rowChar1.charAt(0) > 'J' || Integer.parseInt(columnsDigit1) < 1 ||
                Integer.parseInt(columnsDigit1) > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            shotCoordinate();
        }
    }

    public boolean isAvailable(Ship ship, String position) {

        int x = getRow();
        int y = getColumns();
        int count = ship.getRow();
        while (count != 0) {
            for (int i = 0; i < ship.getRow(); i++) {
                int xi = 0;
                int yi = 0;
                if (position.equals("Horizontal")) {
                    yi = i;
                } else {
                    xi = i;
                }
                if (x + 1 + xi < field.length && x + 1 + xi >= 0) {
                    if (field[x + 1 + xi][y + yi].equals("O")) {
                        return false;
                    }
                }
                if (x - 1 + xi < field.length && x - 1 + xi >= 0) {
                    if (field[x - 1 + xi][y + yi].equals("O")) {
                        return false;
                    }
                }
                if (y + 1 + yi < field.length && y + 1 + yi >= 0) {
                    if (field[x + xi][y + 1 + yi].equals("O")) {
                        return false;
                    }
                }
                if (y - 1 + yi < field.length && y - 1 + yi >= 0) {
                    if (field[x + xi][y - 1 + yi].equals("O")) {
                        return false;
                    }
                }
            }
            count--;
        }
        return true;
    }

    public boolean sankShip(String[][] field) {
        boolean result = false;

        if (getRow() == 0 && getColumns() == 0) {
            result = field[getRow() + 1][getColumns()].equals("O") || field[getRow()][getColumns() + 1].equals("O");
        }
        if (getRow() == 0 && getColumns() > 0 && getColumns() < 9) {
            result = field[getRow()][getColumns() - 1].equals("O") || field[getRow()][getColumns() + 1].equals("O") ||
                    field[getRow() + 1][getColumns()].equals("O");
        }
        if (getRow() == 0 && getColumns() == 9) {
            result = field[getRow()][getColumns() - 1].equals("O") || field[getRow() + 1][getColumns()].equals("O");
        }
        if (getRow() > 0 && getRow() < 9 && getColumns() == 0) {
            result = field[getRow() - 1][getColumns()].equals("O") || field[getRow() + 1][getColumns()].equals("O") ||
                    field[getRow()][getColumns() + 1].equals("O");
        }
        if (getRow() > 0 && getRow() < 9 && getColumns() > 0 && getColumns() < 9) {
            result = field[getRow() + 1][getColumns()].equals("O") || field[getRow() - 1][getColumns()].equals("O") ||
                    field[getRow()][getColumns() + 1].equals("O") || field[getRow()][getColumns() - 1].equals("O");
        }
        if (getRow() > 0 && getRow() < 9 && getColumns() == 9) {
            result = field[getRow() - 1][getColumns()].equals("O") || field[getRow() + 1][getColumns()].equals("O") ||
                    field[getRow()][getColumns() - 1].equals("O");
        }
        if (getRow() == 9 && getColumns() == 0) {
            result = field[getRow() - 1][getColumns()].equals("O") || field[getRow()][getColumns() + 1].equals("O");
        }
        if (getRow() == 9 && getColumns() > 0 && getColumns() < 9) {
            result = field[getRow() - 1][getColumns()].equals("O") || field[getRow()][getColumns() - 1].equals("O") ||
                    field[getRow()][getColumns() + 1].equals("O");
        }
        if (getRow() == 9 && getColumns() == 9) {
            result = field[getRow() - 1][getColumns()].equals("O") || field[getRow()][getColumns() - 1].equals("O");
        }
        return result;
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

