package models;

import java.util.ArrayList;

public class BaggageList {
    private static final double BASE_FEE = 0.0;
    public double totalWeight;
    public double totalFee;
    private ArrayList<Baggage> baggageList;
    private double totalVolume;
    private int quantity;

    public BaggageList() {
        this.baggageList = new ArrayList<Baggage>();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.totalFee = 0.0;
        this.quantity = 0;
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
            for (Baggage baggage : list.baggageList) {
                mergedList.addBaggage(baggage);
            }
        }
        mergedList.calculateTotalFee();
        return mergedList;
    }

    public void addBaggage(Baggage baggage) {
        if (baggage != null && baggage.getWeight() != 0 && baggage.getSize() != 0) {
            this.baggageList.add(baggage);
            this.totalVolume += baggage.getVolume();
            this.totalWeight += baggage.getWeight();
            this.totalFee += baggage.getFee();
            this.quantity++;
        }
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

    public boolean removeBaggage(Baggage baggage) {
        if (this.baggageList.remove(baggage)) {
            this.totalVolume -= baggage.getVolume();
            this.totalWeight -= baggage.getWeight();
            this.totalFee -= baggage.getFee();
            this.quantity--;
            return true;
        }
        return false;
    }

    public void calculateTotalWeight() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalWeight += baggage.getWeight();
        }
    }


    public double getTotalFee() {
        return this.totalFee;
    }

    public void calculateTotalVolume() {
        this.totalVolume = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalVolume += baggage.getVolume();
        }
    }

    public void clear() {
        this.baggageList.clear();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.quantity = 0;
    }

    public int size() {
        return this.baggageList.size();
    }

    public double calculateTotalFee() {
        // Reset the fee to a base value or specific initial charge
        double Fee = 0.0;
        double totalWeightLimit = 40; // 40 kg weight limit
        double excessWeightFee = 50; // Charge per kg for weight over the limit

        // Check if the baggage is over the weight limit
        if (this.totalWeight > totalWeightLimit) {
            Fee += (this.totalWeight - totalWeightLimit) * excessWeightFee;
        }

        // Check if the baggage is over the size limit
        this.totalFee = BASE_FEE + Fee;
        return totalFee;
    }

    public ArrayList<Baggage> getBaggageList() {
        return new ArrayList<>(baggageList); // 返回乘客列表的一个副本以保护封装性
    }

    public void clearBaggages() {
        baggageList.clear();
    }

    public boolean isEmpty() {
        return baggageList.isEmpty();
    }
}