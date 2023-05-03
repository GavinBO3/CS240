package madzip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Compresses a file using huffman ecoding.
 */
public class MadZip {

	byte[] inputBytes;
	
	/**
	 * Reads the file into the inputBytes array.
	 */
	public void readFile(File f) throws FileNotFoundException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("No file found!");
		}
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		try {
			inputBytes = bis.readAllBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println("start");
		MadZip m = new MadZip();
		HuffmanTree tree = new HuffmanTree();
		File f = new File("");
		if (args.length != 2) { // The first argument should be the input file and the second the output
			System.out.println("Please enter the file you wish to zip follow by the destination file!");
		} else {
			f = new File(args[0]);
			
			
		}
		
		try {
			m.readFile(f);
			tree.createFrequencyMap(m.inputBytes);
			tree.createHuffmanTree();
			BitSequence sequence = tree.createEncodedSequence(m.inputBytes);
			System.out.println("Sequence: " + sequence);
			
			FileOutputStream fos = new FileOutputStream(args[1]);
			ObjectOutputStream bos = null;
			try {
				bos = new ObjectOutputStream(fos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HuffmanSave save = new HuffmanSave(sequence, tree.frequencies);
			
			try {
				bos.writeObject(save);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) { // If it can't read the input file or write to the output file
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
