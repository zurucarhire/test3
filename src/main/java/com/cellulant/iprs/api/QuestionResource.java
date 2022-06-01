package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.Question;
import com.cellulant.iprs.service.IExperienceService;
import com.cellulant.iprs.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/question")
@Slf4j
public class QuestionResource {
    private final IQuestionService questionService;

    @GetMapping("/findall")
    public ResponseEntity<List<Question>> findAll() {
        return ResponseEntity.ok().body(questionService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> findCount() {
        log.info("findCount");
        return ResponseEntity.ok().body(questionService.count());
    }

    @PostMapping("/create/{procedureID}/{name}/{title}/{description}")
    public ResponseEntity<Question> create(@PathVariable(value = "procedureID") Long procedureID,
                                           @PathVariable(value = "name") String name,
                                           @PathVariable(value = "title") String title,
                                             @PathVariable(value = "description") String description) {
        log.info("createQuestion {} {} {} {}", procedureID, name, title, description);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/question/create").toUriString());
        return ResponseEntity.created(uri).body(questionService.create(procedureID, name, title, description));
    }

    @PostMapping("/createquestion")
    public ResponseEntity<Question> createQuestion(
            @RequestParam("procedureID") Long procedureID,
            @RequestParam("category") String category,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "thumbnail", required = false) MultipartFile[] thumbnail) {
        log.info("createQuestion {} {} {} {} {}", procedureID, category, title, description, thumbnail);
        //return null;
        return ResponseEntity.ok(questionService.createQuestion(procedureID, category, title, description, thumbnail));
    }
}
