package uit.se122.ieltstinder.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.se122.ieltstinder.service.query.Criteria;
import uit.se122.ieltstinder.service.query.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCriteria  implements Serializable, Criteria {

    private LongFilter userId;

    public ChatRoomCriteria(ChatRoomCriteria other) {
        this.userId = Objects.nonNull(other.userId) ? other.userId : null;
    }

    @Override
    public Criteria copy() {
        return new ChatRoomCriteria(this);
    }

}
