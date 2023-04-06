package stracture.football.service;

import stracture.football.model.Player;
import stracture.football.model.Team;
import stracture.football.repository.PlayerRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository repository;
    private final TeamService teamService;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int PERCENTAGE_BASE = 100;
    private static final long GENERAL_AMOUNT_OF_PLAYERS = 100000L;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final int EMPTY_BALANCE = 0;

    public PlayerServiceImpl(PlayerRepository repository,
                             TeamService teamService) {
        this.repository = repository;
        this.teamService = teamService;
    }

    @Override
    public Player save(Player player) {
        return repository.save(player);
    }

    @Override
    public Player update(Player player) {
        return repository.save(player);
    }

    @Override
    public List<Player> getAll() {
        return repository.findAll();
    }

    @Override
    public Player get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find player by id: " + id));
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public boolean transfer(Long playerId, String title) {
        Optional<Team> newTeamOfPlayer = teamService.findTeamByTitle(title);
        if (newTeamOfPlayer.isEmpty()) {
            throw new RuntimeException("Can't find team by title: " + title);
        }
        Player player = get(playerId);
        BigDecimal totalCost = calculateTotalCost(player);
        BigDecimal balanceOfTeamIncludedTransfer = newTeamOfPlayer.get().getBalance().subtract(totalCost);
        if (balanceOfTeamIncludedTransfer.compareTo(BigDecimal.ZERO) < EMPTY_BALANCE) {
            return false;
        }
        doTransferOperation(newTeamOfPlayer.get(), player, balanceOfTeamIncludedTransfer, totalCost);
        return true;
    }

    private void doTransferOperation(Team newTeam, Player player,
                                     BigDecimal updatedBalance, BigDecimal totalCost) {
        Team currentTeam = player.getTeam();
        currentTeam.setBalance(currentTeam.getBalance().add(totalCost));
        currentTeam.getPlayers().remove(player);
        teamService.save(currentTeam);

        player.setTeam(newTeam);
        save(player);

        newTeam.setBalance(updatedBalance);
        newTeam.getPlayers().add(player);
        teamService.save(newTeam);
    }

    private BigDecimal calculateTotalCost(Player player) {
        BigDecimal transferCost = calculateCostOfTransfer(player);
        Double commission = player.getTeam().getCommission();
        return transferCost.multiply(ONE.add(BigDecimal.valueOf(commission / PERCENTAGE_BASE)));
    }

    private BigDecimal calculateCostOfTransfer(Player player) {
        int agesOfCareer = LocalDate.now().getYear() - player.getStartCareer().getYear();
        int monthsOfCareer = agesOfCareer * MONTHS_IN_YEAR + (MONTHS_IN_YEAR - player.getStartCareer().getMonthValue());
        int ageOfPlayer = LocalDate.now().getYear() - player.getBirthDate().getYear();
        return BigDecimal.valueOf(monthsOfCareer * GENERAL_AMOUNT_OF_PLAYERS / ageOfPlayer);
    }
}
