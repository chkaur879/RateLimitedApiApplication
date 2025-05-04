package org.chkaur99;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicAPIController {

    @GetMapping("/public-data")
    public ResponseEntity<List<String>> getPublicData() {
        List<String> data = List.of("Item1", "Item2", "Item3");
        return ResponseEntity.ok(data);
    }
}
