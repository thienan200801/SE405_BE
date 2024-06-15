package uit.se122.ieltstinder.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;
import uit.se122.ieltstinder.service.query.Criteria;
import uit.se122.ieltstinder.service.query.filter.Filter;
import uit.se122.ieltstinder.service.query.filter.LongFilter;
import uit.se122.ieltstinder.service.query.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCriteria implements Serializable, Criteria {

    private StringFilter name;
    private TestLevelFilter level;
    private LongFilter testId;

    public TestCriteria(TestCriteria other) {
        this.name = Objects.nonNull(other.name) ? other.name : null;
        this.level = Objects.nonNull(other.level) ? other.level : null;
        this.testId = Objects.nonNull(other.testId) ? other.testId : null;
    }

    @Override
    public Criteria copy() {
        return new TestCriteria(this);
    }

    public static class TestLevelFilter extends Filter<TestLevel> implements Serializable {
        private static final long serialVersionUID = 1L;

        public TestLevelFilter() {
        }

        public TestLevelFilter(TestCriteria.TestLevelFilter other) {
            super(other);
        }

        @Override
        public Filter<TestLevel> copy() {
            return new TestCriteria.TestLevelFilter(this);
        }
    }

}
