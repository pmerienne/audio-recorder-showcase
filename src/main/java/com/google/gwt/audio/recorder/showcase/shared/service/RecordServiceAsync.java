package com.google.gwt.audio.recorder.showcase.shared.service;

import java.util.List;

import com.google.gwt.audio.recorder.showcase.shared.model.Record;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecordServiceAsync {

	void findAll(AsyncCallback<List<Record>> callback);

}
