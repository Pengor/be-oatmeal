import java.util.ArrayList;

public class SEIRChain extends MarkovChain {

	private final double CONS_GROWTH = 1;
	
	private boolean med;
	
	private double s; // people susceptible
	private double s_0;
	private double e; // people exposed
	private double e_0;
	private double i; // people infectious
	private double i_0;
	private double r; // people removed

	private double n;

	private double h;
	private double beta;

	private double p;

	private double _delt;
	private double p_c;

	private double _gam;
	private double p_r;

	private double m;
	
	private double v;
	private double n_v;
	private double p_v;
	
	private double u;
	
	private double r_0;
	private ArrayList<Double> r_i;
	
	/**
	 * Constructor
	 * @param a Matrix a, the stochastic matrix
	 * @param x_0 Matrix x_0, the initial state vector
	 */
	public SEIRChain(Matrix a, Matrix x_0) {
		super(a, x_0);
	}

	
	
	/**
	 * Constructor
	 * @param n The number of individuals in the population
	 * @param s_0 The initial proportion of susceptible individuals
	 * @param e_0 The initial proportion of exposed individuals
	 * @param i_0 The initial proportion of infectious individuals
	 * @param h The specified interval in time between iterations
	 * @param beta The transmission parameter
	 * @param delt The inverse of the mean incubation period
	 * @param gam The inverse of the mean infectious period
	 */
	public SEIRChain(double n, double s_0, double e_0, double i_0, 
			double beta, double h, double gam, double delt) {
		super();
		
		med = false;
		
		//Set initial fields
		this.s_0 = s_0;
		this.s = this.s_0;
		
		this.e_0 = e_0;
		this.e = this.e_0;
		
		this.i_0 = i_0;
		this.i = this.i_0;
		
		this.r = 0;
		
		this.n = n;

		this.h = h;
		
		this.beta = beta;
		
		setP();
		
		this._delt = 1/delt;
		this.p_c = 1 - Math.pow(Math.E, -delt * h);

		this._gam = 1/gam;
		this.p_r = 1 - Math.pow(Math.E, -gam * h);

		//Setup ArrayList for holding state vectors
		this.x_i = new ArrayList<Matrix>();
		
		//Set initial state vector
		this.setX_n();
		
		//Set initial a matrix
		this.setA();
		
		//Calculate R_0
		setR_0();
		this.r_i = new ArrayList<Double>();
	}

	
	
	/**
	 * Constructor
	 * @param n The number of individuals in the population
	 * @param s_0 The initial proportion of susceptible individuals
	 * @param e_0 The initial proportion of exposed individuals
	 * @param i_0 The initial proportion of infectious individuals
	 * @param h The specified interval in time between iterations
	 * @param beta The transmission parameter
	 * @param delt The inverse of the mean incubation period
	 * @param gam The inverse of the mean infectious period
	 * @param n_v
	 * @param p_v
	 * @param n_m
	 * @param p_m
	 * @throws Exception 
	 */
	public SEIRChain(double n, double s_0, double e_0, double i_0, 
			double beta, double h, double gam, double delt,
			double n_v, double p_v, double n_m, double p_m) throws Exception {
		this(n, s_0, e_0, i_0, beta, h, gam, delt);
		
		med = true;
		
		this.n_v = n_v;
		this.p_v = p_v;
		
		setM();
		
		setV();
		
		u = 0;
		
		setAMed();
		setX_nMed();
	}
	
	
	
	/**Returns the ArrayList of all x_i values
	 * @return The ArrayList x_i
	 */
	public ArrayList<Matrix> getX_i() {
		return x_i;
	}
	
	
	
	
	
	
	/**
	 * Generates the next state vector and stochastic matrix pair
	 * @throws Exception
	 */
	@Override
	protected void iterate() throws Exception {
		x_n.multR(a);
		x_i.add(x_n.copy());
		
		s = x_n.get(0, 0);
		e = x_n.get(1, 0);
		i = x_n.get(2, 0);
		r = x_n.get(3, 0);
		
		setP();
		setA();
		
		setR_0();
		r_i.add(r_0);
	}
	
	
	
