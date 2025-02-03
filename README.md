# EatsNow 🍽️
EatsNow는 고객과 식당을 연결하는 배달 주문 서비스입니다.  
사용자는 식당을 조회하고 메뉴를 선택하며 배달 또는 포장 주문을 할 수 있습니다.
[➡ EatsNow Repository](https://github.com/jieunyy/kbt_week1)  

⠀
## 🛠️ Tech Stack
| Category         | Technology   |
|-----------------|-------------|
| Language        | Java 21      |
| IDE            | IntelliJ     |
| Version Control | Git & GitHub |  

## 📖 주요 기능
### 1️⃣ 사용자 로그인 (입출력 예외 처리 완료)
- 일반 고객(`Customer`)과 VIP 고객(`VIPCustomer`, 업캐스팅 적용) 로그인 지원
### 2️⃣ 식당 관리
- **식당 등록**: `Customization → Menu → Restaurant` 순으로 식당을 등록
- **식당 조회**: `RestaurantList`의 모든 식당을 조회
- **식당 선택**: `Customer.selectRestaurant()` 을 통해 특정 식당 선택 가능  (입출력 예외 처리 완료)
### 3️⃣ 메뉴 관리
- **메뉴 선택**: `Customer.selectMenu()` 를 통해 메뉴 및 수량 선택 (입출력 예외 처리 완료)
- **커스텀 메뉴 선택**: `Customer.selectCustomization()` (선택한 메뉴에 커스텀 옵션이 존재할 경우에만 작동)  (입출력 예외 처리 완료)
### 4️⃣ 장바구니
- 선택한 메뉴 및 커스텀 메뉴를 바탕으로 `CartItem` 생성
- 장바구니로 이동하여 추가 편집 가능 (입출력 예외 처리 완료)
### 5️⃣ 주문 처리
- **주문 가능 조건**: 장바구니에 하나 이상의 메뉴가 존재할 경우 주문 가능  (입출력 예외 처리 완료)
- **주문 유형 선택**: `OrderType`에 따라 배달(`setDeliveryOrder()`) 또는 포장(`setTakeoutOrder()`) 주문 진행  (입출력 예외 처리 완료)
- **배달 주문**:
  
  - `setDeliveryOrder()` 실행
  
  - 식당의 최소 주문 금액 이상인 경우 배달 가능
  
  - 최소 주문 금액 미만 시 추가 주문 또는 초기화 가능  (입출력 예외 처리 완료)
- **포장 주문**: `setTakeoutOrder()` 실행
- **주문 완료 후 처리**:
  
  - `Customer.setOrder()` 를 통해 장바구니 아이템을 주문 아이템으로 복사
  
  - 배달비는 `Restaurant.getDeliveryFee()` 를 통해 측정
  
  - VIP 고객(`VIPCustomer`)은 배달료 무료
  
  - 주문 완료 시 `OrderStatus` 변경 및 주문 내역 조회 가능
 

## 🏗️ 클래스 다이어그램
![Image](https://github.com/user-attachments/assets/f706c0d2-cfeb-4a62-b741-736a3b600a47)
⠀
---
이 프로젝트는 고객이 쉽고 빠르게 음식을 주문할 수 있도록 설계되었습니다.  
지속적인 개선을 통해 더 나은 서비스 제공을 목표로 합니다. 🚀
