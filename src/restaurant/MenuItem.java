package restaurant;

public class MenuItem {

    // 필드
    private String itemName;  // 메뉴 아이템명
    private int itemPrice;    // 메뉴 아이템 가격


    // 생성자
    public MenuItem(String itemName, int itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public MenuItem(MenuItem menuItem) {
        this.itemName = menuItem.getItemName();
        this.itemPrice = menuItem.getItemPrice();
    }


    // 메소드
    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }
}
