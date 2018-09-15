package dto;

public class TrendDTO {
	private String userId;
	private String itemType;
	private String startDate;
	private String endDate;
	private int sumQuantity;
	private String itemName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getSumQuantity() {
		return sumQuantity;
	}
	public void setSumQuantity(int sumQuantity) {
		this.sumQuantity = sumQuantity;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Override
	public String toString() {
		return "TrendDTO [userId=" + userId + ", itemType=" + itemType + ", startDate=" + startDate + ", endDate="
				+ endDate + ", sumQuantity=" + sumQuantity + ", itemName=" + itemName + "]";
	}
}