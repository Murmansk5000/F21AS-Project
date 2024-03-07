package models;

import java.util.ArrayList;

public class BaggageList {
    private ArrayList<Baggage> baggageList;
    private static final double BASE_FEE = 0.0;
    public double totalWeight;
    private double totalVolume;
    public double totalFee;
    private final double weightLimit = 40;
    private final double totalWeightLimit = 40; // 40 kg weight limit
    private final double excessWeightFee = 50; // Charge per kg for weight over the limit

    public BaggageList() {
        this.baggageList = new ArrayList<Baggage>();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.totalFee = 0.0;
    }
    public ArrayList<Baggage> getBaggageList() {
        return new ArrayList<>(baggageList); // 返回乘客列表的一个副本以保护封装性
    }

    public double getTotalVolume() {
        return this.totalVolume;
    }

    public double getTotalWeight() {
        return this.totalWeight;
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
    public boolean removeOneBaggage(Baggage baggage) {
        if (this.baggageList.remove(baggage)) {
            this.calculateTotalWeight();
            this.calculateTotalVolume();
            this.calculateTotalFee();
            return true;
        }
        return false;
    }


    public double getTotalFee() {
        return this.totalFee;
    }

    public double getWeightLimit() {
        return weightLimit;
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
     *
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
    public void addBaggage(Baggage baggage) throws AllExceptions.NumberErrorException {
        if (baggage != null && baggage.getWeight() != 0 && baggage.getSize() != 0) {
            this.baggageList.add(baggage);
        }
        this.checkBaggageList();
        this.calculateTotalWeight();
        this.calculateTotalVolume();
        this.calculateTotalFee();
    }

    /**
     * Calculates the total weight of all baggage.
     */
    public void calculateTotalWeight() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalWeight += baggage.getWeight();
        }
    }
    /**
     * Calculates the total volume of all baggage.
     */
    public void calculateTotalVolume() {
        this.totalVolume = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalVolume += baggage.getVolume();
        }
    }

    /**
     * Calculates the total fee based on baggage weight.
     */
    public void calculateTotalFee() {
        // Reset the fee to a base value or specific initial charge
        double Fee = this.BASE_FEE;

        // Check if the baggage is over the weight limit
        if (this.totalWeight > this.totalWeightLimit) {
            Fee += (this.totalWeight - this.totalWeightLimit) * this.excessWeightFee;
        }

        // Check if the baggage is over the size limit
        this.totalFee = this.BASE_FEE + Fee;
    }

    /**
     * Checks if the baggage list is empty.
     *
     * @return true if there are no baggage items in the list, false otherwise.
     */
    public boolean isEmpty() {
        return baggageList.isEmpty();
    }

    /**
     * Validates total baggage weight against the limit.
     *
     * @throws AllExceptions.NumberErrorException if weight limit is exceeded.
     */
    public void checkBaggageList() throws AllExceptions.NumberErrorException {
        if (this.totalWeight > this.getWeightLimit()){
            throw new AllExceptions.NumberErrorException();
        }
    }
}