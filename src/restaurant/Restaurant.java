package restaurant;

import location.Location;

import java.util.List;

public class Restaurant {

    // 필드
    private String restaurantName;         // 식당 이름
    private Location restaurantLocation;     // 식당 위치
    private String restaurantCategory;     // 식당 종류(양식, 한식 등)
    private double restaurantRate;         // 식당 평점
    private int restaurantDeliveryFee;     // 배달료
    private int restaurantMinOrderAmount;  // 최소 주문 금액
    private int restaurantDeliveryTime;    // 배달 시간(배달 선택 시)
    private int restaurantTakeoutTime;     // 포장 시간
    private List<Menu> menuList;           // 식당 메뉴


    // 생성자
    public Restaurant(String restaurantName, Location restaurantLocation, String restaurantCategory, double restaurantRate,
               int restaurantDeliveryFee, int restaurantMinOrderAmount, int restaurantDeliveryTime, int restaurantTakeoutTime) {
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.restaurantCategory = restaurantCategory;
        this.restaurantRate = restaurantRate;
        this.restaurantDeliveryFee = restaurantDeliveryFee;
        this.restaurantMinOrderAmount = restaurantMinOrderAmount;
        this.restaurantDeliveryTime = restaurantDeliveryTime;
        this.restaurantTakeoutTime = restaurantTakeoutTime;
    }


    // 메소드
    public void setMenus(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getRestaurantName() {

        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(String restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }

    public double getRestaurantRate() {
        return restaurantRate;
    }

    public void setRestaurantRate(double restaurantRate) {
        this.restaurantRate = restaurantRate;
    }

    public int getRestaurantDeliveryFee() {
        return restaurantDeliveryFee;
    }

    public void setRestaurantDeliveryFee(int restaurantDeliveryFee) {
        this.restaurantDeliveryFee = restaurantDeliveryFee;
    }

    public int getRestaurantMinOrderAmount() {
        return restaurantMinOrderAmount;
    }

    public void setRestaurantMinOrderAmount(int restaurantMinOrderAmount) {
        this.restaurantMinOrderAmount = restaurantMinOrderAmount;
    }

    public int getRestaurantDeliveryTime() {
        return restaurantDeliveryTime;
    }

    public void setRestaurantDeliveryTime(int restaurantDeliveryTime) {
        this.restaurantDeliveryTime = restaurantDeliveryTime;
    }

    public int getRestaurantTakeoutTime() {
        return restaurantTakeoutTime;
    }

    public void setRestaurantTakeoutTime(int restaurantTakeoutTime) {
        this.restaurantTakeoutTime = restaurantTakeoutTime;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }


    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}

