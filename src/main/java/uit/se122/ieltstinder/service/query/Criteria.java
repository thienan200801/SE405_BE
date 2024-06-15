package uit.se122.ieltstinder.service.query;

/**
 * Implementation should usually contain fields of Filter instances.
 */
public interface Criteria {

    /**
     * @return a new criteria with copied filters
     */
    Criteria copy();
}
