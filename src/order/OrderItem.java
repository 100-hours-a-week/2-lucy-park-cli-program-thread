package order;

import cart.CartItem;
import restaurant.MenuItem;

public class OrderItem {

    // 필드
    private MenuItem menuItem;     // 주문 메뉴 리스트
    private int quantity;          // 주문 메뉴 수량


    // 생성자
    public OrderItem(CartItem cartItem) {
        this.menuItem = new MenuItem(cartItem.getMenuItem());
        this.quantity = cartItem.getQuantity();
    }


    // 메소드
    public int getSubTotal() {
        return menuItem.getItemPrice() * getQuantity();
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }
}
