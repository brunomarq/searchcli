package com.github.brunomarq.searchcli.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomarq.searchcli.domain.Entity;
import com.github.brunomarq.searchcli.exception.InvalidFieldNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public abstract class SearchRepository<T> {

    static final Logger log = LoggerFactory.getLogger(SearchRepository.class);

    // Key: the field name
    // Value: Map from the value of the field to the set of instances of the entity
    private Map<String, Map<String, Set<T>>> invertedIndex = new HashMap<>();

    /**
     * Loads the json files into the invertedIndex to allow efficient searches.
     * 
     * @throws IOException if the files are not found or cannot be loaded.
     */
    public void loadDatabases(String filePath, Class<T> typeClass) throws IOException {
        this.invertedIndex = new HashMap<>();
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();

        List<Map<String, Object>> records;
        ObjectMapper mapper = new ObjectMapper();

        log.info("Looking for file {} under resources...", filePath);
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream == null) {
            log.info("Looking for file {} on the filesystem...", filePath);
            inputStream = new FileInputStream(new File(filePath));
        }
        StopWatch watch = new StopWatch();
        watch.start();
        records = mapper.readValue(inputStream, List.class);

        log.info("Building inverted index for {}...", filePath);
        for (Map<String, Object> record : records) {
            buildInvertedIndex(record, typeClass);
        }
        watch.stop();
        log.info("Inverted index for {} created in {} seconds", filePath, watch.getTotalTimeSeconds());
    }

    /**
     * Populates the hashmap representing the inverted index.
     * 
     * @param record    record to be inserted in the inverted index.
     * @param typeClass type of the entity.
     */
    private void buildInvertedIndex(Map<String, Object> record, Class<T> typeClass) {
        ObjectMapper mapper = new ObjectMapper();
        T convertedRecord = mapper.convertValue(record, typeClass);

        for (Entry<String, Object> entry : record.entrySet()) {
            String key = entry.getKey();

            invertedIndex.computeIfAbsent(key, k -> new HashMap<>());
            Map<String, Set<T>> valueMap = invertedIndex.get(key);

            if (entry.getValue() instanceof List) {
                List<String> listValues = (List<String>) entry.getValue();
                for (String value : listValues) {
                    valueMap.computeIfAbsent(value, k -> new HashSet<>());
                    valueMap.get(value).add(convertedRecord);
                }
            } else {
                String value = entry.getValue().toString();
                valueMap.computeIfAbsent(value, k -> new HashSet<>());
                valueMap.get(value).add(convertedRecord);
            }
        }
    }

    /**
     * Looks for record in the inverted index, and, if no record is found, returns
     * and empty collection.
     * 
     * @param field field in the model to be used in the search.
     * @param value value to be searched for in the field.
     * @return List<Entity> result of the search as a collection
     */
    public Set<T> findByFieldValue(String field, String value) throws InvalidFieldNameException {

        Map<String, Set<T>> idInvertedIndex = invertedIndex.get(field);
        if (idInvertedIndex == null) {
            throw new InvalidFieldNameException();
        }
        Set<T> result = idInvertedIndex.get(value);
        if (result == null) {
            result = new HashSet<>();
        }
        return result;
    }

    /**
     * Helper method to look for a singular record by id. Return null if not found.
     * 
     * @param id unique identifier of the record to be looked for.
     * @return T Entity to be returned.
     * @throws InvalidFieldNameException in case an invalid field string is
     *                                   provided.
     */
    public T findById(Long id) throws InvalidFieldNameException {
        T entity = null;
        if (id != null) {
            Set<T> result = this.findByFieldValue(Entity.FIELD_ID, id.toString());
            if (result != null && !result.isEmpty()) {
                entity = result.iterator().next();
            }
        }
        return entity;
    }

}
