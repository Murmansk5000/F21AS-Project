package models;

public class Baggage {
    private static final double BASE_FEE = 0.0;
    private double weight = 0.0;
    private double length = 0.0;
    private double width = 0.0;
    private double height = 0.0;
    private double volume = 0.0;
    private final double weightLimit = 23;
    private final double sizeLimit = 158;

    public Baggage(double weight, double length, double width, double height) throws AllExceptions.FormatErrorException {
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

    public double getWeightLimit(){
        return this.weightLimit;
    }
    public double getSizeLimit(){
        return this.sizeLimit;
    }

    public void checkBaggage() throws AllExceptions.FormatErrorException {
        //Check if the baggage is over the weight limit
        if (this.weight > this.getWeightLimit()) {
            throw new AllExceptions.FormatErrorException();
        }
        // Check if the baggage is over the size limit
        if (this.getSize() > this.getSizeLimit()) {
            throw new AllExceptions.FormatErrorException();
        }
    }
}
