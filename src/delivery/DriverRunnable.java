package delivery;

public class DriverRunnable implements Runnable{

    // 필드


    // 생성자


    // 메소드

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("[ERROR] 배달이 취소되었습니다.");
        }
    }
}
