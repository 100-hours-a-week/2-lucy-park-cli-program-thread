package delivery;

import location.Location;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

public class DriverRunnable implements Runnable{

    // 필드
    private BlockingQueue<Location> locationQueue;  // 위치 정보
    private BlockingQueue<Integer> remainingDistanceQueue;
    private BigDecimal driverLatitude;
    private BigDecimal driverLongitude;
    private int remainingDistance;
    // 생성자
    public DriverRunnable(BlockingQueue<Location> locationQueue, BlockingQueue<Integer> distanceQueue) {
        this.locationQueue = locationQueue;
        this.remainingDistanceQueue = distanceQueue;
        this.driverLatitude = locationQueue.peek().getLatitude();
        this.driverLongitude = locationQueue.peek().getLongitude();
    }


    // 메소드
    @Override
    public void run() {
        try {
            while (remainingDistance > -1) {
                //locationQueue.take();
                BigDecimal newLatitude = driverLatitude.subtract(new BigDecimal("0.001"));
                BigDecimal newLongitude = driverLongitude.subtract(new BigDecimal("0.001"));
                locationQueue.add(new Location(newLatitude, newLongitude));

                System.out.println("[라이더 현재 위치]  " + newLatitude + ", " + newLongitude);

                remainingDistance = remainingDistanceQueue.take();
                remainingDistance -= 2;
                remainingDistanceQueue.put(remainingDistance);

                Thread.sleep(2000);
            }
            System.out.println("[DriverRunnable]   배달 완료. 다른 주문을 배정받습니다.");
        } catch (InterruptedException e) {
            System.out.println("[ERROR] 배달이 취소되었습니다.");
        }
    }
}
