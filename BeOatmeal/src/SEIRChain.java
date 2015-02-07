
public class SEIRChain extends MarkovChain {

	private double s; // people susceptible
	private double s_0;
	private double e; // people exposed
	private double e_0;
	private double i; // people infectious
	private double i_0;
	private double r; // people removed
	private double r_0;

	private double n;

	private double h;
	private double beta;

	private double p;

	private double _delt;
	private double p_c;

	private double _gam;
	private double p_r;


	/**
	 * Constructor
	 * @param a Matrix a, the stochastic matrix
	 * @param x_0 Matrix x_0, the initial state vector
	 */
	public SEIRChain(Matrix a, Matrix x_0) {
		super(a, x_0);
	}


	/**
	 * @param n
	 * @param s_0
	 * @param e_0
	 * @param i_0
	 * @param beta
	 * @param gam
	 * @param delt
	 */
	public SEIRChain(double n, double s_0, double e_0, double i_0, double beta, double gam, double delt) {
		super();

		this.s_0 = s_0;
		this.e_0 = e_0;
		this.i_0 = i_0;

		this.n = n;

		this.h = h;
		this.beta = beta;

		this.p = 1 - Math.pow(Math.E, (-beta * h * i_0) / n);

		this._delt = 1/delt;
		this.p_c = 1 - Math.pow(Math.E, -delt * h);

		this._gam = 1/gam;
		this.p_r = 1 - Math.pow(Math.E, -gam * h);
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
