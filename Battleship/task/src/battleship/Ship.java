package battleship;

public enum Ship {

    AircraftCarrier("Aircraft Carrier", 5),
    Battleship("Battleship", 4),
    Submarine("Submarine", 3),
    Cruiser("Cruiser", 3),
    Destroyer("Destroyer", 2);

    private final int row;
    private final String name;

    Ship(String name, int row) {
        this.name = name;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public String getName() {
        return name;
    }
}
