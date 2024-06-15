package uit.se122.ieltstinder.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public final class DateUtils {

    public static final String GMT_7_TIMEZONE = "Asia/Bangkok";
    public static final ZoneId GMT_7_ZONE_ID = ZoneId.of(GMT_7_TIMEZONE);
    public static final Clock GMT_7_CLOCK = Clock.system(GMT_7_ZONE_ID);

    private DateUtils() {}

    public static Instant currentInstant() {
        return Instant.now(Clock.system(GMT_7_ZONE_ID));
    }
}
