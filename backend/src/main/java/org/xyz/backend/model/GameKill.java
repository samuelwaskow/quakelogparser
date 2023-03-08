package org.xyz.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_kill")
@NoArgsConstructor
@AllArgsConstructor
public final class GameKill {

    @Id
    private String player;

    private int kills;

    public void increaseKills(){
        this.kills++;
    }

    public void decreaseKills(){
        this.kills--;
    }
}
