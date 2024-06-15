package uit.se122.ieltstinder.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, JpaSpecificationExecutor<Friend> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Friend f WHERE (f.user.id = :userId AND f.friend.id = :friendId) OR" +
    "(f.user.id = :friendId AND f.friend.id = :userId)")
    void deleteByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query("SELECT f FROM Friend f WHERE f.friend.id = :userId OR f.user.id = :userId")
    Page<Friend> findByFkUserId(@Param("userId") Long userId, Pageable pageable);

}
