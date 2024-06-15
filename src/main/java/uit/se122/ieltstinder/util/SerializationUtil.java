package uit.se122.ieltstinder.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uit.se122.ieltstinder.exception.ParseJsonException;

import java.util.*;

public final class SerializationUtil {
    private SerializationUtil() {}

    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    }

    public static <T> T readValue(final String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception exception) {
            throw new ParseJsonException("Parse json to object error", exception);
        }
    }

    public static <T> List<T> readListValue(final String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                    ArrayList.class,
                    valueType
            ));
        } catch (Exception exception) {
            throw new ParseJsonException("Parse json to object error", exception);
        }
    }

    public static String writeValueAsString(final Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception exception) {
            throw new ParseJsonException("Parse object to json error", exception);
        }
    }

    public static Map<String, Object> convertValueToMap(final Object value) {
        try {
            if (Objects.isNull(value)) {
                return new HashMap<>();
            }
            return mapper.convertValue(value, new TypeReference<Map<String, Object>>() {});
        } catch (Exception exception) {
            throw new ParseJsonException("Convert object to Map error", exception);
        }
    }

    public static String removeWhiteSpaces(String json) {
        if (Objects.isNull(json)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean quoted = false;
        boolean escaped = false;
        for (Character c : json.toCharArray()) {
            if (escaped) {
                result.append(c);
                escaped = false;
                continue;
            }
            if (c == '"') {
                quoted = !quoted;
            } else if (c == '\\') {
                escaped = true;
            }
            if ((c == ' ' && !quoted) || (c == '\n')) {
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }
}
