package models;

public class Baggage {
    private static final double BASE_FEE = 0.0;
    private double weight = 0.0;
    private double length = 0.0;
    private double width = 0.0;
    private double height = 0.0;
    private double volume = 0.0;
    private double fee = 0.0;

    public Baggage(double weight, double length, double width, double height) {
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.updateVolume();

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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void checkBaggage() throws AllExceptions.FormatErrorException {
        double weightLimit = 23; // 23 kg weight limit
        double sizeLimit = 158; // 158 cm size limit (length + width + height)
        //Check if the baggage is over the weight limit
        if (this.weight > weightLimit) {
            throw new AllExceptions.FormatErrorException();
        }
        // Check if the baggage is over the size limit
        if ((this.length + this.width + this.height) > sizeLimit) {
            throw new AllExceptions.FormatErrorException();
        }
    }
}
