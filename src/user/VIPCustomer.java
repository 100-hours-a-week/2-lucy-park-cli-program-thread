package user;

import restaurant.Restaurant;

public class VIPCustomer extends Customer {

    // 필드 --

    // 생성자
    public VIPCustomer(String name, UserStatus userStatus) {
        super(name, userStatus);
    }


    // 메소드
    @Override
    public int getDeliveryFee(Restaurant restaurant) {
        return 0;
    }
}
