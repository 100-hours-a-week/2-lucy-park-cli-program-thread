package delivery;

import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;
import user.Customer;

public class DeliveryRunnable implements Runnable{

    // 필드
    private long orderId;                  // 주문 아이디
    private Restaurant restaurant;         // 식당
    private Customer customer;             // 고객
    private OrderStatus orderStatus;       // 주문 상태
    private String orderTimeStamp;         // 최초 주문 시각
    private int deliveryTime;              // 총 배달 소요 시간
    private int deliveryDistance = 8;      // 임의 지정 거리
    private Order order;


    // 생성자
    public DeliveryRunnable(Order order) {
        this.orderId = order.getOrderId();
        this.restaurant = order.getRestaurant();
        this.customer = order.getCustomer();
        this.orderStatus = order.getOrderStatus();
        this.orderTimeStamp = order.getOrderTimeStamp();
        this.deliveryTime = restaurant.getRestaurantDeliveryTime();

        this.order = order;
    }


    // 메소드
    @Override
    public void run() {
        order.updateOrderStatus();
        orderStatus = order.getOrderStatus();
        System.out.printf("[%s]:  라이더 님이 출발했어요! 접수번호: %d%n", orderStatus, orderId);
        System.out.printf("           %s에서 %s님이 계신 곳으로 배송 중입니다!%n", restaurant.getRestaurantLocation(), customer.getName());

        try {
            while(deliveryDistance > -1) {
                Thread.sleep(2000);
                deliveryDistance -= 2;

                switch (deliveryDistance) {
                    case 6:
                        System.out.println("           주변 배달을 함께 처리하고 있어요!");
                        break;
                    case 4:
                        System.out.println("           라이더 님이 주변에 도착했어요!");
                        break;
                    case 2:
                        System.out.println("           라이더 님이 곧 도착해요!");
                        System.out.println("           음식 수령을 위해 기다려주세요.");  // 순차적으로 나오는 이유 궁금
                        break;
                    case 0:
                        order.updateOrderStatus();
                        orderStatus = order.getOrderStatus();
                        System.out.printf("[%s]   라이더 님이 주문하신 음식을 문 앞에 전달하셨습니다.%n", orderStatus);
                        System.out.println("           음식을 수령해주세요!");
                        break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("❗ 배달이 취소되었습니다.");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: "+e);
        }
    }
}
