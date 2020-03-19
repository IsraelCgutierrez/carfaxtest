package com.carfax.test.carfaxTest;

import com.carfax.test.carfaxTest.entities.Record;
import com.carfax.test.carfaxTest.entities.Records;
import com.carfax.test.carfaxTest.exceptions.ConnectionException;
import com.carfax.test.carfaxTest.services.RecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarfaxTestApplicationTests {

    @Autowired
    private RecordsService recordsService;

    /**
     * Test api for development
     *
     * @throws ConnectionException conection exception
     */
    @Test
    void testApi() throws ConnectionException {
        Records inputData = recordsService.getRecordsData("VSSZZZ6JZ9R056308");
        assertTrue(inputData.getRecords().size() > 0);
    }

    /**
     * Tests the process for flagging Rollbacks
     */
    @Test
    void testProcess() {
        List<Record> inputRecords = generateInputs();
        List<Record> result = recordsService.processRecords(inputRecords);
        checkResults(result);
    }

    /**
     * checks only 5 and 7 are Rollback
     *
     * @param result processed list
     */
    private void checkResults(List<Record> result) {
        for (int i = 0; i < result.size(); i++) {
            if (i == 5 || i == 7) {
                assertTrue(result.get(i).getOdometer_rollback());
            } else {
                assertNull(result.get(i).getOdometer_rollback());
            }
        }
    }

    /**
     * @return generates a list of records
     * flagging 5 and 7 as Rollback
     */
    private List<Record> generateInputs() {
        List<Record> generated = new ArrayList<>();
        int increment = 0;
        for (int i = 0; i < 10; i++) {
            Record a = new Record();
            a.setVin(randString(17));
            a.setData_provider_id(i);
            a.setDate(i + "");
            if (i == 5 || i == 7) {
                a.setOdometer_reading(0);
                increment = 0;
            } else {
                increment++;
                a.setOdometer_reading(increment);
            }
            a.setService_details(Collections.emptyList());
        }
        return generated;
    }


    /**
     * creates random strings
     *
     * @param size string size
     * @return string
     */
    private String randString(int size) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
