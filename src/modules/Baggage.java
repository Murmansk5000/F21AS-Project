package modules;

public class Baggage {
    private static final double BASE_FEE = 0.0;
    private final double weightLimit = 23;
    private final double sizeLimit = 158;
    private double weight = 0.0;
    private double length = 0.0;
    private double width = 0.0;
    private double height = 0.0;
    private double volume = 0.0;

    public Baggage(double weight, double length, double width, double height) throws AllExceptions.NumberErrorException {
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.updateVolume();
        this.checkBaggage();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
        updateVolume();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        updateVolume();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        updateVolume();
    }

    /**
     * Returns the size of the baggage.
     *
     * @return double value representing the sum of the height, width, and length.
     */
    public double getSize() {
        return this.height + this.width + this.length;
    }

    private void updateVolume() {
        this.volume = length * width * height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public double getWeightLimit() {
        return this.weightLimit;
    }

    public double getSizeLimit() {
        return this.sizeLimit;
    }

    /**
     * This method check the baggage against predefined weight and size limits.
     * It throws a NumberErrorException if the baggage exceeds either limit.
     * This ensures that all baggage adheres to the specified restrictions before being accepted.
     *
     * @throws AllExceptions.NumberErrorException if the baggage exceeds the weight limit or the size limit.
     */
    public void checkBaggage() throws AllExceptions.NumberErrorException {
        //Check if the baggage is over the weight limit
        if (this.getWeight() > this.getWeightLimit()) {
            throw new AllExceptions.NumberErrorException();
        }
        // Check if the baggage is over the size limit
        if (this.getSize() > this.getSizeLimit()) {
            throw new AllExceptions.NumberErrorException();
        }
    }
}
