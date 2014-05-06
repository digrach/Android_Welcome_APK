package colley.chisholm.diploma.welcome;

import android.graphics.Paint;

public class Particle {

	private double posy;
	private double posx;
	private double targetx;
	private double targety;
	private double  size;
	//private Paint color;
	private int color;
	private double randomEase;
	private double alpha;
	private double fade;
	private double returny;
	private boolean onWayDown;
	private double travelRate;
	double originy;
	double initialSize;
	String name = "";

	public Particle(double posx, double posy, double targetx, double targety,
			int size, int color, double returny) {
		super();
		this.posy = posy;
		this.posx = posx;
		this.targetx = targetx;
		this.targety = targety;
		this.size = size;
		this.color = color;
		this.returny = returny;
		randomEase = Math.random() * 0.03;
		alpha = 1;
		fade = Math.random() * 0.1;
		this.onWayDown = false;
		travelRate = 0.03;
	}

//	public void draw(GraphicsContext context) {
//		context.setFill(color);
//		context.fillOval(posx-size, posy-size, size+size, size+size);
//	}

	private double  calculateYDistance(double yTarget) {
		return yTarget - posy;
	}

	private double  calculateXDistance(double xTarget) {
		return xTarget - posx;
	}

	public boolean check() {
		
		return  alpha < 0.005;
	}

	public boolean update() {
		
		posx += calculateXDistance(targetx) * (travelRate);
		posy += calculateYDistance(targety) * (travelRate + randomEase);
		
//		double floorx = Math.floor(posx + 0.5);
//		double floory = Math.floor(posy + 0.5);
//
//		// If has reached the target.
//		if (floorx == targetx && floory == targety){
//			originy = targety;
//			initialSize = size;
//			// Reset target
//			targety = returny;
//			travelRate = 0.01;
//			onWayDown = true;
//		}
//
//		posx += calculateXDistance(targetx) * (travelRate);
//		posy += calculateYDistance(targety) * (travelRate + randomEase);
//
//		if (onWayDown == true) {
//			double span = targety - originy;
//			double currenty = posy - originy;
//			double ratio = (1.0 / (span  * 1.0)) * currenty;
//			size = initialSize * (1.0 - ratio);
//		}

		return posy < 100;
		//if (name == "track") {
//		System.out.println();
//		System.out.println("initial size: " + initialSize);
//		System.out.println("originy: " + originy);
//		System.out.println("span: " + span);
//		System.out.println("currenty: " + currenty);
//		System.out.println("ratio: " + ratio);
//		System.out.println("size: " + size);

	//}

	}

	public boolean reachedTarget() {
		double floorx = Math.floor(posx + 0.5);
		double floory = Math.floor(posy + 0.5);
		if (floorx == targetx && floory == targety){
			return true;
		}
		return false;
	}

	public boolean reachedTargetCoordinate(double currentCoord, double targetCoord) {
		double floor = Math.floor(currentCoord + 0.5);
		if (floor == targetCoord){
			return true;
		}
		return false;
	}

	public double getPosy() {
		return posy;
	}
	public void setPosy(double posy) {
		this.posy = posy;
	}
	public double getPosx() {
		return posx;
	}
	public void setPosx(double posx) {
		this.posx = posx;
	}
	public double getTargetx() {
		return targetx;
	}
	public void setTargetx(double targetx) {
		this.targetx = targetx;
	}
	public double getTargety() {
		return targety;
	}
	public void setTargety(double targety) {
		this.targety = targety;
	}
	public double getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public double getRandomEase() {
		return randomEase;
	}
	public void setRandomEase(double randomEase) {
		this.randomEase = randomEase;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getFade() {
		return fade;
	}
	public void setFade(double fade) {
		this.fade = fade;
	}
	public double getReturny() {
		return returny;
	}
	public void setReturny(double returny) {
		this.returny = returny;
	}

	public String toString() {
		return "Particle " + posx + " " + posy;
	}

	public boolean isOnWayDown() {
		return this.onWayDown;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(posx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(posy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(targetx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(targety);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particle other = (Particle) obj;
		if (Double.doubleToLongBits(posx) != Double
				.doubleToLongBits(other.posx))
			return false;
		if (Double.doubleToLongBits(posy) != Double
				.doubleToLongBits(other.posy))
			return false;
		if (Double.doubleToLongBits(size) != Double
				.doubleToLongBits(other.size))
			return false;
		if (Double.doubleToLongBits(targetx) != Double
				.doubleToLongBits(other.targetx))
			return false;
		if (Double.doubleToLongBits(targety) != Double
				.doubleToLongBits(other.targety))
			return false;
		return true;
	}
	
	

}
