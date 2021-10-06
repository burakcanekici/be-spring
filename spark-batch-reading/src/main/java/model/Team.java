package model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Team {
    private Long id;
    private String name;
    private Long league_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public void setLeague_id(Long league_id) {
        this.league_id = league_id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(null)
                .append("id", id)
                .append("name", name)
                .append("league_id", league_id)
                .toString();
    }
}
