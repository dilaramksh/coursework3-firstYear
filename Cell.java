import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public abstract class Cell {
    protected Random rand = Randomizer.getRandom();
    
    private boolean alive;    
    private boolean nextAlive; // The state of the cell in the next iteration
    private Field field;
    private Location location;
    private Color color = Color.WHITE;
    
    //these instance variables can be accesessed by subclasses to find out the count of living neighbouring cell types
    protected List<Cell> neighbours; //all living neighbouring cells in a list
    protected long mycoplasmaCount; //count of all living neighbouring mycoplasma cells
    protected long amoebaCount; //count of all living neighbouring amoeba cells   
    protected long ureaplasmaCount; //count of all living neighbouring ureaplasma cells
    protected long brucellaCount; //count of all living neighbouring brucells cells
    protected long aliveNeighboursCount; //count of all living neighbouring cells

    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        setLocation(location);
        setColor(col);
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();
    
    /**
     * Check whether the cell is alive or not.
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    public void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Return the current generation
     * @return The generation (interger)
     */
    protected int getGeneration() {
        return Simulator.getGeneration();
    }
      
    /**
     * Updates the neighbouring cell count of all living cells
     */
    protected void updateNeighbourCount() {
        neighbours = getField().getLivingNeighbours(getLocation());
        mycoplasmaCount = neighbours.stream().filter(c -> c instanceof Mycoplasma).count();
        amoebaCount = neighbours.stream().filter(c -> c instanceof Amoeba).count();
        ureaplasmaCount = neighbours.stream().filter(c -> c instanceof Ureaplasma).count();
        brucellaCount = neighbours.stream().filter(c -> c instanceof Brucella).count();
        aliveNeighboursCount = neighbours.size();
    } 

}
