package com.carfax.test.carfaxTest.services;

import com.carfax.test.carfaxTest.config.ApiRecords;
import com.carfax.test.carfaxTest.entities.Record;
import com.carfax.test.carfaxTest.entities.Records;
import com.carfax.test.carfaxTest.exceptions.ConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordsService {

    private final ApiRecords apiRecords;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RecordsService(ApiRecords apiRecords) {
        this.apiRecords = apiRecords;
    }

    /**
     * @param ivn Vehicle identification number.
     * @return Records object updated
     * @throws ConnectionException API exception
     */
    public Records getRecords(String ivn) throws ConnectionException {
        Records records = getRecordsData(ivn);
        records.setRecords(processRecords(records.getRecords()));
        return records;
    }

    /**
     * @param records Process records according to business logic
     * @return list of updated records
     */
    public List<Record> processRecords(@NotNull List<Record> records) {
        List<Record> inputRecords = records.stream().sorted(Comparator.comparing(Record::getDate)).collect(Collectors.toList());
        for (int i = 1; i < inputRecords.size(); i++) {
            Record prev = inputRecords.get(i - 1);
            Record record = inputRecords.get(i);
            if (record.getOdometer_reading() < prev.getOdometer_reading()) {
                record.setOdometer_rollback(true);
            }
        }
        return inputRecords;
    }

    /**
     * @param ivn Vehicle identification number.
     * @return consume api and validate response
     * @throws ConnectionException API exception
     */
    public Records getRecordsData(String ivn) throws ConnectionException {
        return httpGetArray(apiRecords.getRecords(), ivn, null).getBody();
    }

    /**
     * @param endpoint api endpoint
     * @param resource api resource
     * @param headers  api headers for auth
     * @return Records object
     * @throws ConnectionException API exception
     */
    private ResponseEntity<Records> httpGetArray(String endpoint, String resource,
                                                 HttpHeaders headers) throws ConnectionException {
        String url = (endpoint + resource);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, Records.class);
        } catch (Exception e) {
            throw new ConnectionException(url + " " + e.getMessage(), e);
        }
    }
}
