package Stage1.modules;


import java.util.Random;

public class BaggageFactory {
    public static final Random random = new Random();

    private static final double MIN_WEIGHT = 5;
    private static final double MIN_LENGTH = 20;
    private static final double MIN_WIDTH = 20;
    private static final double MIN_HEIGHT = 20;

    private static final double MAX_WEIGHT = Baggage.getWeightLimit();
    private static final double MAX_LENGTH = Baggage.getMaxLength();
    private static final double MAX_WIDTH = Baggage.getMaxWidth();
    private static final double MAX_HEIGHT = Baggage.getMaxHeight();


    /**
     * Generates a Baggage object with random weight and dimensions within preset limits.
     *
     * @return A Baggage object with random attributes.
     * @throws AllExceptions.NumberErrorException if generated attributes exceed limits.
     */
    public static Baggage generateRandomBaggage() throws AllExceptions.NumberErrorException {
        double weight = MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
        double length = MIN_LENGTH + (MAX_LENGTH - MIN_LENGTH) * random.nextDouble();
        double width = MIN_WIDTH + (MAX_WIDTH - MIN_WIDTH) * random.nextDouble();
        double height = MIN_HEIGHT + (MAX_HEIGHT - MIN_HEIGHT) * random.nextDouble();
        return new Baggage(weight, length, width, height);
    }
}

