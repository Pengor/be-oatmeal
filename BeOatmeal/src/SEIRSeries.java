
public class SEIRSeries extends MarkovChain {
	
	private double S;
	private double E;
	private double I;
	private double R;
	
	private double h;
	private double beta;
	
	private double _delt;
	private double p_c;
	
	private double _gam;
	private double p_r;
	
	
	public SEIRSeries(Matrix a, Matrix x_0) {
		super(a, x_0);
	}
	
	
	/**
	 * @throws Exception
	 */
	@Override
	protected void iterate() throws Exception {
		x_n.multL(a);
		x_i.add(x_n.copy());
	}
}
