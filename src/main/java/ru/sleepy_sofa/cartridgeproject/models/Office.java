package ru.sleepy_sofa.cartridgeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cartridge> cartridges = new ArrayList<>();

    public Office(Long id, String name, List<Cartridge> cartridges) {
        this.id = id;
        this.name = name;
        this.cartridges = cartridges;
    }

    public Office() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cartridge> getCartridges() {
        return this.cartridges;
    }

    public void setCartridges(List<Cartridge> cartridges) {
        this.cartridges = cartridges;
    }

}
