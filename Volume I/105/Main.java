
import java.util.*;
import java.io.*;

class Main {
	int minX = 10000;
	int maxX = -10000;
	int buildings = 0;
	int[] data;

	public Main() {
		data = new int[50000*3];
	}

	public void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			int b = 0;
			while ((line = reader.readLine()) != null) {
				String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
				String[] tokens = cleanLine.split("\\s+");

				if (tokens.length == 3) {
					data[b*3] = Integer.parseInt(tokens[0]);
					data[b*3+1] = Integer.parseInt(tokens[1]);
					data[b*3+2] = Integer.parseInt(tokens[2]);
					if (data[b*3] < minX) minX = data[b*3];
					if (data[b*3+2] > maxX) maxX = data[b*3+2];
					b++;
				}
				buildings = b;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public void skyline_heightmap() {
		int[] height = new int[10000];

		for (int b = 0; b < buildings; b++) {
			int x1 = data[b*3];
			int h = data[b*3+1];
			int x2 = data[b*3+2];
			for (int i = x1; i < x2; i++)
				height[i] = Math.max(height[i], h);
		}

		for (int i = 1; i < 10000; i++) {
			if (height[i] != height[i-1]) {
				if (i > 1) System.out.print(" ");
            	System.out.print(i + " " + height[i]);
            }
		}
		System.out.println();
	}

	public void skyline_raster() {
		int currHeight = -1;
		boolean addSpace = false;

		for (int i = minX; i <= maxX; i++) {
			int bestHeight = 0;

			for (int b = 0; b < buildings; b++) {
				int x1 = data[b*3];
				int h = data[b*3+1];
				int x2 = data[b*3+2];

				if (i >= x1 && i < x2) {
					if (bestHeight < h) {
						bestHeight = h;
					}
				}
			}
			if (currHeight != bestHeight) {
				if (addSpace) System.out.print(" ");

				System.out.print(i + " ");
				System.out.print(bestHeight);
				currHeight = bestHeight;
				addSpace = true;
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.parseInput();
		//m.skyline_raster();
		m.skyline_heightmap();
	}
}