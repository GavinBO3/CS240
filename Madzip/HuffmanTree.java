package madzip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Used for huffman encoding of files.
 * Compresses a file by sorting bytes by frequencies, with more frequent bytes having shorter encodings.
 */
public class HuffmanTree {
	HashMap<Byte, Integer> frequencies;
	HuffmanNode root;
	
	
	
	public HuffmanTree(HashMap<Byte, Integer> frequencies) {
		this.frequencies = frequencies;
	}
	
	public HuffmanTree() {}
	
	public void createFrequencyMap(byte[] inputBytes) {
		frequencies = new HashMap<Byte, Integer>();
		
		for (byte b : inputBytes) {
			if (frequencies.containsKey(b)) {
				frequencies.put(b, frequencies.get(b) + 1);
				System.out.println("incremented " + b + "! It now has a frequency of " 
						+ frequencies.get(b));
				
			} else {
				frequencies.put(b, 1);
				System.out.println("added " + b);
				
			}
		}
	}
	
	public void createHuffmanTree() {
		
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
		
		if (queue.size() == 1) {
			HuffmanNode theOne = queue.remove();
			root = new HuffmanNode(theOne, theOne, 1, (byte)1);
		} else {
		
		for (Byte b : frequencies.keySet()) {
			HuffmanNode temp = new HuffmanNode(null, null, frequencies.get(b), b);
			queue.add(temp);
			
	
		}
		
		
		while (queue.size() > 1) {
			HuffmanNode l = queue.remove();
			HuffmanNode r = queue.remove();
			HuffmanNode newNode = new HuffmanNode(l, r, l.frequency + r.frequency, (byte) (l.value + r.value));
			System.out.println("Added " + l.value + "(frequency " + frequencies.get(l.value) +
					") and " + r.value + "(frequency " + frequencies.get(r.value) + ""
							+ ") to a new node!(frequency " + newNode.frequency+ ")");
			
			
			queue.add(newNode);
		}
		try {
			root = queue.remove();
		} catch (NoSuchElementException e) {
			System.out.println("The file is empty!");
		}
		}
	}
	
	/**
	 * Traverses the tree, searching for the specified byte
	 */
	public BitSequence traverse(byte searchFor) {
		String s = traverse(root, searchFor, "");
		BitSequence toReturn =  new BitSequence();
		for (int c : s.toCharArray()) {
			if (c == '0') {
				toReturn.appendBit(0);
			} else {
				toReturn.appendBit(1);
			}
		}
		System.out.println("Before: " + searchFor);
		System.out.println("After: " + toReturn);
		return toReturn;
		
	}
	
	/**
	 * Recursive method that checks the current node and calls itself on that node's children until the specified byte is found
	 */
	private String traverse(HuffmanNode currNode, byte searchFor, String s) {
		if (currNode.isLeaf()) {
			if (currNode.value == searchFor) {
				return s;
			} else {
				throw new IllegalStateException("Wrong leaf");
			}
		} else {
			try {
				return traverse(currNode.left, searchFor, s + "0");
			} catch (IllegalStateException e) {
				try {
					return traverse(currNode.right, searchFor, s + "1");
				}
				catch (IllegalStateException f) {
					throw new IllegalStateException("Not in children of this node");
				}
			}
		}
	}
	
	/**
	 * Converts the input sequence into a shortened huffman encoded sequence
	 */
	public BitSequence createEncodedSequence(byte[] inputBytes) {
		BitSequence toReturn = new BitSequence();
		for(byte pre : inputBytes) {
			toReturn.appendBits(traverse(pre));
		}
		return toReturn;
	}
	
	/**
	 * Decodes a sequence of bits using the previously created huffman tree
	 */
	public byte[] decodeSequence(BitSequence inputBits) throws IOException {
		ArrayList<Byte> decodedBytes = new ArrayList<Byte>();
		System.out.println("Before decoding: " + inputBits);
	
		HuffmanNode currNode = root;
		if (currNode == null) {
			System.out.println("There is no huffman tree!");
			return new byte[0];
		}
		
		for (int i = 0; i < inputBits.length(); i++) {
			if (currNode.isLeaf()) {
				decodedBytes.add(currNode.value);
				System.out.println("Bit decoded: " + currNode.value);
				currNode = root;
			}
			
			if (inputBits.getBit(i) == 0) {
				currNode = currNode.left;
					
			} else {
				currNode = currNode.right;
					
			}

			System.out.println("current bit: " + inputBits.getBit(i));
		}
		if (currNode.isLeaf()) {
			decodedBytes.add(currNode.value);
			System.out.println("Bit decoded: " + currNode.value);
			currNode = root;
		}
		
		byte[] toReturn  = new byte[decodedBytes.size()];
		Iterator<Byte> f = decodedBytes.iterator();
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = f.next().byteValue();
		}
		return toReturn;
	}
}


/**
 * A class to represent the nodes in the tree. Should not have both a value and children, only one
 * or the other.
 * 
 *
 */
class HuffmanNode implements Comparable<HuffmanNode> {

	HuffmanNode left;
	HuffmanNode right;
	int frequency;
	Byte value;
	
	public HuffmanNode(HuffmanNode left, HuffmanNode right, int frequency, Byte value) {
		this.left = left;
		this.right = right;
		this.frequency = frequency;
		this.value = value;
	}
	
	
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	public int getFrequency() {
		return frequency;
	}


	@Override
	public int compareTo(HuffmanNode o) {
		HuffmanNode that = (HuffmanNode) o;
		
		if (this.frequency < that.frequency) {
			return -1;
		} else if (this.frequency > that.frequency) {
			return 1;
		} else {
			if (this.value < that.value) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	
}

