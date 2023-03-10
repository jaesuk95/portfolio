package com.portfolio.domain.model.order;

import com.portfolio.domain.model.address.Address;
import com.portfolio.domain.model.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderDetailNumber;

    @ManyToOne(fetch = FetchType.LAZY)      // ManyToOne 은 항상 연관관계의 주인이 된다.
    @JoinColumn(name = "ORDER_ID")
    private UserOrder userOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @Lob
    private String optionJson;
}
