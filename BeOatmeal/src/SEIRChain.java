
public class SEIRChain extends MarkovChain {
	
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
	
	
	public SEIRChain(Matrix a, Matrix x_0) {
		super(a, x_0);
	}
	
	
	public SEIRChain(double n, double s_0, double e_0, double i_0, double h, double beta, double delt, double gam) {
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
		
		this._delt = 1/delt;
		this.p_c = 1 - Math.pow(Math.E, -delt * h);
		
		this._gam = 1/gam;
		this.p_r = 1 - Math.pow(Math.E, -gam * h);
		
		//Set initial state vector
		double[][] temp = new double[4][1];
		temp[0][0] = s;
		temp[1][0] = e;
		temp[2][0] = i;
		temp[3][0] = r;
		
		this.x_0 = new Matrix(temp);
		
		//Set initial a matrix
		temp = new double[4][4];
		
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
	 * @throws Exception
	 */
	@Override
	protected void iterate() throws Exception {
		x_n.multL(a);
		x_i.add(x_n.copy());
	}
}
