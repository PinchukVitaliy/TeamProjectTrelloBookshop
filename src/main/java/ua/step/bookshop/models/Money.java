package ua.step.bookshop.models;

public class Money {
	private String currency;
	private String baseCurrency;
	private String buy;
	private String sale;

	public Money(String currency, String baseCurrency, String buy, String sale) {
		this.currency = currency;
		this.baseCurrency = baseCurrency;
		this.buy = buy;
		this.sale = sale;
	}

	public Money() {
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String baseCurrency) {
		this.currency = currency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBase_ccy(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}
}
