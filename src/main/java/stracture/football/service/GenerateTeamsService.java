package stracture.football.service;

import stracture.football.model.Player;
import stracture.football.model.Team;
import java.util.List;

public interface GenerateTeamsService {
    Team generateTeam();

    void setPlayersIntoTeam(Team team, List<Player> players);
}
