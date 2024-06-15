package uit.se122.ieltstinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.entity.ChatRoom;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, JpaSpecificationExecutor<ChatRoom> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatRoom c WHERE (c.user1.id = :userId1 AND c.user2.id = :userId2)" +
    "OR (c.user1.id = :userId2 AND c.user2.id = :userId1)")
    void deleteByTwoUserId(Long userId1, Long userId2);

    @Query("SELECT c FROM ChatRoom c WHERE c.user1.id = :userId OR c.user2.id = :userId2")
    List<ChatRoom> getChatRoomByUserId(Long userId);

}
