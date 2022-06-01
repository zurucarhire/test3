package com.cellulant.iprs.api;

import com.cellulant.iprs.dto.ExperienceDTO;
import com.cellulant.iprs.entity.Experience;
import com.cellulant.iprs.entity.ExperienceComment;
import com.cellulant.iprs.entity.Product;
import com.cellulant.iprs.service.IExperienceService;
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
@RequestMapping("/api/psm/experience")
@Slf4j
public class ExperienceResource {
    private final IExperienceService experienceService;

    @GetMapping("/findall")
    public ResponseEntity<List<ExperienceDTO>> findAll() {
        log.info("info {}",experienceService.findAll());
        return ResponseEntity.ok().body(experienceService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> findCount() {
        log.info("findCount");
        return ResponseEntity.ok().body(experienceService.count());
    }

    @PostMapping("/create/{procedureID}/{name}/{description}")
    public ResponseEntity<Experience> create(@PathVariable(value = "procedureID") Long procedureID,
                                             @PathVariable(value = "name") String name,
                                             @PathVariable(value = "description") String description) {
        log.info("createExperience {} {} {}", procedureID, name, description);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/experience/create").toUriString());
        return ResponseEntity.created(uri).body(experienceService.create(procedureID, name, description));
    }

    @PostMapping("/createcomment/{experienceID}/{name}/{description}")
    public ResponseEntity<ExperienceComment> createComent(@PathVariable(value = "experienceID") Long experienceID,
                                                          @PathVariable(value = "name") String name,
                                                          @PathVariable(value = "description") String description) {
        log.info("createExperienceComment {} {} {}", experienceID, name, description);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/experience/create").toUriString());
        return ResponseEntity.created(uri).body(experienceService.createComment(experienceID, name, description));
    }

    @PostMapping("/createexperience")
    public ResponseEntity<Experience> createExperience(
            @RequestParam("procedureID") Long procedureID,
            @RequestParam("category") String category,
            @RequestParam("title") String title,
            @RequestParam("completed") String completed,
            @RequestParam("cost") double cost,
            @RequestParam("description") String description,
            @RequestParam(value = "thumbnail", required = false) MultipartFile[] thumbnail) {
        log.info("createExperience {} {} {} {} {} {} {}", procedureID, category, title, completed, cost, description, thumbnail);
        //return null;
        return ResponseEntity.ok(experienceService.createExperience(procedureID, category, title, completed, cost, description, thumbnail));
    }
}
