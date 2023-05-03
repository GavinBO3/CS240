package madzip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Decompresses a file using the huffman tree used to encode it.
 */
public class MadUnzip {
	
	HuffmanSave hs;
	BitSequence inputBytes;
	
	public void readFile(File f) throws ClassNotFoundException, IOException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("No file found!");
		}
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		
		try {
			hs = (HuffmanSave) ois.readObject();
			inputBytes = hs.getEncoding();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public static void main(String[] args) {
		System.out.println("start");
		MadUnzip m = new MadUnzip();
		HuffmanTree tree = new HuffmanTree();
		File f = new File("");
		if (args.length != 2) {
			System.out.println("Please enter the file you wish to unzip"
					+ " followed by the destination file!");
		} else {
			f = new File(args[0]);
		}
		
		try {
			m.readFile(f);
			tree = new HuffmanTree(m.hs.getFrequencies());
			tree.createHuffmanTree();
			byte[] bytes = tree.decodeSequence(m.inputBytes);
			FileOutputStream fos = new FileOutputStream(args[1]);
			
			fos.write(bytes);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
