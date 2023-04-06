package stracture.football.controller;

import stracture.football.model.Player;
import stracture.football.model.Team;
import stracture.football.service.GeneratePlayersService;
import stracture.football.service.GenerateTeamsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/fill")
public class FillDataController {
    private final GeneratePlayersService generatePlayersService;
    private final GenerateTeamsService generateTeamsService;

    public FillDataController(GeneratePlayersService generatePlayersService,
                              GenerateTeamsService generateTeamsService) {
        this.generatePlayersService = generatePlayersService;
        this.generateTeamsService = generateTeamsService;
    }

    @GetMapping
    public String fillData() {
        Team team1 = generateTeamsService.generateTeam();
        List<Player> players1 = generatePlayersService.generatePlayersOfTeam(team1);
        generateTeamsService.setPlayersIntoTeam(team1, players1);

        Team team2 = generateTeamsService.generateTeam();
        List<Player> players2 = generatePlayersService.generatePlayersOfTeam(team2);
        generateTeamsService.setPlayersIntoTeam(team2, players2);

        return "Done!";
    }


}
