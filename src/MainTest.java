import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileInputStream input = new FileInputStream(new File("test.txt"));
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
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
