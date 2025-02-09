package delivery;

import location.Location;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;
import user.Customer;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

public class DeliveryRunnable implements Runnable{

    // 필드
    private BlockingQueue<Integer> remainingDistanceQueue;  // 스레드 간 데이터 공유
    private Order order;                    // 주문
    private long orderId;                   // 주문 아이디
    private Restaurant restaurant;          // 식당
    private Customer customer;              // 고객
    private OrderStatus orderStatus;        // 주문 상태
    private String orderTimeStamp;          // 최초 주문 시각
    private int deliveryTime;               // 총 배달 소요 시간
    private Location userLocation;          // 배달 주소
    private int remainingDistance = 0;

    // 생성자
    public DeliveryRunnable(BlockingQueue<Integer> remainingDistanceQueue, Order order) {
        this.remainingDistanceQueue = remainingDistanceQueue;
        this.orderId = order.getOrderId();
        this.restaurant = order.getRestaurant();
        this.customer = order.getCustomer();
        this.userLocation = order.getCustomer().getUserLocation();
        this.orderStatus = order.getOrderStatus();
        this.orderTimeStamp = order.getOrderTimeStamp();
        this.deliveryTime = restaurant.getRestaurantDeliveryTime();
        this.order = order;
    }


    // 메소드

    // 큐에서 받은 위치 기준 업데이트 함수 필요 -> 별도의 클래스로 구현해야 하는가?
    @Override
    public void run() {
        order.updateOrderStatus();
        orderStatus = order.getOrderStatus();

        try {
            while(remainingDistance > -1) {
                remainingDistance = remainingDistanceQueue.take();
                switch (remainingDistance) {
                    case 8:
                        System.out.printf("[%s]:  라이더 님이 출발했어요! 접수번호: %d%n", orderStatus, orderId);
                        System.out.printf("           %s에서 %s님이 계신 곳으로 배송 중입니다!%n", restaurant.getRestaurantLocation(), customer.getName());
                        break;
                    case 6:
                        System.out.println("           주변 배달을 함께 처리하고 있어요!");
                        break;
                    case 4:
                        System.out.println("           라이더 님이 주변에 도착했어요!");
                        break;
                    case 2:
                        System.out.println("           라이더 님이 곧 도착해요!");
                        System.out.println("           음식 수령을 위해 기다려주세요.");
                        break;
                    case 0:
                        order.updateOrderStatus();
                        orderStatus = order.getOrderStatus();
                        System.out.printf("[%s]   주문하신 음식이 문 앞에 전달되었어요.%n", orderStatus);
                        System.out.println("           음식을 수령해주세요!");
                        System.out.println("감사합니다. EatsNow!");
                        System.out.println("━━━━━━━━━━━━━━━━━⊱⊰━━━━━━━━━━━━━━━━");
                        break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[ERROR] 배달이 취소되었습니다.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
