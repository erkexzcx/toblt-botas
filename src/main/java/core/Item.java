package core;

public class Item {

	private final String id;
	private final String title;
	private final String buyUrl;
	private final String sellUrl;

	public Item(String id, String title, String buyUrl, String sellUrl) {
		this.id = id;
		this.title = title;
		this.buyUrl = buyUrl;
		this.sellUrl = sellUrl;
	}

	public Item(String id) {
		this.id = id;
		this.title = null;
		this.buyUrl = null;
		this.sellUrl = null;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getBuyUrl() {
		return buyUrl;
	}

	public String getSellUrl() {
		return sellUrl;
	}

}
