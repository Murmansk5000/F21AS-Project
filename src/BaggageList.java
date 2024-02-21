import java.util.ArrayList;

public class BaggageList {
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

    public void calculateTotalfee() {
        this.totalFee = 0.0;
        for (Baggage baggage : this.baggages) {
            this.totalFee += baggage.getFee();
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
    public static BaggageList mergeBaggageLists(BaggageList... lists) {
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
}
