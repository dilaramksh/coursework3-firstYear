import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {
    
    //this is the probability of the the cell being either one of the 4 cells (it is calculated through the range)
    private static final double MYCOPLASMA_PROB = 0.25; // 25%
    private static final double AMOEBA_PROB = 0.47; // 22%
    private static final double UREAPLASMA_PROB = 0.79; // 31%
    private static final double BRUCELLA_PROB = 1; // 20%

    
    //this is the probability of the cells being alive in the 0th generation for each type of cell
    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double AMOEBA_ALIVE_PROB = 0.21;
    private static final double UREAPLASMA_ALIVE_PROB = 0.43;
    private static final double BRUCELLA_ALIVE_PROB = 0.39;
    
    private static List<Cell> cells;
    private Field field;
    private static int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act(); 
        }

        for (Cell cell : cells) {
          cell.updateState();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
      Random rand = Randomizer.getRandom();
      field.clear();
      for (int row = 0; row < field.getDepth(); row++) {
        for (int col = 0; col < field.getWidth(); col++) {
          double probabilityCell = rand.nextDouble(); //creates random double for every cell
          double probabilityAlive = rand.nextDouble(); //creates random double for the probability of a cell
          Location location = new Location(row, col);
          if (probabilityCell <= MYCOPLASMA_PROB) { //<= 0.25 (25%)
              Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
              cells.add(myco);
              if (probabilityAlive >= MYCOPLASMA_ALIVE_PROB){
                  myco.setDead();}
          }
          else if (probabilityCell <= AMOEBA_PROB){ //0.25 < x <= 0.47 (22%)
              Amoeba amoeba = new Amoeba(field, location, Color.GREEN);
              cells.add(amoeba);
              if (probabilityAlive >= AMOEBA_ALIVE_PROB){
                  amoeba.setDead();}
          }
          else if (probabilityCell <= UREAPLASMA_PROB){ //0.47 < x <= 0.79 (32%)
              Ureaplasma ureaplasma = new Ureaplasma(field, location, Color.GREY);
              cells.add(ureaplasma);
              if (probabilityAlive >= UREAPLASMA_ALIVE_PROB){
                  ureaplasma.setDead();}
          }
          else{ //0.79 < x <= 1.0 (21%)
              Brucella brucella = new Brucella(field, location, Color.PURPLE);
              cells.add(brucella);
              if (probabilityAlive >= BRUCELLA_ALIVE_PROB){
                brucella.setDead();}
          }
        }
      }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public Field getField() {
        return field;
    }

    public static int getGeneration() {
        return generation;
    }
}
