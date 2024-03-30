package Stage1;

import java.util.ArrayList;

public class BaggageList {
    private static final double BASE_FEE = 0.0;
    private static final double FREE_QUOTA = 15;
    private static final double EXCESS_WEIGHT_FEE = 50; // Charge per kg for weight over the limit

    private final ArrayList<Baggage> baggageList;
    public double totalWeight;
    public double totalFee;
    private double totalVolume;

    public BaggageList() {
        this.baggageList = new ArrayList<Baggage>();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.totalFee = 0.0;
    }

    public ArrayList<Baggage> getBaggageList() {
        return new ArrayList<>(baggageList);
    }

    public double getTotalVolume() {
        return this.totalVolume;
    }

    public double getTotalWeight() {
        return this.totalWeight;
    }


    public double getTotalFee() {
        return this.totalFee;
    }

    public double getFreeQuota() {
        return FREE_QUOTA;
    }

    /**
     * Clears all baggage items and updates totals.
     * Removes all items from baggage list, then recalculates total weight, volume, and fees.
     */
    public void clear() {
        this.baggageList.clear();
        this.calculateTotalWeight();
        this.calculateTotalVolume();
        this.calculateTotalFee();
    }

    /**
     * @return The size of the baggage list.
     */
    public int size() {
        return this.baggageList.size();
    }

    /**
     * Adds valid baggage to the list and updates totals.
     *
     * @param baggage The Baggage to add.
     */
    public synchronized void addBaggage(Baggage baggage) throws AllExceptions.NumberErrorException {
        if (baggage != null && baggage.getWeight() != 0 && baggage.getSize() != 0) {
            this.baggageList.add(baggage);
        }
        this.calculateTotalWeight();
        this.calculateTotalVolume();
        this.calculateTotalFee();
    }

    /**
     * Attempts to remove baggage from the baggage list.
     * If the specified baggage is found in the baggage list, it is removed.
     * Then the method recalculates the total weight, volume and total fees.
     *
     * @param baggage The Baggage object to be removed from the baggage list.
     * @return boolean Returns true if the baggage was successfully removed.
     * Returns false if the baggage was not found in the list.
     */
    private synchronized boolean removeOneBaggage(Baggage baggage) {
        if (this.baggageList.remove(baggage)) {
            this.calculateTotalWeight();
            this.calculateTotalVolume();
            this.calculateTotalFee();
            return true;
        }
        return false;
    }

    /**
     * Calculates the total weight of all baggage.
     *
     * @return total weight.
     */
    private double calculateTotalWeight() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalWeight += baggage.getWeight();
        }
        return this.totalWeight;
    }

    /**
     * Calculates the total volume of all baggage.
     *
     * @return total volume.
     */
    private double calculateTotalVolume() {
        this.totalVolume = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalVolume += baggage.getVolume();
        }
        return this.totalVolume;
    }

    /**
     * Calculates the total fee based on baggage weight.
     *
     * @return total fee.
     */
    private double calculateTotalFee() {
        // Reset the fee to a base value or specific initial charge
        double Fee = BASE_FEE;

        // Check if the baggage is over the weight limit
        if (this.totalWeight > this.FREE_QUOTA) {
            Fee += (this.totalWeight - this.FREE_QUOTA) * this.EXCESS_WEIGHT_FEE;
        }

        // Check if the baggage is over the size limit
        this.totalFee = Fee;
        return totalFee;
    }

    public void renewBaggageList() {
        this.calculateTotalWeight();
        this.calculateTotalVolume();
        this.calculateTotalFee();
    }


    public Baggage get(int i) {
        return this.baggageList.get(i);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Baggage baggage : baggageList) {
            result.append(baggage.toString()).append(" ");
        }
        return result.toString();
    }
}