package com.simplelibrary.algorithm;

public class OptimalAlgorithm {

	public static int maximumProfitBuySellStock(int[] prices) {

		if (prices.length < 2) {
			return 0;
		}
		int[] maxOfSubArray = new int[prices.length];
		int max = prices[prices.length - 1];
		maxOfSubArray[prices.length - 1] = max;
		for (int i = prices.length - 2; i >= 0; i--) {
			if (max < prices[i]) {
				max = prices[i];
			}
			maxOfSubArray[i] = max;
		}
		int buyDayResult = -1;
		int sellDatResult = -1;

		int buy = 0;
		int maxBenefit = 0;
		for (int i = 1; i < prices.length; i++) {
			int temmaxBenefit = maxOfSubArray[i] - prices[buy];
			if (temmaxBenefit > maxBenefit) {
				buyDayResult = buy;
				maxBenefit = temmaxBenefit;
			}
			buy = i;
		}

		return maxBenefit;
	}
}
