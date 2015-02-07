
public class SEIRSeries extends MarkovChain {
	
	private double s;
		private double s_0;
	private double e;
		private double e_0;
	private double i;
		private double i_0;
	private double r;
		private double r_0;
	
	private double n;
	
	private double h;
	private double beta;
	
	private double p;
	
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
