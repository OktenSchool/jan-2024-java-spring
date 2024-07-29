package org.okten.demo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.demo.properties.Office;
import org.okten.demo.properties.ReferenceDataProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reference-data")
@RequiredArgsConstructor
public class ReferenceDataController {

    @Value("${reference-data.categories}")
    private List<String> categories;

    private final ReferenceDataProperties referenceDataProperties;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/offices")
    public ResponseEntity<List<Office>> getOffices(@RequestParam(required = false) String city) {
        if (city != null) {
            return ResponseEntity
                    .ok(referenceDataProperties
                            .getOffices()
                            .stream()
                            .filter(office -> Objects.equals(office.getAddress().getCity(), city))
                            .toList());
        } else {
            return ResponseEntity.ok(referenceDataProperties.getOffices());
        }
    }

    @GetMapping("/offices/{name}")
    public ResponseEntity<Office> getOffice(@PathVariable String name) {
        return ResponseEntity
                .of(referenceDataProperties
                        .getOffices()
                        .stream()
                        .filter(office -> Objects.equals(office.getName(), name))
                        .findFirst());
    }
}
