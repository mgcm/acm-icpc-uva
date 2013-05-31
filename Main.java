
import java.util.*;
import java.io.*;

class Main {

	public Main() {

	}

	public void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
				String[] tokens = cleanLine.split("\\s+");

				/*
				if (tokens.length == 1) {
				}
				*/
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public void solve() {

	}

	public static void Main(String[] args) {
		Main m = new Main();
		m.parseInput();
		m.solve();
	}
}