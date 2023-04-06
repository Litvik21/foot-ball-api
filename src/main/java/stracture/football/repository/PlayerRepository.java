package stracture.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stracture.football.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
