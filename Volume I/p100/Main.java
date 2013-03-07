
import java.io.*;
import java.util.*;

class Main {
	static final int MAX_SIZE = 1000000;
	static final long[] cache = new long[MAX_SIZE];

	public void process() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean hasValues = true;
		long min, max;

		String line;
		try {
	        while ((line = reader.readLine()) != null) {
				min = max = 0;
				
				String REGEX_WHITESPACE = "\\s+";
            	String cleanLine = line.trim().replaceAll(REGEX_WHITESPACE, " ");
            	String[] tokens = cleanLine.split(REGEX_WHITESPACE);

            	if (tokens.length == 2) {
               		min = new Long(tokens[0]).longValue();
               		max = new Long(tokens[1]).longValue();
               		long maxCycle = maxCycle(min, max);
					System.out.println(min + " " + max + " " + maxCycle);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
			System.exit(0);
		}
	}

	protected long maxCycle(long min, long max) {
		if (min > max) {
			long tmp = max;
			max = min;
			min = tmp;
		}

		long maxCycle = -1;
		for (long a = min; a <= max; a ++) {
			long m = -1;
			
			if (a < MAX_SIZE && cache[(int)a] > 0) {
				m = cache[(int)a];
			} else {
				m = cycleLength(a, 1);
				if (a < MAX_SIZE)
					cache[(int)a] = m;
			}

			if (m > maxCycle) maxCycle = m;
		}

		return maxCycle;
	}

	protected long cycleLength(long j, long c) {
		long current = j;
		long count = 1;

		if (j == 0 || j == 1) return j;

		while (current > 1) {
			if (current % 2 == 0) current = current / 2;
			else current = current * 3 + 1;
			count ++;
		}

		return count;
	}

	public static void main(String[] args) {
		Main s = new Main();
		s.process();
	}
}