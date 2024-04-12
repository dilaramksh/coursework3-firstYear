import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Brucella lives by a non-deterministic way of life.
 *
 * @author Dilara Mukasheva && Lucia Garces
 * @version (a version number or a date)
 */
public class Brucella extends Cell
{
    private static final int MIN_SURVIVAL_NEIGHBOURS = 3; // Minimum neighbors to survive
    private static final int MAX_SURVIVAL_NEIGHBOURS = 3; // Maximum neighbors to survive without overcrowding
    private static final int REPRODUCTION_NEIGHBOURS = 3; // Neighbors required for reproduction
    private boolean isResilient = false; // New state to track Brucella's temporary resilience

    /**
     * Create a new Brucella.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Brucella(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
     * This method shows Brucella's non-deterministic behaviour.
     */
    public void act() {
        updateNeighbourCount(); // updates count of living neighbour cells
        int generation = getGeneration(); //stores which generation it is
        double probabilityOfRule = rand.nextDouble(); //probabliity for the rule to execute
    
        // Non-deterministic rules
        if (probabilityOfRule <= 0.13) { 
            // Rule 1: Flip state with a 13% chance
            setNextState(!isAlive());
        } else if (probabilityOfRule <= 0.48) {
            // Rule 2: Affect others in the row with a 35% chance
            affectRowBasedOnCondition();
        } else if (probabilityOfRule <= 0.73) {
            // Rule 3: every 10 generations brucella cells gets a 'immunity' pass from the parasite relation with a 25% chance
            if (generation % 10 == 0){
                isResilient = true;
            }
        } else {
            // Maintain current state with the remaining 27% chance
            setNextState(isAlive());
        }
        
        //parasite relation with mycoplasma
        if (isAlive()) {
            if (mycoplasmaCount > 0) { //if mycoplasma cells are present
                if (isResilient == false){ //if doesn't has resilience
                    // Brucella has a lower chance of survival in the presence of Mycoplasma
                    // It now requires exactly 3 neighbors to survive, instead of a range
                    setNextState(aliveNeighboursCount == REPRODUCTION_NEIGHBOURS); //if isn't immune to the parasitic relationship then will determine the next state
                } else{
                    isResilient = false; //resets the resilience to false as it loses its immunity after it encounters the parasite relation once
                }
            } else {
                // Without Mycoplasma, Brucella can survive with 2 or 3 neighbors
                setNextState(aliveNeighboursCount >= MIN_SURVIVAL_NEIGHBOURS && aliveNeighboursCount <= MAX_SURVIVAL_NEIGHBOURS);
            }
        } else {
            if (mycoplasmaCount > 0 ) { //if mycoplasma cells are present
                if (isResilient == false){ //if isn't immune to the parasitic relationship then will die
                    setNextState(false);
                } else{
                    // Brucella cannot reproduce if Mycoplasma is present
                    isResilient = false; //resets the pass to false as it loses its immunity after it encounters the parasite relation once
                }
            } else {
                // Brucella can reproduce if there are exactly 3 neighbors and no Mycoplasma
                setNextState(aliveNeighboursCount == REPRODUCTION_NEIGHBOURS);
            }
        }
        
    }
    
    
    /**
     * Triggers response in a row if Brucella exceeds a third of the row's cells.
     * All non-Brucella cells in the row are eliminated, simulating Brucella's impact.
     */
    private void affectRowBasedOnCondition() {
        int rowOfCell = getLocation().getRow(); // gets the row where the ureaplasma cell is located 
        int thirdOfGridWidth = SimulatorView.GRID_WIDTH / 3;
        //checks how many brucella cells are in the row && compares if its more than third of the row cells
        if (getField().numberBrucellaInRow(rowOfCell) > thirdOfGridWidth){
            getField().wipeOutRowofCell(rowOfCell); //all cells in that row (except brucella cells) die
        }
    }
}
