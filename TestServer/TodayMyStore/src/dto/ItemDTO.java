package dto;

public class ItemDTO {
	private String itemType;
	private String userId;
	private String itemName;
	private int unitPrice;
	
	@Override
	public String toString() {
		return "ItemDTO [itemType=" + itemType + ", userId=" + userId + ", itemName=" + itemName + ", unitPrice="
				+ unitPrice + "]";
	}
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
}