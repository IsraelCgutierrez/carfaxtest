package com.carfax.test.carfaxTest.resources;

import com.carfax.test.carfaxTest.exceptions.ConnectionException;
import com.carfax.test.carfaxTest.services.RecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(RecordsResource.PATH)
public class RecordsResource {
    public static final String PATH = "/getRecords"; // public for integration tests
    public static final String IVN = "/{ivn}"; // public for integration tests

    private final RecordsService recordsService;

    @Inject
    public RecordsResource(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    /**
     * @param ivn Vehicle identification number.
     * @return Vehicle records
     * @throws ConnectionException API exception
     */
    @GetMapping(IVN)
    public ResponseEntity getRecordsByIvn(@PathVariable("ivn") String ivn) throws ConnectionException {
        return ResponseEntity.ok(recordsService.getRecords(ivn));
    }
}
