import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;

public class ChainDriver {

	public static void main(String[] args) throws Exception {
		String filename = "SEIR Generated Data for ";
		filename += Long.toString(System.currentTimeMillis());
		filename += ".csv";
		Path out = FileSystems.getDefault().getPath("C:","GenData",filename);
		
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedWriter writer = Files.newBufferedWriter(out, charset)) {
		    
			SEIRChain mainChain = new SEIRChain(270114, 0.99574, 0.00123, 0.00123, 0.2, 1, 0.143, 0.2);
			System.out.println("eehhhh");
			
			int i = 0;
			mainChain.iterate();
			Matrix tempMat = mainChain.getX_i().get(i);
			while (tempMat.get(0, 1) > 0.0001 && tempMat.get(0, 2) > 0.0001) {
				
				writer.write(i);
				System.out.println("yo");
				
				mainChain.iterate();
				i++;
				tempMat = mainChain.getX_i().get(i);
			}
			
		    
		    
		    writer.close();
		} /*catch (IOException x) {
		    System.err.format("Exception: %s%n", x);
		}*/
	}
}
