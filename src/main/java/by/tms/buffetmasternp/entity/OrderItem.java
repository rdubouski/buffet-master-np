package by.tms.buffetmasternp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderBox order;

    @ManyToOne
    @JoinColumn(name = "id_box")
    private Box box;

    @Column(name = "quantity")
    private int quantity;
}
