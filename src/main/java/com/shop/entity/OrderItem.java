package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격
    private int count;      //수량

    //주문할 상품과 주문 수량을 통해 주문상품(OrderItem) 객체를 만드는 메소드
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    //해당 상품을 주문한 총 가격을 계산하는 메소드
    public int getTotalPrice() {
        return orderPrice * count; //주문가격 * 주문수량
    }

    //주문 취소 시 주문 수량만큼 상품의 재고를 더해주는 메소드
    public void cancel() {
        this.getItem().addStock(count);
    }
}
