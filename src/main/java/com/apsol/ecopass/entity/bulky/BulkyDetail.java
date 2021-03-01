package com.apsol.ecopass.entity.bulky;

import com.apsol.ecopass.core.enums.BulkyState;
import com.apsol.ecopass.entity.Photo;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bulky_details")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyDetail {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 주문(상위)건 FK
     */
    @ManyToOne
    @JoinColumn(name = "bulky_order")
    private BulkyOrder bulkyOrder;

    /**
     * 주문(상위)건 FK
     */
    @ManyToOne
    @JoinColumn(name = "cancel_order")
    private BulkyOrder cancelOrder;

    /**
     * 사진 FK
     */
    @ManyToOne
    @JoinColumn(name = "photo")
    private Photo photo;

    /**
     * 수거 직원 FK
     */
    @ManyToOne
    @JoinColumn(name = "emp_collector")
    @Setter(AccessLevel.NONE)
    private Employee empCollector;

    /**
     * 결재 직원 FK
     */
    @ManyToOne
    @JoinColumn(name = "emp_manager")
    private Employee empManager;

    /**
     * 대형폐기물 품목 FK
     */
    @ManyToOne
    @JoinColumn(name = "bulky_item")
    private BulkyItem bulkyItem;

    /**
     * 배출번호
     */
    @Column(name = "detail_no", nullable = false, updatable = false, unique = true, length = 13)
    @Setter(AccessLevel.NONE)
    private String detailNo;

    /**
     * 품목 분류
     */
    @Column(name = "category_name", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String categoryName;

    /**
     * 품목 이름
     */
    @Column(name = "item_name", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String itemName;

    /**
     * 품목 규격
     */
    @Column(name = "item_standard", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String itemStandard;

    /**
     * 품목 가격
     */
    @Column(name = "item_price", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal itemPrice = BigDecimal.ZERO;

    /**
     * 상태값
     */
    @Column(name = "bulky_state", nullable = false)
    @Setter(AccessLevel.NONE)
    private BulkyState bulkyState;

    /**
     * 업데이트 시간
     */
    @Column(name = "updated_datetime", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDatetime = new Date();

    /**
     * 업데이트 시간
     */
    @Column(name = "sms_sended", nullable = false)
    private boolean smsSended = false;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
