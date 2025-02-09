package delivery;

import location.Location;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

public class DriverRunnable implements Runnable{

    // 필드
    private BlockingQueue<Location> locationQueue;                        // 위치 정보(실제로는 넘어가게 구현 필요)
    private BlockingQueue<Integer> remainingDistanceQueue;                // 남은 거리
    private BigDecimal driverLatitude = new BigDecimal("37.5665");    // 라이더 위도 정보
    private BigDecimal driverLongitude = new BigDecimal("126.9780");  // 라이더 경도 정보
    private int remainingDistance = 8;  // 초기에는 라이더 GPS로 자동 매핑 가정(원래는 식당 정보와 연계 필요)


    // 생성자
    public DriverRunnable(BlockingQueue<Location> locationQueue, BlockingQueue<Integer> distanceQueue) {
        this.locationQueue = locationQueue;
        this.remainingDistanceQueue = distanceQueue;
    }


    // 메소드
    @Override
    public void run() {
        try {
            while (remainingDistance > -1) {

                // 가상 위치 데이터 업데이트
                // 실제로는 라이더 GPS에 따른 값으로 매핑.
                BigDecimal newLatitude = driverLatitude.subtract(new BigDecimal("0.001"));
                BigDecimal newLongitude = driverLongitude.subtract(new BigDecimal("0.001"));
                locationQueue.put(new Location(newLatitude, newLongitude));

                // 실제로는 거리 계산 로직 구현 필요
                remainingDistance -= 2;
                remainingDistanceQueue.put(remainingDistance);

                System.out.println("[라이더 현재 위치]  " + newLatitude + ", " + newLongitude);
                System.out.println("[라이더 주행 남은 시간]  " + remainingDistance * 5 + "분");

                if (remainingDistance == 0) {
                    break;
                }

                Thread.sleep(2000);
            }
            System.out.println("[DriverRunnable]   배달 완료. 다른 주문을 배정받습니다.");
        } catch (InterruptedException e) {
            System.out.println("[ERROR] 배달이 취소되었습니다.");
        }
    }
}
