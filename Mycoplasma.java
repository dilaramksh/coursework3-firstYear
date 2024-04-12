import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Mycoplasma extends Cell {
    private static final int REPRODUCTION_THRESHOLD = 2; // Threshold of Brucella for reproduction
    
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This method shows Mycoplasma's behaviour.
     */
     public void act() {
        updateNeighbourCount(); // updates count of living neighbour cells
        
        // RULE 1 and 4: Cell dies due to underpopulation or overpopulation
        if (aliveNeighboursCount < 2 || aliveNeighboursCount > 3) {
            setNextState(false);
        }
        // RULE 2: Cell lives on to the next generation
        else if (aliveNeighboursCount == 2 || aliveNeighboursCount == 3) {
            setNextState(isAlive());
        }
        // RULE 3: Dead cell comes to life due to reproduction
        if (!isAlive() && aliveNeighboursCount == 3) {
            setNextState(true);
        }
        
        // Parasite relation with brucella
        if (isAlive()) {
            // Increase survival chances in the presence of Brucella
            if (brucellaCount > 0) {
                setNextState(true); // Mycoplasma benefits from Brucella
            } else {
                // Applies default logic if no Brucella are present
                setNextState(aliveNeighboursCount == 2 || aliveNeighboursCount == 3);
            }
        } else {
            // Boosted reproduction in the presence of multiple Brucella
            if (brucellaCount >= REPRODUCTION_THRESHOLD) {
                setNextState(true); // Conditions are favorable for Mycoplasma to come to life
            }
        }
    }
}
