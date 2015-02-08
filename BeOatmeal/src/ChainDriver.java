import java.io.BufferedWriter;
import java.nio.charset.*;
import java.nio.file.*;

public class ChainDriver {

	private static final boolean MED_VACC = false; 
	
	private static double N 	= 270114;
	private static double S 	= 0.99574;
	private static double E 	= 0.00123;
	private static double I 	= 0.00123;
	
	private static double BETA 	= 0.2;
	private static double H 	= 1;
	private static double DELT 	= 0.2;
	private static double GAM 	= 0.143;
	
	private static double V 	= 1000;
	private static double P_V 	= 0.3;
	private static double M 	= 15;
	private static double P_M 	= 0.35;
	
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
			
		if (!MED_VACC) {
			BufferedWriter writer = Files.newBufferedWriter(out, charset);
		
			SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT);
			writer.write("Iteration #,S,E,I,R,R_0\n");
			
			int i = 0;
			mainChain.iterate();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (tempMat.get(1, 0) > 0.0001 || tempMat.get(2, 0) > 0.0001) {
				
				writer.write(i+"," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
						"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + ", " + mainChain.getR_0() + "\n");

				mainChain.iterate();
				i++;
				tempMat = mainChain.getX_i().get(i);
			}
			 
		    writer.close();
		}
		
		else {
			BufferedWriter writer = Files.newBufferedWriter(out, charset);
			
			SEIRChain mainChain = new SEIRChain(N, S, E, I, BETA, H, GAM, DELT,
					V, P_V, M, P_M);
			writer.write("Iteration #,S,E,I,R,U,M,V,R_0\n");
			
			int i = 0;
			mainChain.iterMed();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (tempMat.get(1, 0) > 0.0001 || tempMat.get(2, 0) > 0.0001) {
				
				writer.write(i + "," + tempMat.get(0,0) + "," + tempMat.get(1,0) +
						"," + tempMat.get(2,0) + "," + tempMat.get(3,0) + "," + tempMat.get(4,0) + ", " + mainChain.getM() + ", " + 
						mainChain.getV() + ", " + mainChain.getR_0() + "\n");
	
				mainChain.iterMed();
				i++;
				tempMat = mainChain.getX_i().get(i);
			}
			 
		    writer.close();
		}
	}
}
