import java.io.BufferedWriter;
import java.nio.charset.*;
import java.nio.file.*;

public class ChainDriver {

	private static final boolean MED_VACC = true; 
	
	public static void main(String[] args) throws Exception {
		String filename = "SEIR Generated Data for ";
		filename += Long.toString(System.currentTimeMillis());
		filename += ".csv";
		Path out = FileSystems.getDefault().getPath("C:","GenData",filename);
		
		Charset charset = Charset.forName("US-ASCII");
			
		if (!MED_VACC) {
			BufferedWriter writer = Files.newBufferedWriter(out, charset);
		
			SEIRChain mainChain = new SEIRChain(270114, 0.99574, 0.00123, 0.00123, 0.2, 1, 0.143, 0.2);
			writer.write("Iteration #,S,E,I,R,R_0\n");
			
			int i = 0;
			mainChain.iterate();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (tempMat.get(1, 0) > 0.0001 && tempMat.get(2, 0) > 0.0001) {
				
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
			
			SEIRChain mainChain = new SEIRChain(270114, 0.99574, 0.00123, 0.00123, 0.2, 1, 0.143, 0.2,
					1000, .33, 15, .10);
			writer.write("Iteration #,S,E,I,R,U,M,V,R_0\n");
			
			int i = 0;
			mainChain.iterMed();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (tempMat.get(1, 0) > 0.0001 && tempMat.get(2, 0) > 0.0001) {
				
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
