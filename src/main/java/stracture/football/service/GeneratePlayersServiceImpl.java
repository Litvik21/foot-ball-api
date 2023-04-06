package stracture.football.service;

import stracture.football.model.Player;
import stracture.football.model.Team;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class GeneratePlayersServiceImpl implements GeneratePlayersService {
    private static final int COUNT_OF_PLAYERS = 11;
    private static final List<String> NAMES =
            List.of("James", "John", "David", "Murad", "James", "Artem");
    private static final List<String> SURNAMES =
            List.of("Smith", "Lebron", "Mironov", "Smirnov", "Rudynov", "Bulgakov");
    private static final List<LocalDate> START_CAREER_DATES =
            List.of(LocalDate.of(2016, 9, 5), LocalDate.of(2014, 3, 14),
                    LocalDate.of(2017, 11, 11), LocalDate.of(2019, 4, 1),
                    LocalDate.of(2020, 12, 17), LocalDate.of(2011, 8, 10));
    private static final List<LocalDate> BIRTH_DATES =
            List.of(LocalDate.of(1999, 4, 5), LocalDate.of(2001, 11, 22),
                    LocalDate.of(2002, 1, 19), LocalDate.of(2000, 10, 14),
                    LocalDate.of(1998, 12, 18), LocalDate.of(1999, 9, 14));
    private final PlayerService playerService;

    public GeneratePlayersServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }


    @Override
    public List<Player> generatePlayersOfTeam(Team team) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < COUNT_OF_PLAYERS; i++) {
            Player player = new Player();
            player.setFirstName(NAMES.get(new Random().nextInt(NAMES.size())));
            player.setLastName(SURNAMES.get(new Random().nextInt(SURNAMES.size())));
            player.setBirthDate(BIRTH_DATES.get(new Random().nextInt(BIRTH_DATES.size())));
            player.setStartCareer(START_CAREER_DATES.get(new Random().nextInt(START_CAREER_DATES.size())));
            player.setTeam(team);

            players.add(playerService.save(player));
        }
        return players;
    }
}
