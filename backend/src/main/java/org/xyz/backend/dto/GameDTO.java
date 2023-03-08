package org.xyz.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonPropertyOrder({ "total_kills", "players", "kills" })
public final class GameDTO {

    @JsonIgnore
    private String name;

    @JsonProperty("total_kills")
    private int totalKills;

    private List<String> players;

    private Map<String, Integer> kills;

}
