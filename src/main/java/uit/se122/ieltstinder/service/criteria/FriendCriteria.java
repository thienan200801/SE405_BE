package uit.se122.ieltstinder.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.se122.ieltstinder.service.query.Criteria;
import uit.se122.ieltstinder.service.query.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendCriteria implements Serializable, Criteria {

    private StringFilter name;

    public FriendCriteria(FriendCriteria other) {
        this.name = Objects.nonNull(other.name) ? other.name : null;
    }

    @Override
    public Criteria copy() {
        return new FriendCriteria(this);
    }

}
