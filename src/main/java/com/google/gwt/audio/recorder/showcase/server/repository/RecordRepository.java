package com.google.gwt.audio.recorder.showcase.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.google.gwt.audio.recorder.showcase.shared.model.Record;

public interface RecordRepository extends MongoRepository<Record, String> {

}
