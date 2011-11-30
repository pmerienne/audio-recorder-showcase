package com.google.gwt.audio.recorder.showcase.shared.service;

import java.util.List;

import com.google.gwt.audio.recorder.showcase.shared.model.Record;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recordService.rpc")
public interface RecordService extends RemoteService {

	List<Record> findAll();
}
