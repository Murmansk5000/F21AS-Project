package Stage1;

public class Baggage {
    private static final double BASE_FEE = 0.0;
    private static final double WEIGHT_LIMIT = 23;
    private static final double MAX_LENGTH = 76;
    private static final double MAX_WIDTH = 51;
    private static final double MAX_HEIGHT = 31;

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

    public static double getWeightLimit() {
        return WEIGHT_LIMIT;
    }

    public static double getSizeLimit() {
        return MAX_LENGTH + MAX_WIDTH + MAX_HEIGHT;
    }

    public static double getMaxLength() {
        return MAX_LENGTH;
    }

    public static double getMaxWidth() {
        return MAX_WIDTH;
    }

    public static double getMaxHeight() {
        return MAX_HEIGHT;
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

    public String getWeightPrint() {
        return String.format("%5s",
                String.format("%.2f", weight));
    }

    public double getVolume() {
        return volume;
    }

    public String getVolumePrint() {
        return String.format("%5s x %5s x %5s",
                String.format("%.2f", length),
                String.format("%.2f", width),
                String.format("%.2f", height));
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
        if (this.getWeight() > getWeightLimit()) {
            throw new AllExceptions.NumberErrorException();
        }
        // Check if the baggage is over the size limit
        if (this.getSize() > getSizeLimit()) {
            throw new AllExceptions.NumberErrorException();
        }
    }


    @Override
    public String toString() {
        return String.format("[weight=%s kg, dimensions=%s]",
                getWeightPrint(),
                getVolumePrint());
    }

}
