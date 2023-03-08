package org.xyz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.backend.model.Game;

public interface AccessRepository extends JpaRepository<Game, String> {
}
