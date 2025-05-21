package me.yuri.logback_demo.logging;

import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.SplunkHttpLogFormatter;

import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;

@Configuration
public class LogbookConfiguration {
//    @Bean
    public Logbook logbook(HttpLogWriter writer) {
        return Logbook
            .builder()
            .sink(new DefaultSink(new SplunkHttpLogFormatter(), writer))
            .condition(request -> request.getPath().equalsIgnoreCase("/summaries"))
            .headerFilter(headers -> headers.update("x-iban", "XXX"))
            .bodyFilter(jsonPath("iban").replace("XXX"))
            .build();
    }
}
