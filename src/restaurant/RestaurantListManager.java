package restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListManager {

    //  필드
    List<Restaurant> restaurantList;  // 식당 리스트


    // 생성자
    public RestaurantListManager(List<Restaurant> restaurantList) {
        this.restaurantList = (restaurantList != null) ? restaurantList : new ArrayList<>();
    }


    // 메소드

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
