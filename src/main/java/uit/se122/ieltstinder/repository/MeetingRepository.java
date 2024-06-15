package uit.se122.ieltstinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.se122.ieltstinder.entity.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
