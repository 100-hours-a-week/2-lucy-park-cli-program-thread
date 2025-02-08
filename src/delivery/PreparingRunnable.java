package delivery;

import order.Order;
import order.OrderStatus;

public class PreparingRunnable implements Runnable{

    // 필드
    private Order order;                 // 주문
    private boolean isAccepted = false;  // 주문 접수 여부
    private boolean isPrepared = false;  // 조리 완료 여부


    // 생성자
    public PreparingRunnable(Order order) {
        this.order = order;
    }


    // 메소드
    @Override
    public void run() {
        while(!isPrepared) {
            try {
                OrderStatus orderStatus = order.getOrderStatus();
                order.updateOrderStatus();
                if (!isAccepted) {
                    System.out.printf("[%s]  %s 식당에서 주문이 접수되었습니다.%n",
                            orderStatus.toString(), order.getRestaurant().getRestaurantName());
                    isAccepted = true;
                } else {
                    System.out.printf("[%s]  %s 식당에서 조리가 완료되었습니다.%n",
                            orderStatus.toString(), order.getRestaurant().getRestaurantName());
                    isPrepared = true;
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("[ERROR] 주문이 취소되었습니다.");
                return;
            }
        }
    }
}
