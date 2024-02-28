package models;

import java.util.ArrayList;

public class BaggageList {
    private static final double BASE_FEE = 0.0;
    private ArrayList<Baggage> baggages;
    private double totalVolume;
    private double totalWeight;
    private double totalFee;
    private int quantity;

    public BaggageList() {
        this.baggages = new ArrayList<Baggage>();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.totalFee = 0.0;
        this.quantity = 0;
    }

    public void addBaggage(Baggage baggage) {
        if (baggage != null) {
            this.baggages.add(baggage);
            this.totalVolume += baggage.getVolume();
            this.totalWeight += baggage.getWeight();
            this.totalFee += baggage.getFee();
            this.quantity++;
        }
    }

    public boolean removeBaggage(Baggage baggage) {
        if (this.baggages.remove(baggage)) {
            this.totalVolume -= baggage.getVolume();
            this.totalWeight -= baggage.getWeight();
            this.totalFee -= baggage.getFee();
            this.quantity--;
            return true;
        }
        return false;
    }

    public double getTotalVolume() {
        return this.totalVolume;
    }

    public double getTotalWeight() {
        return this.totalWeight;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void calculateTotalWeight() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggages) {
            this.totalWeight += baggage.getWeight();
        }
    }

    public void calculateTotalVolume() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggages) {
            this.totalVolume += baggage.getVolume();
        }
    }


    public double getTotalFee() {
        return this.totalFee;
    }

    /**
     * Merge multiple baggage lists.
     *
     * @param lists The baggage lists to merge
     * @return The merged baggage list
     */
    public static BaggageList mergeBaggageLists(BaggageList... lists) throws AllExceptions.OverWeightException {
        BaggageList mergedList = new BaggageList();
        for (BaggageList list : lists) {
            for (Baggage baggage : list.baggages) {
                mergedList.addBaggage(baggage);
            }
        }
        mergedList.calculateTotalfee();
        return mergedList;
    }
    public void clear() {
        this.baggages.clear();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.quantity = 0;
    }

    public int size(){
        return this.size();
    }

    public void calculateTotalfee() throws AllExceptions.OverWeightException{
        // Reset the fee to a base value or specific initial charge
        double Fee = 0.0;
        double totalWeightLimit = 40; // 40 kg weight limit
        double excessWeightFee = 50; // Charge per kg for weight over the limit

        // Check if the baggage is over the weight limit
        if (this.totalWeight > totalWeightLimit) {
            Fee += (this.totalFee - totalWeightLimit) * excessWeightFee;
            throw new AllExceptions.OverWeightException();
        }

        // Check if the baggage is over the size limit
        this.totalFee = BASE_FEE + Fee;
    }
}