	/**
	 * Generates the next state vector and stochastic matrix pair
	 * @throws Exception
	 */
	protected void iterMed() throws Exception {
		if (med) {	
			x_n.multR(a);
			x_i.add(x_n.copy());
			
			s = x_n.get(0, 0);
			e = x_n.get(1, 0);
			i = x_n.get(2, 0);
			r = x_n.get(3, 0);
			u = x_n.get(4, 0);
			
			setP();
			setV();
			setAMed();
			
			setR_0();
			r_i.add(r_0);
		}
	}
	
	
	
	/**
	 * Sets p based on current values of beta, h, i, and n
	 */
	private void setP() {
		p = 1 - Math.pow(Math.E, (-beta * h * i));
	}
	
	
	
	/** Returns the R_naught at this iteration
	 * @return Returns R_0
	 */
	public double getR_0() {
		return r_0;
	}
	
	
	
	/**
	 * Calculates R_0 for the current parameters
	 */
	private void setR_0() {
		r_0 = (beta * s * _gam);
	}
	
	
	
	/**
	 * Sets x_0 for current values of s, e, i, r
	 */
	private void setX_n() {
		double[][] temp = new double[4][1];
		temp[0][0] = s;
		temp[1][0] = e;
		temp[2][0] = i;
		temp[3][0] = r;
		
		this.x_n = new Matrix(temp);
	}
	
	
	
	/**
	 * Sets x_0 for current values of s, e, i, r
	 */
	private void setX_nMed() {
		double[][] temp = new double[5][1];
		temp[0][0] = s;
		temp[1][0] = e;
		temp[2][0] = i;
		temp[3][0] = r;
		temp[4][0] = v;
		
		this.x_n = new Matrix(temp);
	}
	
	
	
	
	/**
	 * Sets matrix based on current values of p, p_c, and p_r
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
	
	
	
	/**
	 * Sets matrix based on current values of p, p_c, p_r, m, and v
	 */
	private void setAMed() {
		double[][] temp = new double[5][5];
		
		temp[0][0] = 1 - p - v;
		temp[0][1] = 0;
		temp[0][2] = 0;
		temp[0][3] = 0;
		temp[0][4] = 0;
		
		temp[1][0] = p; 
		temp[1][1] = 1 - p_c - m;
		temp[1][2] = 0;
		temp[1][3] = 0;
		temp[1][4] = 0;
		
		temp[2][0] = 0; 
		temp[2][1] = p_c;
		temp[2][2] = 1 - p_r;
		temp[2][3] = 0;
		temp[2][4] = 0;
		
		temp[3][0] = 0; 
		temp[3][1] = 0;
		temp[3][2] = p_r;
		temp[3][3] = 1;
		temp[3][4] = 0;
		
		temp[4][0] = v;
		temp[4][1] = m;
		temp[4][2] = 0;
		temp[4][3] = 0;
		temp[4][4] = 1;
		
		this.a = new Matrix(temp);
	}
	
	
	
	/**
	 * Adjusts the value of M to the current value of E 
	 */
	private void setM() {
		m = 1 - (1/getR_0());
	}

	
	/**Returns the medication coefficient for this iteration
	 * @return m
	 */
	public double getM() {
		if (med)
			return m;
		else
			return -1;
	}
	
	
	
	/**
	 * Adjusts the value of M to the current value of E 
	 */
	private void setV() {
		setN_v();
		v = (p_v * n_v) / (n * s * (1 - p));
	}
	
	
	
	private void setN_v() {
		n_v *= CONS_GROWTH;
	}
	
	
	
	/**Returns the vaccination coefficient for this iteration
	 * @return v
	 */
	public double getV() {
		if (med)
			return v;
		else
			return -1;
	}
}