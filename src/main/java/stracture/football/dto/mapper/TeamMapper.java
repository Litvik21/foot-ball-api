package stracture.football.dto.mapper;

import org.springframework.stereotype.Component;
import stracture.football.dto.TeamRequestDto;
import stracture.football.dto.TeamResponseDto;
import stracture.football.model.Player;
import stracture.football.model.Team;
import stracture.football.service.PlayerService;

@Component
public class TeamMapper {
    private final PlayerService playerService;

    public TeamMapper(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Team toModel(TeamRequestDto dto) {
        Team team = new Team();
        team.setTitle(dto.title());
        team.setCountry(dto.country());
        team.setCity(dto.city());
        team.setBalance(dto.balance());
        team.setCommission(dto.commission());
        if (!dto.playerIds().isEmpty()) {
            team.setPlayers(dto.playerIds().stream()
                    .map(playerService::get).toList());
        }

        return team;
    }

    public TeamResponseDto toDto(Team team) {
        return new TeamResponseDto(
                team.getId(),
                team.getTitle(),
                team.getCountry(),
                team.getCity(),
                team.getBalance(),
                team.getCommission(),
                team.getPlayers().stream().map(Player::getId).toList()
        );
    }
}
