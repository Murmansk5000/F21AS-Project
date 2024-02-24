import AllException.FormatErrorException;
import AllException.OverWeightException;

public class Baggage {
	// Assuming a base fee
	private static final double BASE_FEE = 0.0;
	private double length = 0.0;
	private double width = 0.0;
	private double height = 0.0;
	private double weight = 0.0;
	private double volume = 0.0;
	private double fee = 0.0;

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

	public void calculateFee() throws FormatErrorException {
		// Reset the fee to a base value or specific initial charge
		double weightFee = 0.0;
		double sizeFee = 0.0;

		double weightLimit = 23; // 23 kg weight limit
		double sizeLimit = 158; // 158 cm size limit (length + width + height)
		double excessWeightFee = 50; // Charge per kg for weight over the limit
		double excessSizeCharge = 100; // Charge for size over the limit

		// Check if the baggage is over the weight limit
		if (weight > weightLimit) {
			//weightFee += (weight - weightLimit) * excessWeightFee;
			throw new FormatErrorException();
		}

		// Check if the baggage is over the size limit
		if ((length + width + height) > sizeLimit) {
			//sizeFee += excessSizeCharge;
			throw new FormatErrorException();
		}
		fee = BASE_FEE + Math.max(weightFee, sizeFee);
	}
}
