package stracture.football.dto.mapper;

import stracture.football.dto.TeamRequestDto;
import stracture.football.dto.TeamResponseDto;
import stracture.football.model.Player;
import stracture.football.model.Team;
import stracture.football.service.PlayerService;
import stracture.football.service.TeamService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    private final PlayerService playerService;
    private final TeamService teamService;

    public TeamMapper(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public Team toModel(TeamRequestDto dto) {
        Team team = new Team();
        team.setTitle(dto.title());
        team.setCountry(dto.country());
        team.setCity(dto.city());
        team.setBalance(dto.balance());
        team.setCommission(dto.commission());

        return team;
    }

    public TeamResponseDto toDto(Team team) {
        Team updatedTeam = checkUpdatesOfPlayers(team);
        return new TeamResponseDto(
                updatedTeam.getId(),
                updatedTeam.getTitle(),
                updatedTeam.getCountry(),
                updatedTeam.getCity(),
                updatedTeam.getBalance(),
                updatedTeam.getCommission(),
                updatedTeam.getPlayers().stream().map(Player::getId).toList()
        );
    }

    private Team checkUpdatesOfPlayers(Team team) {
        List<Player> playersByTeamId = playerService.findPlayersByTeamId(team.getId());
        if (team.getPlayers() == null) {
            team.setPlayers(playersByTeamId);
            return teamService.update(team);
        } else if (playersByTeamId.size() != team.getPlayers().size()) {
            Set<Player> players = new HashSet<>(team.getPlayers());
            players.addAll(playersByTeamId);
            team.setPlayers(new ArrayList<>(players));
            return teamService.update(team);
        }
        return team;
    }
}
