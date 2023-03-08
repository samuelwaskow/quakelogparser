package org.xyz.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "game")
@RequiredArgsConstructor
public final class Game {

    @Id
    private final String id;

    private int totalKills;
    @OneToMany(cascade = CascadeType.ALL)
    private final List<GameKill> kills = new ArrayList<>();


    public void addTotalKills(){
        this.totalKills++;
    }
}
