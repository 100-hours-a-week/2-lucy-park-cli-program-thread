package user;

import location.Location;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    // 필드
    private static final AtomicLong idCounter = new AtomicLong(1);  // 카운터
    private long id;                                                          // 유저 아이디
    private String name;                                                      // 유저 이름
    private UserStatus userStatus;
    private Location location = new Location(new BigDecimal(37.5665), new BigDecimal(126.9780));


    // 생성자
    public User(String name, UserStatus userStatus) {
        this.id = idCounter.getAndIncrement();
        this.name = name;
        this.userStatus = userStatus;
    }


    // 메소드
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getUserLocation() {
        return userLocation;
    }
}
