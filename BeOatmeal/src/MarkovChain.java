import java.util.ArrayList;

public class MarkovChain {
	
	protected Matrix a;
	protected Matrix x_0;
	
	protected Matrix x_n;
	protected ArrayList<Matrix> x_i;
	
	
	/**
	 * @param a
	 * @param x_0
	 */
	public MarkovChain(Matrix a, Matrix x_0) {
		this.a = a;
		this.x_0 = x_0;
		//Fix references and copy instead
		this.x_n = x_0.copy();
		
		x_i = new ArrayList<Matrix>();
		x_i.add(this.x_0);
	}
	
	
	
	/**
	 * @throws Exception
	 */
	protected void iterate() throws Exception {
		x_n.multL(a);
		x_i.add(x_n.copy());
	}
}
