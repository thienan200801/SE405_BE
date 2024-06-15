package uit.se122.ieltstinder.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.se122.ieltstinder.entity.enumeration.Role;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;
import uit.se122.ieltstinder.entity.enumeration.UserStatus;
import uit.se122.ieltstinder.service.query.Criteria;
import uit.se122.ieltstinder.service.query.filter.Filter;
import uit.se122.ieltstinder.service.query.filter.LongFilter;
import uit.se122.ieltstinder.service.query.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCriteria implements Serializable, Criteria {

    private LongFilter id;
    private StringFilter name;
    private StringFilter email;
    private UserRoleFilter role;
    private UserStatusFilter status;
    private TestLevelFilter level;

    public UserCriteria(UserCriteria other) {
        this.email = Objects.nonNull(other.email) ? other.email : null;
        this.name = Objects.nonNull(other.name) ? other.name : null;
        this.role = Objects.nonNull(other.role) ? other.role : null;
        this.status = Objects.nonNull(other.status) ? other.status : null;
        this.id = Objects.nonNull(other.id) ? other.id : null;
        this.level = Objects.nonNull(other.level) ? other.level : null;
    }

    @Override
    public Criteria copy() {
        return new UserCriteria(this);
    }

    public static class UserRoleFilter extends Filter<Role> implements Serializable {
        private static final long serialVersionUID = 1L;

        public UserRoleFilter() {
        }

        public UserRoleFilter(UserRoleFilter other) {
            super(other);
        }

        @Override
        public Filter<Role> copy() {
            return new UserRoleFilter(this);
        }
    }

    public static class UserStatusFilter extends Filter<UserStatus> implements Serializable {
        private static final long serialVersionUID = 1L;

        public UserStatusFilter() {
        }

        public UserStatusFilter(UserStatusFilter other) {
            super(other);
        }

        @Override
        public Filter<UserStatus> copy() {
            return new UserStatusFilter(this);
        }
    }

    public static class TestLevelFilter extends Filter<TestLevel> implements Serializable {
        private static final long serialVersionUID = 1L;

        public TestLevelFilter() {
        }

        public TestLevelFilter(TestLevelFilter other) {
            super(other);
        }

        @Override
        public Filter<TestLevel> copy() {
            return new TestLevelFilter(this);
        }
    }

}
