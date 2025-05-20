package me.yuri.logback_demo.summary.internal;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summaries")
public class SummaryController {
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping
    public ResponseEntity<Summary> loadSummaryByIban(@RequestBody SummaryRequest request) {
        return summaryService
            .loadSummaryByIban(request.iban())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.of(buildAccountNotFoundProblemDetail()).build());
    }

    private static ProblemDetail buildAccountNotFoundProblemDetail() {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), "Account not found");
    }
}
