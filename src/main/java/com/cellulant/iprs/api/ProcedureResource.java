package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.ChangeLog;
import com.cellulant.iprs.entity.Procedure;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IProcedureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/procedure")
@Slf4j
public class ProcedureResource {
    private final IProcedureService procedureService;

    @GetMapping("/findall")
    public ResponseEntity<List<Procedure>> findAll(){
        log.info("findAllProcedures");
        return ResponseEntity.ok().body(procedureService.findAll());
    }

    @GetMapping("/findallname/{name}")
    public ResponseEntity<List<Procedure>> findAllName(@PathVariable (value = "name") String name){
        log.info("findAllName {}", name);
        return ResponseEntity.ok().body(procedureService.findAllName(name));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> findCount() {
        log.info("findCount");
        return ResponseEntity.ok().body(procedureService.count());
    }

    @GetMapping("/findbycategory/{category}")
    public ResponseEntity<List<Procedure>> findByCategory(@PathVariable (value = "category") String category){
        log.info("findByCategory");
        return ResponseEntity.ok().body(procedureService.findByCategory(category));
    }

    @GetMapping("/findbyname/{name}")
    public ResponseEntity<List<Procedure>> findByName(@PathVariable (value = "name") String name){
        log.info("findByName");
        return ResponseEntity.ok().body(procedureService.findByName(name));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<List<Procedure>> findById(@PathVariable (value = "id") Long id){
        log.info("findByName");
        return ResponseEntity.ok().body(procedureService.findById(id));
    }

    // ADMIN
    @PostMapping("/create")
    public ResponseEntity<Procedure> create(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("subcategory") String subcategory,
            @RequestParam("type") String type,
            @RequestParam("subtype") String subtype,
            @RequestParam("city") String city,
            @RequestParam("cost") Double cost,
            @RequestParam("description") String description,
            @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        log.info("save {} {} {} {} {} {} {} {} {}", name, category, subcategory, type,
                subtype, city, cost, description, thumbnail);

        return ResponseEntity.ok(procedureService.create(name, category, subcategory, type, subtype, city, cost, description, thumbnail));
    }

    @PutMapping("/update")
    public ResponseEntity<Procedure> create(@RequestBody Procedure procedure) {
        log.info("updateProcedure {}", procedure);
        return ResponseEntity.ok().body(procedureService.update(procedure));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable (value = "id") Long id){
        log.info("findByName");
        Long deleted = procedureService.delete(id);
        return ResponseEntity.ok().body(deleted);
    }
}
