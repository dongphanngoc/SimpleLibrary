package com.simplelibrary.algorithm;

import java.util.*;

public class AdvanceStringDS {
	public static int[] suffixArray(CharSequence S) {
		int n = S.length();
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = n - 1 - i;
		Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));
		int[] sa = new int[n];
		int[] rank = new int[n];
		for (int i = 0; i < n; i++) {
			sa[i] = order[i];
			rank[i] = S.charAt(i);
		}
		for (int len = 1; len < n; len *= 2) {
			int[] c = rank.clone();
			for (int i = 0; i < n; i++) {
				rank[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n
						&& c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? rank[sa[i - 1]] : i;
			}
			int[] cnt = new int[n];
			for (int i = 0; i < n; i++)
				cnt[i] = i;
			int[] s = sa.clone();
			for (int i = 0; i < n; i++) {
				int s1 = s[i] - len;
				if (s1 >= 0)
					sa[cnt[rank[s1]]++] = s1;
			}
		}
		return sa;
	}

	public static int[] lcp(int[] sa, CharSequence s) {
		int n = sa.length;
		int[] rank = new int[n];
		for (int i = 0; i < n; i++)
			rank[sa[i]] = i;
		int[] lcp = new int[n - 1];
		for (int i = 0, h = 0; i < n; i++) {
			if (rank[i] < n - 1) {
				for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length()
						&& s.charAt(i + h) == s.charAt(j + h); ++h)
					;
				lcp[rank[i]] = h;
				if (h > 0)
					--h;
			}
		}
		return lcp;
	}

	/**
	 * Build z table
	 * 
	 * @param s
	 * @return
	 */
	public static int[] zFucntion(String s) {
		int n = s.length();
		int z[] = new int[n];
		int R = 0;
		int L = 0;

		for (int i = 1; i < n; i++) {
			z[i] = 0;
			if (R > i) {
				z[i] = Math.min(R - i, z[i - L]);
			}
			while (i + z[i] < n && s.charAt(i + z[i]) == s.charAt(z[i])) {
				z[i]++;
			}
			if (i + z[i] > R) {
				L = i;
				R = i + z[i];
			}
		}
		z[0] = n;
		return z;
	}

	private static List<String> findAllSuffix(String s, int miniminLength) {
		List<String> result = new ArrayList<>();
		for (int i = miniminLength + 1; i < s.length() + 1; i++) {
			result.add(s.substring(0, i));
		}
		return result;
	}

	public static int solution(String E, String L) {
		// write your code in Java SE 8
		int resultCost = 0;

		String[] es = E.split(":");
		if (es.length <2) {
			throw new RuntimeException("Invalid input");
		}
		int startHour = Integer.parseInt(es[0]);
		int startMinute = Integer.parseInt(es[1]);
		String[] ls = L.split(":");
		
		if (ls.length <2) {
			throw new RuntimeException("Invalid input");
		}
		int leftHour = Integer.parseInt(ls[0]);
		int leftMinute = Integer.parseInt(ls[1]);

		int total = leftHour - startHour;
		if ((total < 0) || ((total==0) && (leftMinute < startMinute))) {
			throw new RuntimeException("Invalid input");
		}

		if (total == 0) {
			if (leftMinute > startMinute) {
				resultCost = resultCost + 2 + 3;
			}
		} else if (total == 1) {
			resultCost = resultCost + 2 + 3;
			if (leftMinute > startMinute) {
				resultCost = resultCost + 4;
			}
		} else {
			resultCost = resultCost + 2 + 3 + 4 * (total - 1);
			if (leftMinute > startMinute) {
				resultCost = resultCost + 4;
			}
		}
		return resultCost;
	}

	public static int solution1(int[] A) {
		// write your code in Java SE 8

		if (A.length >= 23) {
			return 25;// Best is to buy month
		} else {
			return chooseWeekOrDayTicket(A);
		}

	}

	private static int chooseWeekOrDayTicket(int[] A) {
		int totalCost = 0;
		int start = A[0];
		int countDayinWeek = 1;
		for (int i = 1; i < A.length; i++) {
			if ((A[i] - start) < 7) {
				countDayinWeek++;
			} else if ((A[i] - start) >= 7) {
				if (countDayinWeek <= 3) {
					// Buy Day-Ticket will be the best
					totalCost = totalCost + 2 * countDayinWeek;
				} else if (countDayinWeek > 3) {
					// Buy Week-Ticket will be the best
					totalCost = totalCost + 7;
				}
				start = A[i];
				countDayinWeek = 1;
			}
		}
		if ((A[A.length - 1] - start) < 7) {
			if (countDayinWeek <= 3) {
				// Buy Day-Ticket will be the best
				totalCost = totalCost + 2 * countDayinWeek;
			} else if (countDayinWeek > 3 || countDayinWeek <= 6) {
				// Buy Week-Ticket will be the best
				totalCost = totalCost + 7;
			} else {
				totalCost = totalCost + 7 + 2;
			}
		}
		return totalCost;
	}

	// Usage example
	public static void main(String[] args) {
		
		System.out.println(solution("9:34","9:35"));

	}
}