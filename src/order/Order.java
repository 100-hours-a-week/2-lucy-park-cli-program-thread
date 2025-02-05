package order;

import restaurant.Restaurant;
import user.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static order.OrderStatus.주문대기;

public class Order {

    // 필드
    private static final AtomicLong orderIdCounter = new AtomicLong(1);
    private long orderId;                      // 주문 아이디
    private Customer customer;                 // 주문한 고객
    private Restaurant restaurant;             // 주문받은 식당
    private List<OrderItem> orderItemList;     // 주문 메뉴 리스트
    private int totalOrderPrice;               // 주문 금액
    private OrderStatus orderStatus = 주문대기;  // 주문 상태 default 주문대기
    private OrderType orderType;               // 주문 타입(배달, 포장)
    private LocalDateTime orderTimeStamp;      // 주문 시간


    // 생성자
    public Order(Customer customer, Restaurant restaurant,
                 List<OrderItem> orderItemList, int totalOrderPrice, OrderType orderType) {
        this.orderId = orderIdCounter.getAndIncrement();
        this.customer = customer;
        this.restaurant = restaurant;
        this.orderItemList = orderItemList;
        this.totalOrderPrice = totalOrderPrice;
        this.orderType = orderType;

        // 상태만 업데이트
        this.updateOrderStatus();

        // 주문 시각
        this.orderTimeStamp = LocalDateTime.now();
    }


    // 메소드
    public void updateOrderStatus() {
        // enum 활용해서 추가 구현 필요
        OrderStatus nextStatus = orderStatus.nextOrderStatus(orderType, orderStatus);
        this.orderStatus = nextStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getOrderTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(orderTimeStamp);
    }
}
