package uit.se122.ieltstinder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uit.se122.ieltstinder.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatroomId")
    Page<Message> getMessageByChatroomId(Long chatroomId, Pageable pageable);

}
