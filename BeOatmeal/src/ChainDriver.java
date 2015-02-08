import java.io.BufferedWriter;
import java.nio.charset.*;
import java.nio.file.*;

public class ChainDriver {

	private static final boolean MED_VACC = false; 
	
	private static final double N 		= 270114;
	private static final double S 		= 0.99574;
	private static final double E 		= 0.00123;
	private static final double I 		= 0.00123;
	
	private static final double BETA 	= 0.2;
	private static final double H 		= 1;
	private static final double DELT 	= 0.2;
	private static final double GAM 	= 0.143;
	
	private static final double V 		= 1000;
	private static final double P_V 	= 0.3;
	private static final double M 		= 15;
	private static final double P_M 	= 0.35;
	
	public static void main(String[] args) throws Exception {
		String filename = "SEIR Generated Data for ";
		filename += Long.toString(System.currentTimeMillis());
		filename += ".csv";
		Path out = FileSystems.getDefault().getPath("C:","GenData",filename);
		
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
