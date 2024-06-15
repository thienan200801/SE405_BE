package uit.se122.ieltstinder.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.se122.ieltstinder.entity.enumeration.PostStatus;
import uit.se122.ieltstinder.service.query.Criteria;
import uit.se122.ieltstinder.service.query.filter.Filter;
import uit.se122.ieltstinder.service.query.filter.LongFilter;
import uit.se122.ieltstinder.service.query.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCriteria implements Serializable, Criteria {

    private StringFilter name;
    private PostStatusFilter status;
    private LongFilter userId;

    public PostCriteria(PostCriteria other) {
        this.name = Objects.nonNull(other.name) ? other.name : null;
        this.status = Objects.nonNull(other.status) ? other.status : null;
        this.userId = Objects.nonNull(other.userId) ? other.userId : null;
    }

    @Override
    public Criteria copy() {
        return new PostCriteria(this);
    }

    public static class PostStatusFilter extends Filter<PostStatus> implements Serializable {
        private static final long serialVersionUID = 1L;

        public PostStatusFilter() {

        }

        public PostStatusFilter(PostStatusFilter other) {
            super(other);
        }

        @Override
        public Filter<PostStatus> copy() {
            return new PostStatusFilter(this);
        }
    }

}
