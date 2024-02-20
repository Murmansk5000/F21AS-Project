//a simple class to contain and manage Baggage details
//(id, name, hours)
public class Baggage {
	private double length;
	private double width;
	private double height;
	private double weight;
	private double volume;
	private double fee;

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
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

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}
	
	public void calculateFee() {
	    double weightLimit = 23; // 23 kg weight limit
	    double sizeLimit = 158; // 158 cm size limit (length + width + height)
	    double excessWeightFee = 50; // Charge per kg for weight over the limit
	    double excessSizeCharge = 100; // Charge for size over the limit

	    // Check if the baggage is over the weight limit
	    if (weight > weightLimit) {
	        fee += (weight - weightLimit) * excessWeightFee;
	    }

	    // Check if the baggage is over the size limit
	    if ((length + width + height) > sizeLimit) {
	        fee += excessSizeCharge;
	    }
	}
}
