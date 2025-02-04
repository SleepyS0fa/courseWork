package ru.sleepy_sofa.cartridgeproject.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;
    @OneToOne
    @JoinColumn(name = "cartridge_id")
    private Cartridge cartridge;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Queue(Cartridge cartridge, Office office) {
        this.cartridge = cartridge;
        this.office = office;
    }

    public Queue(Long id, Office office, Cartridge cartridge, LocalDateTime createdAt) {
        this.id = id;
        this.office = office;
        this.cartridge = cartridge;
        this.createdAt = createdAt;
    }

    public Queue() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Cartridge getCartridge() {
        return this.cartridge;
    }

    public void setCartridge(Cartridge cartridge) {
        this.cartridge = cartridge;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", office=" + office +
                ", cartridge=" + cartridge +
                ", createdAt=" + createdAt +
                '}';
    }
}
