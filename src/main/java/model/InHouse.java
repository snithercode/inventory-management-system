package model;

/**
 * This class extends the Part class and represents an in-house part.
 */
public class InHouse extends Part {
    // Declare Fields
    private int machineId;

    // Declare Constructor

    /**
     * Constructor method for the InHouse class, which extends the Part class.
     * Initializes properties inherited from Part, as well as the machineId property specific to InHouse.
     * @param id         The unique id for the part.
     * @param name       The name of the part.
     * @param price      The price of the part.
     * @param stock      The quantity of the part in stock.
     * @param min        The minimum allowed quantity of the part in stock.
     * @param max        The maximum allowed quantity of the part in stock.
     * @param machineId  The id of the machine.
     */
    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId){
        // Call the constructor of the parent class, Part, to inherit attributes and properly initialize the object.
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    // Declare Methods

    /**
     * Sets the machine ID for this part.
     * @param machineId the machine ID for this part
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Gets the machine ID for this part.
     * @return the machine ID for this part
     */
    public int getMachineId(){
        return machineId;
    }

    }

