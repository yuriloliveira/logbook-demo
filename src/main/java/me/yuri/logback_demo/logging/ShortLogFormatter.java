package me.yuri.logback_demo.logging;

import org.zalando.logbook.core.SplunkHttpLogFormatter;

import java.util.Map;
import java.util.stream.Stream;

public class ShortLogFormatter extends SplunkHttpLogFormatter {
    @Override
    public String format(final Map<String, Object> content) {
        Stream.of("origin", "protocol", "remote", "host", "scheme").forEach(content::remove);
        return super.format(content);
    }
}
