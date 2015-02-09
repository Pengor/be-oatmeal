import java.io.BufferedWriter;
import java.nio.charset.*;
import java.nio.file.*;


public class ChainDriver {

	private static final boolean MED_VACC = true; 
	private static final double TOLERANCE = 0.000001;

	private static final boolean USE_ITERS = false;
	private static final int ITERS = 100;

	private static final boolean GRAPH_GEN = false;

	private static double N 	= 18629152;
	private static double S 	= 0.9988263556;
	private static double E 	= 0.0005705573716;
	private static double I 	= 0.0006030870326;

	private static double BETA 	= 0.2;
	private static double H 	= 1;
	private static double DELT 	= 0.2;
	private static double GAM 	= 0.143;

	private static double V 	= 42755.43;
	private static double P_V 	= .9;
	private static double M 	= 2976;
	private static double P_M 	= 0.67;

	public static void main(String[] args) throws Exception {
		Path out;

		if (args.length <= 0) {
			String filename = "SEIR Generated Data for ";
			filename += Long.toString(System.currentTimeMillis());
			filename += ".csv";
			out = FileSystems.getDefault().getPath("C:","GenData",filename);
		}

		else {
			String filename = args[1] + "_" + args[0] + "_" + "SEIR_" + Long.toString(System.currentTimeMillis()) + ".csv";
			out = FileSystems.getDefault().getPath("C:","GenData",filename);

			N 		= Double.parseDouble(args[2]);
			S 		= Double.parseDouble(args[3]);
			E 		= Double.parseDouble(args[4]);
			I	 	= Double.parseDouble(args[5]);

			BETA 	= Double.parseDouble(args[6]);
			H 		= Double.parseDouble(args[7]);
			DELT 	= Double.parseDouble(args[8]);
			GAM 	= Double.parseDouble(args[9]);

			V 		= Double.parseDouble(args[10]);
			P_V 	= Double.parseDouble(args[11]);
			M 		= Double.parseDouble(args[12]);
			P_M 	= Double.parseDouble(args[13]);
		}

		Charset charset = Charset.forName("US-ASCII");
		BufferedWriter writer = Files.newBufferedWriter(out, charset);


		if (!MED_VACC && !GRAPH_GEN) {
			SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT);
			writer.write("Iteration #,S,E,I,R,R_0\n");

			int i = 0;
			mainChain.iterate();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (!USE_ITERS ? (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE)
					: (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE) && i <= ITERS) {

				writer.write(i+"," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
						"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + ", " + mainChain.getR_0() + "\n");

				mainChain.iterate();
				i++;
				tempMat = mainChain.getX_i().get(i);
			}
		}

		else if (!GRAPH_GEN) {
			SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT,
					V, P_V, M, P_M);
			writer.write("Iteration #,S,E,I,R,U,M,V,R_0\n");

			int i = 0;
			mainChain.iterMed();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (!USE_ITERS ? (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE)
					: (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE) && i <= ITERS) {

				writer.write(i + "," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
						"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + "," + tempMat.get(4,0) + ", " + mainChain.getM() + ", " + 
						mainChain.getV() + ", " + mainChain.getR_0() + "\n");

				mainChain.iterMed();
				i++;
				tempMat = mainChain.getX_i().get(i);
			}
		}
		else { // This section is for creating datasets for use in MATLAB, called when GRAPH_GEN = true
			if (!MED_VACC) {
				SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT);
				writer.write("Iteration #,S,E,I,R,R_0\n");

				int i = 0;
				mainChain.iterate();
				Matrix tempMat = mainChain.getX_i().get(i);
				while (i < ITERS) {
					writer.write(i+"," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
							"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + ", " + mainChain.getR_0() + "\n");
					i++;

					if (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE) {
						mainChain.iterate();
						tempMat = mainChain.getX_i().get(i);
					}


				}
			} else {
				SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT,
						V, P_V, M, P_M);
				writer.write("Iteration #,S,E,I,R,U,M,V,R_0\n");

				int i = 0;
				mainChain.iterMed();
				Matrix tempMat = mainChain.getX_i().get(i);
				while (i < ITERS) {
					writer.write(i + "," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
							"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + "," + tempMat.get(4,0) + ", " + mainChain.getM() + ", " + 
							mainChain.getV() + ", " + mainChain.getR_0() + "\n");
					i++;

					if (tempMat.get(1, 0) > TOLERANCE || tempMat.get(2, 0) > TOLERANCE) {
						mainChain.iterMed();
						tempMat = mainChain.getX_i().get(i);
					}
				}
			}
		}
		writer.close();
	}
}
