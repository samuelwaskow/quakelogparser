package org.xyz.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public final class GameKill {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String player;

    private int kills;

    /**
     * Default Constructor
     *
     * @param player Player's name
     * @param kills Player's number of kills
     */
    public GameKill(final String player, final int kills) {
        this.player = player;
        this.kills = kills;
    }

    public void increaseKills() {
        this.kills++;
    }

    public void decreaseKills() {
        this.kills--;
    }
}
