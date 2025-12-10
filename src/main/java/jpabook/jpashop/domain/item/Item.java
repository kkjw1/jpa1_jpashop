package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 엔티티 자체에서 해결할 수 있는 것은 엔티티 자체에서 처리하는 것이 좋음
    // service 계층에서 setter로 넣고 뺴는것은 좋지않음

    /**
     * 제고 증가 (stock)
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 제고 감소 (stock)
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
