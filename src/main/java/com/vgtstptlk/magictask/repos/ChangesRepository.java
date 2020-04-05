package com.vgtstptlk.magictask.repos;

import com.vgtstptlk.magictask.domain.Changes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface ChangesRepository extends JpaRepository<Changes, Long> {
    Optional<Changes>  findByTaskUserUsernameAndDateUpdateAndDescription(String username, Date dateUpdate, String description);
    Collection<Changes> findByTaskUserUsernameAndDateUpdateBetween(String username, Date dateFrom, Date dateTo);
    Optional<Changes> findByTaskUserUsernameAndTaskIdAndDateUpdate(String username, Long id, Date dateUpdate);
    Collection<Changes> findByTaskUserUsernameAndDescription(String username, String description);
    Collection<Changes> findByTaskUserUsername(String username);
    Collection<Changes> findByTaskUserUsernameAndTaskId(String username, Long id);
    Optional<Changes> findByTaskUserUsernameAndTaskIdAndDateUpdateAndDescription(String userId, Long id, Date date, String created);
}
