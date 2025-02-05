package user;

import cart.Cart;
import cart.CartItem;
import order.Order;
import order.OrderItem;
import order.OrderType;
import restaurant.Customization;
import restaurant.Menu;
import restaurant.MenuItem;
import restaurant.Restaurant;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static order.OrderType.배달;

public class Customer extends User{

    // 필드 --


    // 생성자
    public Customer(String name, UserStatus userStatus) {
        super(name, userStatus);
    }

    // 메소드
    public Restaurant selectRestaurant(List<Restaurant> restaurants, String rName) {
        for(Restaurant restaurant : restaurants) {
            if(rName.equals(restaurant.getRestaurantName())) {
                System.out.printf("%s 식당이 선택되었습니다.%n", restaurant.getRestaurantName());
                return restaurant;
            }
        }
        return null;
    }

    public Cart selectMenu(Restaurant restaurant) {
        Menu confirmedMenu = null;
        int contimedQuantity = 0;
        Scanner sc = new Scanner(System.in);

        while(confirmedMenu == null) {
            System.out.println("━━━━━━━━━━━━━━━⊱메뉴⊰━━━━━━━━━━━━━━━");

            List<Menu> menuList = restaurant.getMenuList();
            for (Menu menu : menuList) {
                System.out.printf("\uD80C\uDFF2๋࣭࣪˖ <%s> %s  %d%n", menu.getCategory(), menu.getItemName(), menu.getItemPrice());  // 특수 이모티콘입니다!
            }
            System.out.println("━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━");

            try {
                String selectedMenu = sc.nextLine().trim();

                if (selectedMenu.equals("뒤로 가기")) {
                    System.out.println("뒤로 가기를 선택하셨습니다. 식당 조회 화면으로 되돌아갑니다.");
                    return null;
                }

                for (Menu menu : menuList) {
                    if (menu.getItemName().equals(selectedMenu)) {
                        confirmedMenu = menu;
                        System.out.printf("%s 메뉴가 선택되었습니다.%n", selectedMenu);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("문자만 입력해주세요.");
            }

            contimedQuantity = selectQuantity(sc);

            System.out.printf("%s 메뉴가 %d개 선택되었습니다.%n", confirmedMenu.getItemName(), contimedQuantity);
            if (confirmedMenu == null) {
                System.out.println("존재하지 않는 메뉴입니다.");
            }
        }

        CartItem menuCartItem = null;
        menuCartItem = new CartItem(confirmedMenu, contimedQuantity);

        CartItem customizationCartItem = null;

        customizationCartItem = selectCustomazation(confirmedMenu, customizationCartItem, sc);

        System.out.println("선택된 메뉴는 다음과 같습니다.");
        System.out.printf("%s %d%n", menuCartItem.getMenuItem().getItemName(), menuCartItem.getQuantity());
        if (isCustomizationNotNUll(customizationCartItem)) {
            System.out.printf("%s %d%n", customizationCartItem.getMenuItem().getItemName(), customizationCartItem.getQuantity());
        }

        int totalPrice = isCustomizationNotNUll(customizationCartItem)
                ?
                menuCartItem.getSubTotal() +
                customizationCartItem.getSubTotal()
                :
                menuCartItem.getSubTotal();

        Cart cart = null;
        while(cart == null) {
            System.out.printf("총 가격은 %d원입니다. 카트에 담으시겠습니까?: 네  아니오 %n", totalPrice);

            String reply = sc.nextLine().trim();

            if (reply.equals("네")) {
                System.out.println("장바구니에 추가되었습니다.");
                List<CartItem> cartItemList = new ArrayList<>();
                cartItemList.add(menuCartItem);
                if (isCustomizationNotNUll(customizationCartItem)) {
                    cartItemList.add(customizationCartItem);
                }
                cart = new Cart(cartItemList);
                break;
            } else if (reply.equals("뒤로 가기") || reply.equals("아니오")) {
                System.out.println("처음 화면으로 돌아갑니다.");
                cart = new Cart(null);
                break;
            } else {
                System.out.println("조건에 맞게 응답해주세요.");
            }
        }
        return cart;
    }

    public CartItem selectCustomazation(Menu confirmedMenu, CartItem customizationCartItem, Scanner sc) {
        List<Customization> customizations = confirmedMenu.getCustomizations();

        if(customizations.isEmpty()) {
            System.out.println("사이드 메뉴가 없는 메뉴입니다.");
            return customizationCartItem;
        }
        else {

            System.out.println("━━━━━━━━━━━━━⊱사이드메뉴⊰━━━━━━━━━━━━━");
            for (Customization customization : customizations) {
                System.out.printf(":::::<%s>   %s  %d%n", customization.getCustomizationType(), customization.getItemName(), customization.getItemPrice());
            }
            System.out.println("━━━━━━━━━━━━━━━━━⊱⊰━━━━━━━━━━━━━━━━");

            customizationCartItem = new CartItem();
            MenuItem selectedCustomization = null;

            String side = "";
            System.out.println("사이드 메뉴를 선택해주세요.");
            while(true) {
                try {
                    side = sc.nextLine().trim();

                    for (Customization customization : customizations) {
                        if (customization.getItemName().equals(side)) {
                            selectedCustomization = customization;
                            System.out.printf("%s 사이드 메뉴가 선택되었습니다.%n", side);
                            break;
                        }
                    }
                    if (selectedCustomization != null) {
                        break;
                    }

                    System.out.println("해당 사이드 메뉴가 없습니다. 다시 선택해주세요.");
                } catch (InputMismatchException e) {
                    System.out.println("문자만 입력해주세요.");
                }
            }

            int sideQuantity = 0;
            sideQuantity = selectQuantity(sc);

            System.out.printf("%s 메뉴가 %d개 선택되었습니다.%n", side, sideQuantity);
            customizationCartItem.setMenuItem(selectedCustomization);
            customizationCartItem.setQuantity(sideQuantity);
            return customizationCartItem;
        }
    }

    // 리턴 타입 고려 필요
    public Order setDeliveryOrder(Restaurant restaurant, Cart cart, OrderType orderType, Scanner sc) {
        // 최소 주문 금액 확인(배달)
        int minOrderAmount = restaurant.getRestaurantMinOrderAmount();
        System.out.printf("최소 주문 금액: %d%n", minOrderAmount);

        int cartTotalPrice = cart.getTotalPrice();
        System.out.printf("현재 장바구니 금액: %d%n", cartTotalPrice);

        while (cartTotalPrice < minOrderAmount) {
            System.out.println("❗ 현재 주문 금액이 최소 주문 금액보다 적습니다.");
            System.out.println("추가 주문을 하시겠습니까? 네   아니오");
            String reply = sc.nextLine().trim();

            if (reply.equals("네")) {
                System.out.println("추가 주문을 진행합니다.");
                Cart newItems = selectMenu(restaurant);

                if (newItems != null && newItems.getCartItemList() != null) {
                    cart.getCartItemList().addAll(newItems.getCartItemList());  // 기존 장바구니에 추가
                    cartTotalPrice = cart.getTotalPrice();  // 총 가격 업데이트
                    System.out.printf("추가 주문 완료! 현재 장바구니 총 금액: %d원%n", cartTotalPrice);
                } else {
                    System.out.println("추가 주문이 취소되었습니다.");
                }
            } else if (reply.equals("아니오")) {
                System.out.println("주문을 취소하고 처음으로 돌아갑니다.");
                return null;
            } else {
                System.out.println("올바른 입력이 아닙니다. '네' 또는 '아니오'를 입력해주세요.");
            }
        }

        System.out.println("주문되었습니다.");

        // Customer, VIPCustomer 여부에 따라 오버라이딩된 메소드 사용됨
        int deliveryFee = getDeliveryFee(restaurant);

        if (deliveryFee == 0) {
            System.out.println("VIP 혜택으로 배달비가 0원 처리되었습니다.");
        } else {
            System.out.printf("귀하의 배달비는 %d원입니다.%n", deliveryFee);
        }

        // 주문
        return setOrder(restaurant, cart, orderType);
    }

    public int selectQuantity(Scanner sc) {
        int quantity;

        System.out.println("수량을 선택해주세요.");
        while (true) {
            try {
                quantity = sc.nextInt();
                sc.nextLine();

                if (quantity == 0) {
                    System.out.println("1개 이상 주문 가능합니다.");
                    sc.nextLine();
                    continue;
                }

                if (quantity > 0) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요.");
                sc.nextLine();
            }
        }
        return quantity;
    }

    public boolean isCustomizationNotNUll(CartItem customizationCartItem) {
        if (customizationCartItem != null) {
            return true;
        }
        return false;
    }

    public Order setTakeoutOrder(Restaurant restaurant, Cart cart, OrderType orderType) {
        return setOrder(restaurant, cart, orderType);
    }

    public Order setOrder(Restaurant restaurant, Cart cart, OrderType orderType) {

        List<CartItem> cartItemList = cart.getCartItemList();
        List<OrderItem> orderItemList = new ArrayList<>();

        for(CartItem cartItem : cartItemList) {
            ///  깊은 복사로 업데이트 완료
            OrderItem orderItem = new OrderItem(cartItem);
            orderItemList.add(orderItem);
        }

        int deliverFee = restaurant.getRestaurantDeliveryFee();

        Order order = new Order(this, restaurant, orderItemList,
                                orderType == 배달
                                    ? cart.getTotalPrice() + deliverFee
                                    : cart.getTotalPrice(),
                                orderType);

        System.out.println("━━━━━━━━━━━⊱주문 내역 확인⊰━━━━━━━━━━━");
        System.out.printf(":::::: 주문 번호   %d%n", order.getOrderId());
        System.out.printf(":::::: 주문 고객   %s%n", order.getCustomer().getName());
        System.out.printf(":::::: 식당       %s%n", order.getRestaurant().getRestaurantName());
        System.out.printf(":::::: 주문 메뉴   %n");
        orderItemList = order.getOrderItemList();
        for(OrderItem orderItem : orderItemList) {
            System.out.printf("::::::     %s   %n", orderItem.getMenuItem().getItemName());
        }
        System.out.printf(":::::: 수령 방식   %s%n", order.getOrderType());
        System.out.printf(":::::: 주문 금액   %s%n", order.getTotalOrderPrice());
        System.out.printf(":::::: 주문 시각   %s%n", order.getOrderTimeStamp()); // LocalDateTime 처리
        System.out.printf(":::::: 진행 상황   %s%n", order.getOrderStatus());

        int orderTime =
                orderType == 배달
                ? order.getRestaurant().getRestaurantDeliveryTime()
                : order.getRestaurant().getRestaurantTakeoutTime();
        int hour = orderTime / 60;
        int minutes = orderTime % 60;
        System.out.printf(":::::: 소요 시간  %s %d분%n", hour > 0 ? " " + hour + "시간" : "", minutes);

        System.out.println("이용해 주셔서 감사합니다.");
        System.out.println("EatsNow!");
        System.out.println("━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━");

        return order;
    }

    public int getDeliveryFee(Restaurant restaurant) {
        return restaurant.getRestaurantDeliveryFee();
    }
}
