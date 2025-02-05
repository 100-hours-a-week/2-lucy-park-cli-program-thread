import cart.Cart;
import cart.CartItem;
import delivery.DeliveryThread;
import order.Order;
import order.OrderStatus;
import restaurant.Customization;
import restaurant.Menu;
import restaurant.Restaurant;
import user.Customer;
import user.VIPCustomer;

import java.util.*;

import static order.OrderType.배달;
import static order.OrderType.포장;
import static user.UserStatus.VIP;
import static user.UserStatus.일반;

public class Main {
    public static void main(String[] args) {
        List<Restaurant> restaurantList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        /// 식당1
        Restaurant r1 = new Restaurant("맥도날드", "서울시 용산구", "패스트푸트",
                                        4.8, 3000, 12000, 50, 20);
        List<Menu> r1MenuList = new ArrayList<>();

        // 사이드 메뉴 존재하는 메뉴
        List<Customization> r1Customizations = Arrays.asList(
                new Customization("후렌치 후라이 스몰", 2300, "스낵"),
                new Customization("후렌치 후라이 미디움", 2800, "스낵"),
                new Customization("후렌치 후라이 라지", 3300, "스낵"),
                new Customization("콜라", 1200, "음료"),
                new Customization("제로 콜라", 1500, "음료")
        );
        Menu r1Menu = new Menu("빅맥", 6300, "버거 단품", r1Customizations);
        r1MenuList.add(r1Menu);
        r1.setMenus(r1MenuList);

        // 사이드 메뉴 없는 메뉴
        List<Customization> r1Customizations2 = new ArrayList<>();
        Menu r1Menu2 = new Menu("스파이시 상하이 버거 세트", 13300, "버거 세트", r1Customizations2);
        r1MenuList.add(r1Menu2);
        r1.setMenus(r1MenuList);

        // 사이드 메뉴 없는 메뉴2
        List<Customization> r1Customizations3 = new ArrayList<>();
        Menu r1Menu3 = new Menu("쿼터 파운드 버거 세트", 13800, "버거 세트", r1Customizations2);
        r1MenuList.add(r1Menu3);
        r1.setMenus(r1MenuList);

        restaurantList.add(r1);


        ///  식당2
        Restaurant r2 = new Restaurant("동대문 엽기떡볶이", "서울시 용산구", "분식",
                4.5, 4000, 15000, 70, 15);

        // 사이드 메뉴 없는 메뉴1
        List<Customization> r2Customizations = new ArrayList<>();
        List<Menu> r2MenuList = new ArrayList<>(Arrays.asList(
                new Menu("엽기떡볶이", 14000, "떡볶이", r2Customizations),
                new Menu("엽기오뎅", 14000, "떡볶이", r2Customizations),
                new Menu("엽기반반", 14000, "떡볶이", r2Customizations),
                new Menu("엽기분모자떡볶이", 17000, "떡볶이", r2Customizations)
        ));
        r2.setMenus(r2MenuList);


        // 사이드 메뉴 존재하는 메뉴
        List<Customization> r2Customizations2 = Arrays.asList(
                new Customization("떡 추가", 1000, "추가 사리"),
                new Customization("오뎅 추가", 1000, "추가 사리"),
                new Customization("중국당면 추가", 2500, "추가 사리")
        );
        Menu r2Menu6 = new Menu("로제떡볶이", 16000, "커스텀 떡볶이", r2Customizations2);
        r2MenuList.add(r2Menu6);
        r2.setMenus(r2MenuList);

        // 사이드 메뉴 없는 메뉴2
        List<Customization> r2Customizations3 = new ArrayList<>();
        Menu r2Menu7 = new Menu("엽기밀키트", 18000, "밀키트", r2Customizations2);
        r2MenuList.add(r2Menu7);
        r2.setMenus(r2MenuList);

        restaurantList.add(r2);

        // 로그인
        Customer customer = null;

        String account = "";
        while(true) {
            try {
                System.out.println("로그인할 계정을 선택해주세요: 일반     VIP");
                String accountReply = sc.nextLine().trim();

                if (accountReply.equals("일반")) {
                    account = "일반";
                    break;
                } else if (accountReply.equalsIgnoreCase("VIP")) {
                    account = "VIP";
                    break;
                } else {
                    System.out.println("다시 선택해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("문자만 입력해주세요.");
            }
        }

        System.out.println("계정 이름을 입력해주세요.");
        String accountName = sc.nextLine().trim();

        if (account.equals(일반.toString())) {
            customer = new Customer(accountName, 일반);
        } else if (account.equalsIgnoreCase(VIP.toString())) {
            customer = new VIPCustomer(accountName, VIP);
        }


        // 서비스
        System.out.printf("안녕하세요, %s님.%n", accountName);
        System.out.println("환영합니다. EatsNow!");

        Restaurant restaurant = null;
        Cart cart = null;
        while(cart == null) {
            System.out.println("식당 조회");

            for (Restaurant res : restaurantList) {
                System.out.println("━━━━━━━━━━━━━━━━━⊱⊰━━━━━━━━━━━━━━━━");
                System.out.printf(".•☀ %s%n ☀•.", res.getRestaurantName());
                System.out.printf(".•☀ 평점 %s%n", res.getRestaurantRate());
                System.out.printf(".•☀ %s분 소요%n", res.getRestaurantDeliveryTime());
                System.out.printf(".•☀ 배달팁 %s%n", res.getRestaurantDeliveryFee());
                System.out.printf(".•☀ 최소 주문 %s%n", res.getRestaurantMinOrderAmount());
            }
            System.out.println("━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━");

            System.out.println("식당을 선택해주세요.");
            while(true) {
                String selectedRestaurantName = sc.nextLine().trim();
                restaurant = customer.selectRestaurant(restaurantList, selectedRestaurantName);

                if(restaurant == null) {
                    System.out.println("해당 이름의 식당이 없습니다. 다시 선택해주세요.");
                    continue;
                }
                break;
            }

            /// /// 흐름을 직관적으로 변경할 필요 있음.
            while (cart == null) {
                System.out.println("메뉴를 선택해주세요. (혹은 '뒤로 가기'를 입력하세요.)");
                cart = customer.selectMenu(restaurant);

                if (cart == null) {
                    System.out.println("식당 조회 화면으로 되돌아 갑니다.");
                    break;
                }

                break;
            }
            if (cart == null) {
                continue;
            }
        }

        Order order = null;
        if(cart != null) {
            System.out.println("장바구니를 확인합니다.");
            System.out.println("━━━━━━━━━━━━━━⊱장바구니⊰━━━━━━━━━━━━━━");
            List<CartItem> cartItemList = cart.getCartItemList();
            for(CartItem cartItem : cartItemList) {
                System.out.printf("%s %d%n", cartItem.getMenuItem().getItemName(), cartItem.getQuantity());
            }
            System.out.println("━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━");
            while(true) {
                System.out.println("주문하시겠습니까? 네  아니오");
                String orderReply = sc.nextLine();

                if (orderReply.equals("네")) {               ////////  에러 처리 필요
                    while(true) {
                        System.out.println("수령 방식을 선택해주세요: 배달   포장");    ///// 에러 처리 필요
                        String orderType = sc.nextLine();

                        // 배달 주문
                        if (orderType.equals("배달")) {
                            order = customer.setDeliveryOrder(restaurant, cart, 배달, sc);
                            break;
                        } else if (orderType.equals("포장")) {
                            order = customer.setTakeoutOrder(restaurant, cart, 포장);
                            break;
                        } else {
                            System.out.println("잘못된 응답입니다. 다시 선택해주세요.");
                            sc.nextLine();
                        }
                    }

                }
                break;
            }
        }



        order.updateOrderStatus();
        OrderStatus orderStatus = order.getOrderStatus();
        System.out.printf("[%s]  %s 식당에서 주문이 접수되었습니다.%n", orderStatus.toString(), order.getRestaurant().getRestaurantName());

        order.updateOrderStatus();
        orderStatus = order.getOrderStatus();
        System.out.printf("[%s]  %s 식당에서 조리가 완료되었습니다.%n", orderStatus.toString(), order.getRestaurant().getRestaurantName());


        if(order.getOrderType() == 배달) {
            Thread deliveryThread = new Thread(new DeliveryThread(order));
            deliveryThread.start();
        }

        System.out.println("오늘도 이용해주셔서 감사합니다.");
    }
}