package com.google.gwt.audio.recorder.showcase.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gwt.audio.recorder.showcase.server.repository.RecordRepository;
import com.google.gwt.audio.recorder.showcase.shared.model.Record;
import com.google.gwt.audio.recorder.showcase.shared.service.RecordService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("recordService")
public class RecordServiceImpl extends RemoteServiceServlet implements RecordService {

	private static final long serialVersionUID = -7399024830378065759L;

	@Autowired
	private RecordRepository recordRepository;

	@Override
	public List<Record> findAll() {
		return this.recordRepository.findAll();
	}

	public RecordRepository getRecordRepository() {
		return recordRepository;
	}

	public void setFileRecordRepository(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

}
