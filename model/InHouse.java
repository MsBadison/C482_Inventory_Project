package c482.inventory.model;

/**
 *
 * @author Madison Maguire
 */

public class InHouse extends Part{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId(){
        return machineId;
    }
}

