package stracture.football.service;

import stracture.football.model.Player;
import stracture.football.model.Team;
import java.util.List;

public interface GeneratePlayersService {
    List<Player> generatePlayersOfTeam(Team team);
}
