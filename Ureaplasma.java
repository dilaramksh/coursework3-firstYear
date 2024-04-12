import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Ureaplasma cells behaviour depends on the time that has elapsed or the time changed
 *
 * @author Dilara Mukasheva && Lucia Garces
 * @version (a version number or a date)
 */
public class Ureaplasma extends Cell
{
    
    /**
     * Create a new Ureaplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Ureaplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
     * This method shows Ureaplasma's behaviour.
     */
    
    
    public void act() {
        updateNeighbourCount(); // updates count of living neighbour cells
        int generation = getGeneration();
        double probability = rand.nextDouble();
        
        //if there is a disease and two mycoplasma cells, the cell will become infected with the disease as well
       
        // Checks for the periodic event where Ureaplasma cells die out
        if (generation % 15 == 0 && getColor().equals(Color.GREY)) {
            setNextState(false);
            return; // Exits early as the cell is marked to die
        } else if (isAlive()){
            if (generation <= 40 && probability <= 0.35 && aliveNeighboursCount == 5) {
                // Ureaplasma can turn into a super before the 40 generation with a 35% and if it only has 5 neighbours
                setNextState(isAlive());
                setColor(Color.BLACK);
            } else if (isSuperUreaplasma(probability)) { //check if super ureaplasma and is true with a 34%
                // Special behavior for SUPER Ureaplasma cells
                actSuperUreaplasma(probability);
            } else if (amoebaCount > 0) {// Mutualistic relationship with Amoeba increases survival chances of normal ureaplasma cells
                surviveDueMutualism();
            } else{
                // Applies default survival logic
                actDefaultSurvivalLogic(aliveNeighboursCount);
            }
        } else{
            // Attempts resurrection based on specific conditions
            attemptResurrection(probability, generation);
        }
    }

    /**
     * Ensures survival of the Ureaplasma due to mutualistic relationships with Amoeba.
     * This method is called when Ureaplasma benefits from the presence of neighboring Amoeba cells.
     */
    private void surviveDueMutualism() {
        setNextState(true); // Ureaplasma benefits from the presence of Amoeba
    }

    /**
     * Checks if the current Ureaplasma cell is in a SUPER state, which is determined by its color (BLACK)
     * @param The probability of the cell SUPER behavior.
     * @return true if the cell is a SUPER Ureaplasma.
     */
    private boolean isSuperUreaplasma(double probability) {
        return getColor().equals(Color.BLACK) && probability <= 0.34;
    }

    /**
     * Defines the behavior of SUPER Ureaplasma cells, wiping out neighboring cells.
     * @param The probability that triggers the SUPER Ureaplasma's action.
     */
    private void actSuperUreaplasma(double probability) {
        getField().wipeOutNeighbours(getLocation()); // Kills neighboring cells
        setNextState(true); // SUPER Ureaplasma survives
    }

    /**
     * Attempts to resurrect a dead Ureaplasma cell based on a probability.
     * @param The probability of resurrection.
     * @param The current generation, used to determine if resurrection conditions are met.
     */
    private void attemptResurrection(double probability, int generation) {
        if (probability <= 0.39) {
            setNextState(true); // Dead Ureaplasma has a chance to come back to life
        }
    }

    /**
     * Applies the default survival logic for Ureaplasma cells, based on the number of neighbors.
     * @param neighboursSize The number of neighboring cells.
     */
    private void actDefaultSurvivalLogic(long neighboursSize) {
        // Default logic for normal Ureaplasma survival without the influence of Amoeba
        if (neighboursSize == 2 || neighboursSize == 3) {
            setNextState(true); // Survives with 2 or 3 neighbours
        } else {
            setNextState(false); // Dies in other cases
        }
    }
}
