package me.yuri.logback_demo.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;
import org.zalando.logbook.core.DefaultSink;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class LogbookConfiguration {
    @Bean
    public Logbook logbook(HttpLogWriter writer) {
        HttpLogFormatter formatter = new ShortLogFormatter();
        return Logbook
                .builder()
                .sink(new DefaultSink(formatter, writer))
                .headerFilter(headers -> {
                    Map<String, List<String>> filteredHeaders = headers
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase("x-iban"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return HttpHeaders.of(filteredHeaders);
                })
                .build();
    }
}
