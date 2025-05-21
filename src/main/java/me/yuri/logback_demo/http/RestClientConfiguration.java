package me.yuri.logback_demo.http;

import me.yuri.logback_demo.logging.ShortLogFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

import java.util.regex.Pattern;

import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;

@Configuration
public class RestClientConfiguration {

    public static final String HOST = "http://localhost:8080";
    public static final Pattern IBAN_REPLACEMENT_PATTERN = Pattern.compile("(.{8}).*(.{4})");

    @Bean
    @Primary
    @Qualifier("DefaultRestClient")
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl(HOST)
                .build();
    }

    @Bean
    @Qualifier("RestClientWithLogging")
    public RestClient restClientWithLogging(HttpLogWriter httpLogWriter) {
        return RestClient
                .builder()
                .baseUrl(HOST)
                .requestInterceptor(new LogbookClientHttpRequestInterceptor(configureLogbook(httpLogWriter)))
                .build();
    }

    private static Logbook configureLogbook(HttpLogWriter httpLogWriter) {
        return Logbook
                .builder()
                .sink(new DefaultSink(new ShortLogFormatter(), httpLogWriter))
                .headerFilter(headers -> {
                    if (headers.containsKey("x-iban")) {
                        var iban = headers.getFirst("x-iban");
                        if (iban != null) {
                            return headers.update("x-iban", hiddenIban(iban));
                        }
                    }
                    return headers;
                })
                .queryFilter(query -> query.replaceAll("iban=[^&]+", "iban=XXX"))
                .pathFilter(path -> path.replaceAll("/accounts/.*$", "/accounts/XXX"))
                .bodyFilter(jsonPath("sourceIban").replace(IBAN_REPLACEMENT_PATTERN, "$1*****$2"))
                .bodyFilter(jsonPath("targetIban").replace(IBAN_REPLACEMENT_PATTERN, "$1*****$2"))
                .build();
    }

    private static String hiddenIban(String iban) {
        return iban.substring(0, 8) + "*****" + iban.substring(iban.length() - 4);
    }
}
