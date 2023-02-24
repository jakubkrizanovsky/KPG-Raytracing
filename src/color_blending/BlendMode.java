package color_blending;

public class BlendMode {
	
	public final double src;
	public final double dst;
	
	public final static BlendMode ADDITIVE = new BlendMode(1, 1);
	public final static BlendMode MULTIPLICATIVE = new BlendMode(1, 0);
	
	public BlendMode(double src, double dst) {
		this.src = src;
		this.dst = dst;
	}
}
