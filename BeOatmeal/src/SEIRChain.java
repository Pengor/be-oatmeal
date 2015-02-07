
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
	 * @param h
	 * @param beta
	 * @param delt
	 * @param gam
	 */
	public SEIRChain(double n, double s_0, double e_0, double i_0, double beta, double gam, double delt) {
		super();

		//Set initial fields
		this.s_0 = s_0;
		this.s = this.s_0;
		
		this.e_0 = e_0;
		this.e = this.e_0;
		
		this.i_0 = i_0;
		this.i = this.i_0;
		
		this.r_0 = 0;
		this.r = this.r_0;
		
		this.n = n;

		this.h = h;
		
		this.beta = beta;

		this.p = 1 - Math.pow(Math.E, (-beta * h * i_0) / n);
		
		setP();
		
		this._delt = 1/delt;
		this.p_c = 1 - Math.pow(Math.E, -delt * h);

		this._gam = 1/gam;
		this.p_r = 1 - Math.pow(Math.E, -gam * h);
		
		//Set initial state vector
		this.setX_0();
		
		//Set initial a matrix
		this.setA();

	}

	/**
	 * @throws Exception
	 */
	@Override
	protected void iterate() throws Exception {
		x_n.multL(a);
		x_i.add(x_n.copy());
	}
	
	
	
	/**
	 * 
	 */
	private void setP() {
		p = 1 - Math.pow(Math.E, (-beta * h * i) / n);
	}
	
	
	
	/**
	 * 
	 */
	private void setX_0() {
		double[][] temp = new double[4][1];
		temp[0][0] = s;
		temp[1][0] = e;
		temp[2][0] = i;
		temp[3][0] = r;
		
		this.x_0 = new Matrix(temp);
	}
	
	
	
	/**
	 * 
	 */
	private void setA() {
		double[][] temp = new double[4][4];
		
		temp[0][0] = 1 - p;
		temp[0][1] = 0;
		temp[0][2] = 0;
		temp[0][3] = 0;
		
		temp[1][0] = p; 
		temp[1][1] = 1 - p_c;
		temp[1][2] = 0;
		temp[1][3] = 0;
		
		temp[2][0] = 0; 
		temp[2][1] = p_c;
		temp[2][2] = 1 - p_r;
		temp[2][3] = 0;
		
		temp[3][0] = 0; 
		temp[3][1] = 0;
		temp[3][2] = p_r;
		temp[3][3] = 1;
		
		this.a = new Matrix(temp);
	}
}
