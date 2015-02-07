import java.util.Arrays;

public class Matrix {
	private int m;
	private int n;
	
	private double[][] nums;
	
	// Constructor, copies passed matrix to this.nums
	public Matrix(double[][] nums) {
		m = nums.length;
		n = nums[0].length;
		
		this.nums = new double[m][n];
		for (int i = 0; i < m; i++)
			this.nums[i] = Arrays.copyOfRange(nums[i],0,n);
	}
	
	
	/**Returns the entry in the ith row and the jth column of this Matrix
	 * @param i The index of the ith row
	 * @param j The index of the jth column
	 * @return The entry at (i,j)
	 * @throws Exception 
	 */
	public double get(int i, int j) throws Exception {
		if (i < n && i >= 0 && j < m && j >= 0)
			return nums[i][j];
		else
			throw new Exception();
	}
	
	
	/**Sets the entry in the ith row and the jth column of this Matrix
	 * @param i The index of the ith row
	 * @param j The index of the jth column
	 * @param k The value for (i,j) to be set to
	 * @throws Exception
	 */
	public void set(int i, int j, double k) throws Exception {
		if (i < n && i >= 0 && j < m && j >= 0)
			nums[i][j] = k;
		else
			throw new Exception();
	}
	
	
	/**Copies the existing matrix into a new Matrix
	 * @return The new copy of this Matrix
	 */
	public Matrix copy() {
		return new Matrix(nums);
	}
	
	
	/**
	 * @param x
	 * @return
	 * @throws Exception 
	 */
	public Matrix addRet(Matrix x) throws Exception {
		return new Matrix(add(x, this));
	}
	
	
	/**
	 * @param x
	 * @return
	 * @throws Exception 
	 */
	public void add(Matrix x) throws Exception {
		this.nums = add(x, this);
	}
	
	
	/**
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 */
	private static double[][] add(Matrix x, Matrix y) throws Exception {
		if (x.m != y.m || x.n != y.n)
			throw new Exception();
		
		double[][] temp = new double[x.m][x.n];
		
		for (int i = 0; i < x.m; i++) {
			for (int j = 0; j < x.n; j++) {
				temp[i][j] = x.get(i,j) + y.get(i,j);
			}
		}
		
		return temp;
	}
	
	
	/**Multiplies the given Matrix on the left of this Matrix and returns the result
	 * @param x The given Matrix
	 * @return The product of this Matrix with the given Matrix multiplied on the left
	 * @throws Exception 
	 */
	public Matrix multLRet(Matrix x) throws Exception {
		return multRet(this, x);
	}
	
	
	/**Multiplies the given Matrix on the left of this Matrix and sets this Matrix to the result
	 * @param x The given Matrix
	 * @throws Exception 
	 */
	public void multL(Matrix x) throws Exception {
		double[][] temp = mult(this, x);
		
		this.n = temp.length;
		this.m = temp[0].length;
		this.nums = temp;
	}
	
	
	/**Multiplies the given Matrix on the right of this Matrix and returns the result
	 * @param x The given Matrix
	 * @return The product of this Matrix with the given Matrix multiplied on the right
	 * @throws Exception 
	 */
	public Matrix multRRet(Matrix x) throws Exception {
		return multRet(x, this);
	}
	
	
	/**Multiplies the given Matrix on the right of this Matrix and sets this Matrix to the result
	 * @param x The given Matrix
	 * @throws Exception 
	 */
	public void multR(Matrix x) throws Exception {
		double[][] temp = mult(x, this);
		
		this.n = temp.length;
		this.m = temp[0].length;
		this.nums = temp;
	}
	
	
	/**Multiplies two matrices x and y
	 * @param x Given Matrix x
	 * @param y Given Matrix y
	 * @return The product of the matrices as a new Matrix
	 * @throws Exception 
	 */
	private static Matrix multRet(Matrix x, Matrix y) throws Exception {
		double[][] temp = mult(x,y);
		return new Matrix(temp);
	}
	
	
	/**Multiplies two matrices x and y and returns the resulting Matrix' array
	 * @param x Given Matrix x
	 * @param y Given Matrix y
	 * @return The array representation of the resulting Matrix
	 * @throws Exception 
	 */
	private static double[][] mult(Matrix x, Matrix y) throws Exception {
		if (x.n != y.m)
			throw new Exception();
		
		double[][] temp = new double[x.n][y.m];
		
		for (int i = 0; i < x.n; i++) {
			for (int j = 0; j < y.m; j++) {
				for (int k = 0; k < x.n; k++)
					temp[i][j] += x.get(i,k) * y.get(k,j);
			}
		}
		
		return temp;
	}
}
