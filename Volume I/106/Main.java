
import java.util.*;
import java.io.*;
import java.math.*;

class Main {

	public void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
				String[] tokens = cleanLine.split("\\s+");

				if (tokens.length == 1) {
					try {
						int number = Integer.parseInt(tokens[0]);
						if (number > 0 && number <= 1000000)
							solve(number);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public int gcd(int a, int b) {
  		while (a != 0 && b != 0) {
     		int c = b;
     		b = a % b;
     		a = c;
  		}
  		return a + b;
	}

	public void solve(int num) {
		int[] usage = new int[num];
		int triples = 0;
		int unused = 0;

		int m = (int)Math.sqrt(num);
		if (m * m < num)
			m ++;

		for (int r = 1; r <= m; r++) {
			int up = Math.min((num - r * r), r - 1);
			for (int s = 1; s <= up; s++) {
				int x = r * r - s * s;
				int y = 2 * r * s;
				int z = r * r + s * s;

				if ((x * x + y * y == z * z) && (z <= num)) {
					if (gcd(x,y) == 1) {
						triples ++;
						for (int k = 1; k * z <= num; k++) {
							usage[k * x - 1] = usage[k * y - 1] = usage[k * z - 1] = 1;
						}
					}
				}
			}
		}

		for (int i = 0; i < num; i++) {
			if (usage[i] == 0) unused ++;
		}
		System.out.println(triples + " " + unused);
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.parseInput();
	}
}