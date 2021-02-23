package reactspring.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactspring.task_manager.entities.Cards;


@Repository
@Transactional
public interface CardRepository extends JpaRepository<Cards, Long> {

    Cards findCardsById(Long id);

}
