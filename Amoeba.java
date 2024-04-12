import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Amoeba cells are the life form that changes colour due to its behaviour rules
 *
 * @author Dilara Mukasheva && Lucia Garces
 * @version (a version number or a date)
 */
public class Amoeba extends Cell
{
    /**
     * Create a new Amoeba.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Amoeba(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
     * This method shows Amoeba's behaviour.
     */
    public void act() {
       updateNeighbourCount(); // updates count of living neighbour cells
       //setColor(Color.GREEN); //resets the colour back to the initial GREEN colour
       
        //the set of rules that Amoeba cells have to follow when evolving to the next generation 
        if(isAlive()){ //only applies to when the amoeba cell is alive
            // Mutualistic relationship: Amoeba benefits from Ureaplasma
            if (ureaplasmaCount > 0) {
                // Amoeba has a higher chance of survival or reproduces
                // when at least 1 Ureaplasma neighbor is present.
                setNextState(isAlive()); // Amoeba survives due to mutualistic relationship
            } 
        
            if(mycoplasmaCount == 3 && ureaplasmaCount == 1){
                setNextState(isAlive()); //lives to see the next generation
                setColor(Color.BLUE); //turns BLUE
            }
            //amoeba cell turns red in contact with exactly two brucella cells
            else if(brucellaCount == 2){
                setNextState(isAlive()); //lives to see the next generation
                setColor(Color.RED); //turns RED
            }
            //amoeba cell gets to live on another generation if 3 or 4 living neighbours
            else if(aliveNeighboursCount == 3 || aliveNeighboursCount == 4){
                setNextState(isAlive()); //lives to see the next generation
            }
            //amoeba cell dies if it is surrounded by 5 or more cells of any kind 
            else if (aliveNeighboursCount >= 5){
                setNextState(false); //dies
            }
        }
        else{ //only applies to when the amoeba cell is currently dead 
            if (aliveNeighboursCount == 2){
                setNextState(true); //brings it back to life
            }
        }
    
    }
}
