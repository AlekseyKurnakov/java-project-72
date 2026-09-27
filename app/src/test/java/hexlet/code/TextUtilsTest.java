package hexlet.code;

import hexlet.code.util.TextUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TextUtilsTest {

    @Test
    void testTruncateShortText() {
        String result = TextUtils.truncate("short text", 200);
        assertThat(result).isEqualTo("short text");
    }

    @Test
    void testTruncateLongText() {
        String longText = "a".repeat(250);
        String result = TextUtils.truncate(longText, 200);

        assertThat(result).hasSize(203);
        assertThat(result).endsWith("...");
    }

    @Test
    void testTruncateNull() {
        String result = TextUtils.truncate(null, 200);
        assertThat(result).isNull();
    }
}
