package com.example.project.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = CartItem.class, mappedBy = "cart", cascade = CascadeType.DETACH)
    private Set<CartItem> items;

    public Set<CartItem> getItems() {
        return items;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column
    private double total;

    public Cart(Set<CartItem> items, double total) {
        this.items = items;
        this.total = total;
    }

    public Cart(Set<CartItem> items, double total, Customer customer) {
        this.items = items;
        this.total = total;
        this.customer = customer;
    }

}