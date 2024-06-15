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
public class RequestCriteria implements Serializable, Criteria {

    private LongFilter receiver;
    private LongFilter sender;

    public RequestCriteria(RequestCriteria other) {
        this.receiver = Objects.nonNull(other.receiver) ? other.receiver : null;
        this.sender = Objects.nonNull(other.sender) ? other.sender : null;
    }

    @Override
    public Criteria copy() {
        return new RequestCriteria(this);
    }

}
