package testenum;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Optional;
import java.util.regex.Pattern;

public enum BooleanEnum {
    TRUE(true),
    FALSE(false);

    private final boolean value;

    BooleanEnum(boolean value) {
        this.value = value;
    }

    public static Optional<BooleanEnum> fromValue(String value) {
        // check if value is valid boolean string
        if (isValidBoolean(value)) {
            return BooleanUtils.toBoolean(value) ?
                    Optional.of(BooleanEnum.TRUE):
                    Optional.of(BooleanEnum.FALSE);
        } else {
            return Optional.empty();
        }
    }

    private static boolean isValidBoolean(String value) {
        String booleanPattern = "^(?i)(true|false|yes|no|0|1)$";
        return Pattern.matches(booleanPattern, value);
    }

    public boolean getValue() {
        return value;
    }
}
