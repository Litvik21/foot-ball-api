package stracture.football.service;

import stracture.football.model.Team;
import stracture.football.repository.TeamRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public Team update(Team team) {
        return repository.save(team);
    }

    @Override
    public List<Team> getAll() {
        return repository.findAll();
    }

    @Override
    public Team get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find team by id: " + id));
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public Optional<Team> findTeamByTitle(String title) {
        return repository.findTeamByTitle(title);
    }
}
