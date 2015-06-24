package marketClient;

public class MessageBean {
	//sample message:
	// "{"symbol":"AUD/USD","entryType":"0","price":"0.78288"}"

	private String symbol = "";
	private String entryType = "";
	private String price = "";

	public MessageBean() {

	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getEntryType() {
		return this.entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	
	public String getPrice(){
		return this.price;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
}
