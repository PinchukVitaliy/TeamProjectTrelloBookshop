package ua.step.bookshop.models;

import java.util.ArrayList;
import java.util.List;

public class MoneyList {
	private static List<Money> money = new ArrayList<Money>();
	private static double usd = 26.10000;

	static {
		money.add(new Money("UAH", "UAH", "1", "1"));
		money.add(new Money("USD", "UAH", "26.10000", "26.50000"));
		money.add(new Money("EUR", "UAH", "29.00000", "29.65000"));
		money.add(new Money("RUR", "UAH", "0.37000", "0.41000"));
		money.add(new Money("BTC", "USD", "7636.0746", "8439.8720"));
	}

	public static String calcPrice(String currency, Integer price) {
		String stringPrice = "";

		if (currency.equals("BTC")) {
			double buy = 7636.0746;
			double buy1 = price / 100 / buy;
			buy1 = buy1 / usd;
			stringPrice = String.format("%.5f ", buy1) + " ฿";
			return stringPrice;
		}
		if (currency.equals("UAH")) {
			double buy = 1.0;
			double buy1 = price / 100 / buy;
			stringPrice = String.format("%.2f ", buy1) + " ₴";
			return stringPrice;
		}
		if (currency.equals("USD")) {
			double buy = 26.10000;
			double buy1 = price / 100 / buy;
			stringPrice = String.format("%.2f ", buy1) + " $";
			return stringPrice;
		}

		if (currency.equals("EUR")) {
			double buy = 29.00000;
			double buy1 = price / 100 / buy;
			stringPrice = String.format("%.2f ", buy1) + " €";
			return stringPrice;
		}

		if (currency.equals("RUR")) {
			double buy = 0.37000;
			double buy1 = price / 100 / buy;
			stringPrice = String.format("%.2f ", buy1) + " ₽";
			return stringPrice;
		}

		return stringPrice + "нет цены";
	}
}