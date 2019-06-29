package core;

public class Item {

	private final String id;
	private final String title;
	private final String category;
	private final int requiredSkillLevel;
	private final boolean sellable;
	private final String buyUrl;
	private final String sellUrl;
	private int count;
	

	public Item(String id, String title, String category, int requiredSkillLevel, boolean sellable, String buyUrl, String sellUrl) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.requiredSkillLevel = requiredSkillLevel;
		this.sellable = sellable;
		this.buyUrl = buyUrl;
		this.sellUrl = sellUrl;
		this.count = 0;
	}

	public Item(String id) {
		this.id = id;
		this.title = null;
		this.category = null;
		this.requiredSkillLevel = 0;
		this.sellable = false;
		this.buyUrl = null;
		this.sellUrl = null;
		this.count = 0;
	}

	public String getId() { return id; }
	public String getTitle() { return title; }
	public String getCategory() { return category; }
	public int getRequiredSkillLevel() { return requiredSkillLevel; }
	public boolean isSellable() { return sellable; }
	public String getBuyUrl() { return buyUrl; }
	public String getSellUrl() { return sellUrl; }
	
	public int getCount() { return count; }
	public Item setCount(int count) { this.count = count; return this; }

	@Override
	public String toString() {
		return title;
	}

}
