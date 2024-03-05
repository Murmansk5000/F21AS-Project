package models;

import java.util.ArrayList;

public class BaggageList {
    private ArrayList<Baggage> baggageList;
    private static final double BASE_FEE = 0.0;
    public double totalWeight;
    private double totalVolume;
    public double totalFee;
    private final double weightLimit = 40;

    public BaggageList() {
        this.baggageList = new ArrayList<Baggage>();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
        this.totalFee = 0.0;
    }



    public double getTotalVolume() {
        return this.totalVolume;
    }

    public double getTotalWeight() {
        return this.totalWeight;
    }


    public boolean removeBaggage(Baggage baggage) {
        if (this.baggageList.remove(baggage)) {
            this.totalVolume -= baggage.getVolume();
            this.totalWeight -= baggage.getWeight();
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

    public void clear() {
        this.baggageList.clear();
        this.totalVolume = 0.0;
        this.totalWeight = 0.0;
    }

    public int size() {
        return this.baggageList.size();
    }

    public void calculateTotalWeight() {
        this.totalWeight = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalWeight += baggage.getWeight();
        }
    }

    public void calculateTotalVolume() {
        this.totalVolume = 0.0;
        for (Baggage baggage : this.baggageList) {
            this.totalVolume += baggage.getVolume();
        }
    }

    public void addBaggage(Baggage baggage) {
        if (baggage != null && baggage.getWeight() != 0 && baggage.getSize() != 0) {
            this.baggageList.add(baggage);
        }
        this.checkBaggageList();
        this.calculateTotalWeight();
        this.calculateTotalVolume();
        this.calculateTotalFee();
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

    public boolean isEmpty() {
        return baggageList.isEmpty();
    }


    public void checkBaggageList(){
        if (this.totalWeight > this.getWeightLimit()){
            //TODO Baggagelist > 40 Exception
        }
    }
}