import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * @author Anthony Asp, Michael Cho
 * 
 * HW5 Project for CSC 143.
 *
 */
public class HuffmanTree2 {
	private HuffmanNode2 root;

	/**
	 * Constructs a Huffman tree using the given array of frequencies where count[i]
	 * is the number of occurrences of the character with ASCII value i.
	 * 
	 * @param count int array, An array of character frequencies, where their ascii
	 *              value is their index.
	 */
	public HuffmanTree2(int[] count) {

		Queue<HuffmanNode2> nodeQ = new PriorityQueue<HuffmanNode2>();

		// Assign non zero frequency values from the count array to the priority queue.
		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				nodeQ.add(new HuffmanNode2(i, count[i]));
			}
		}

		// Add end of file char.
		nodeQ.add(new HuffmanNode2(count.length, 1));
		
		
		HuffmanNode2 leaf1, leaf2, branch;
		// Build tree
		while (nodeQ.size() > 1) {
			leaf1 = nodeQ.remove();
			leaf2 = nodeQ.remove();
			branch = new HuffmanNode2(-1, leaf1.frequency + leaf2.frequency);
			
			if (leaf1.frequency <= leaf2.frequency) {
				branch.left = leaf1;
				branch.right = leaf2;
			} else {
				branch.left = leaf2;
				branch.right = leaf1;
			}

			nodeQ.add(branch);
		}

		// assign tree
		root = nodeQ.remove();

	}

	/**
	 * Constructs a Huffman tree from the Scanner. Assumes the Scanner contains a
	 * tree description in standard format.
	 * 
	 * @param codeInput
	 */
	public HuffmanTree2(Scanner codeInput) {

		root = new HuffmanNode2();
		HuffmanNode2 node;
		int asciiVal;
		char[] code;
		
		while (codeInput.hasNextLine()) {
			// read values for code file
			asciiVal = Integer.parseInt(codeInput.nextLine());
			code = codeInput.nextLine().toCharArray();
			
			// assign root as starting position
			node = root;

			for (char direction : code) {

				if (direction == '0') {
					// go left
					if (node.left == null) {
						// make a new branch and continue
						node.left = new HuffmanNode2();
					}
					// traverse to that branch
					node = node.left;

				} else {
					// go right
					if (node.right == null) {
						// make a new branch
						node.right = new HuffmanNode2();
					}
					// traverse to the right branch
					node = node.right;
				}
			}

			node.asciiVal = asciiVal;
		}
	}
	
	/**
	 * Constructs a Huffman tree from the given input stream.
	 * Assumes that the standard bit representation has been
	 * used for the tree.
	 * 
	 * @param input
	 * 			BitInputStream, 
	 */
	public HuffmanTree2(BitInputStream input) {
		
	}
	
	/**
	 * Assigns codes for each character of the tree.
	 * Assumes the array has null values before the method is called.
	 * Fills in a String for each character in the tree indicating
	 * its code.
	 * 
	 * @param codes
	 * 			String[],
	 */
	public void assign(String[] codes) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		root.traversalWrite(map, "");
		
		for (Integer asciiVal: map.keySet()) {
			codes[asciiVal.intValue()] = map.get(asciiVal);
		}
	}
	
	
	/**
	 * Writes the current tree to the output stream using
	 * the standard bit representation.
	 * 
	 * @param output
	 * 			BitOutputStream,
	 */
	public void writeHeader(BitOutputStream output) {
		
	}

	/**
	 * Reads bits from the given input stream and writes the corresponding
	 * characters to the output. Stops reading when it encounters a character with
	 * value equal to eof. This is a pseudo-eof character, so it should not be
	 * written to the output file. Assumes the input stream contains a legal
	 * encoding of characters for this tree’s Huffman code.
	 * 
	 * @param input
	 * @param output
	 * @param eof
	 */
	public void decode(BitInputStream input, PrintStream output, int eof) {
		HuffmanNode2 node;
		while (true) {
			// set node to root
			node = root;
			// while node is on a branch, exit when a leaf is found
			while (node.asciiVal == -1) {
				// traverse left or right depending on the bit value
				node = (input.readBit() == 0) ? node.left : node.right;
			}

			if (node.asciiVal == eof) {
				// break if the end of the file is found
				break;
			} else {
				// write out the ascii value in the leaf
				output.write(node.asciiVal);
			}
		}
	}

	/**
	 * Writes the current tree to the given output stream in standard format.
	 * @param output
	 * 			PrintStream object for writing to a file.
	 */
	public void write(PrintStream output) {
		root.traversalWrite(output, "");
	}
	
	/**
	 * Node class
	 * Use to build the tree needed in a HuffmanTree.
	 */
	private class HuffmanNode2 implements Comparable<HuffmanNode2> {
		private HuffmanNode2 left, right;
		private int asciiVal, frequency;

		private HuffmanNode2(int asciiVal, int frequency) {
			this.asciiVal = asciiVal;
			this.frequency = frequency;
		}

		private HuffmanNode2() {
			this(-1, 0);
		}
		
		/*
		 * return values for debugging purpose.
		 */
		public String toString() {
			return asciiVal + " : " + frequency;
		}

		@Override
		public int compareTo(HuffmanNode2 that) {
			return this.frequency - that.frequency;
		}
		
		/**
		 * Traverse the tree recursively. When a leaf is found, write out
		 * the asciiVal and the path from the root to the leaf.
		 * 
		 * @param output
		 * 		PrintStream object, for writing to file.
		 * @param code
		 * 		String, 0s and 1s, the path from the root to the current position.
		 */
		private void traversalWrite(PrintStream output, String code) {
			if (asciiVal >= 0) {
				output.println(asciiVal);
				output.println(code);
			} else {
				if (left != null) {
					left.traversalWrite(output, code + "0");
				}
				if (right != null) {
					right.traversalWrite(output, code + "1");
				}
			}
		}
		
		
		
		private void traversalWrite(Map<Integer,String> map, String code) {
			if (asciiVal >= 0) {
				map.put(asciiVal, code);
			} else {
				if (left != null) {
					left.traversalWrite(map, code + "0");
				}
				if (right != null) {
					right.traversalWrite(map, code + "1");
				}
			}
		}
	}

}
