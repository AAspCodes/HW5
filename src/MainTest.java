import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		testBuildFirstTree();
//		testScannerConstructor();
		testDecoder();
		
	}
	
	public static void testBuildFirstTree() {
		try {
			FileInputStream input = new FileInputStream(new File("short.text"));
			int[] count = new int[256];
			for (;;) {
				int n = input.read();
				if (n == -1)
					break;
				count[n]++;
			}
			input.close();

			// build tree, open output file, print codes
			HuffmanTree t = new HuffmanTree(count);
			PrintStream output = new PrintStream(new File("short2.code"));
			t.write(output);
			output.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void testScannerConstructor() {
		try {
			Scanner scan = new Scanner(new File("short2.code"));
			HuffmanTree t = new HuffmanTree(scan);
			PrintStream output = new PrintStream(new File("short3.code"));
			t.write(output);
			output.close();
		} catch (IOException e){
			System.out.println(e);
			
		}
	}
	
	
	public static void testDecoder() {
		try {
			Scanner codeInput = new Scanner(new File("hamlet.code"));
			HuffmanTree t = new HuffmanTree(codeInput);

			// open encoded file, open output, decode
			BitInputStream input = new BitInputStream("hamlet.bin");
			PrintStream output = new PrintStream(new File("test.txt"));
			t.decode(input, output, 256);
			output.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("done");
	}

}
