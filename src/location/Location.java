package location;

import java.math.BigDecimal;

public class Location {

    // 필드
    private BigDecimal latitude;   // 위도
    private BigDecimal longitude;  // 경도


    // 생성자
    public Location(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // 메소드
    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
}
