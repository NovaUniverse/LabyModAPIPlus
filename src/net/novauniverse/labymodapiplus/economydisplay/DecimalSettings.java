package net.novauniverse.labymodapiplus.economydisplay;

public class DecimalSettings {
	private String format;
	private int divisor;

	public DecimalSettings(String format, int divisor) {
		this.format = format;
		this.divisor = divisor;
	}

	public int getDivisor() {
		return divisor;
	}

	public String getFormat() {
		return format;
	}

	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}