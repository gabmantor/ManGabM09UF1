package cat.inspedralbes.mangab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class ManGabA01File {

	public static byte[] readFile(File file) {
		
		byte[] data = new byte[(int) file.length()];
		byte[] res = null;
		try (FileInputStream in = new FileInputStream(file)){
			
		int read = in.read(data);
		
		while(read > 0){
		
		res = data;
		read = in.read(data);
		
		}
		}catch (Exception e) { System.err.println("Error while reading data: " + e); }
		
		return res;
		
		}
		
	public static void writeFile(byte[] out, String fileName) {
		
		System.out.println(out[0]);
		
		try (FileOutputStream outFile = new FileOutputStream(fileName)) {
		outFile.write(out);
		
		}catch (Exception e) { 
			
			System.err.println("Error while writing data: " + e); }
		}
		
				
	public static void wipe(File f) throws Exception {
		
		try (RandomAccessFile rand = new RandomAccessFile(f, "rw")){
		
			for (int i=0; i< rand.length(); i++)
			rand.write(0);
		}
		
		}	
}