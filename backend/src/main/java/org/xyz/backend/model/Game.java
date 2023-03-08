package org.xyz.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public final class Game {

    @Id
    private String id;

    private int totalKills;
    @OneToMany(cascade = CascadeType.ALL)
    private final List<GameKill> kills = new ArrayList<>();

    /**
     * Default Constructor
     *
     * @param id Game identifier
     */
    public Game(final String id) {
        this.id = id;
    }

    public void addTotalKills() {
        this.totalKills++;
    }
}
