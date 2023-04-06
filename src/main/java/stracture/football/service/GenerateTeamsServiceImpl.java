package stracture.football.service;

import stracture.football.model.Player;
import stracture.football.model.Team;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class GenerateTeamsServiceImpl implements GenerateTeamsService {
    private static final List<String> CITIES =
            List.of("Havenworth", "Starlake", "Thunderwood", "Moonhaven");
    private static final List<String> COUNTRIES =
            List.of("Celestia", "Valtara", "Skylandia", "Deltora");
    private static final List<String> TEAM_TITLES =
            List.of("ThunderingFC", "ThunderboltsFC", "CrimsonStrikers", "StarlightUnited");
    private static final List<BigDecimal> BALANCES =
            List.of(BigDecimal.valueOf(1239492), BigDecimal.valueOf(3400000),
                    BigDecimal.valueOf(700000), BigDecimal.valueOf(12300012));
    private static final List<Double> COMMISSIONS =
            List.of(4.4, 5.9, 9.0, 4.8, 7.5, 8.1);
    private final TeamService teamService;

    public GenerateTeamsServiceImpl(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public Team generateTeam() {
        Team team = new Team();
        team.setTitle(checkTitle());
        team.setCity(CITIES.get(new Random().nextInt(CITIES.size())));
        team.setCountry(COUNTRIES.get(new Random().nextInt(COUNTRIES.size())));
        team.setCommission(COMMISSIONS.get(new Random().nextInt(COMMISSIONS.size())));
        team.setBalance(BALANCES.get(new Random().nextInt(BALANCES.size())));
        team.setPlayers(new ArrayList<>());

        return teamService.save(team);
    }

    private String checkTitle() {
        String randomTitle = TEAM_TITLES.get(new Random().nextInt(TEAM_TITLES.size()));
        Optional<Team> teamByTitle = teamService
                .findTeamByTitle(randomTitle);
        if (teamByTitle.isPresent()) {
            checkTitle();
        }
        return randomTitle;
    }

    @Override
    public void setPlayersIntoTeam(Team team, List<Player> players) {
        team.setPlayers(players);
        teamService.save(team);
    }
}
