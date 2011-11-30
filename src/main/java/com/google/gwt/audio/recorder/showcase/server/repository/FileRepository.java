package com.google.gwt.audio.recorder.showcase.server.repository;

import java.io.InputStream;
import java.util.List;

public interface FileRepository {

	void save(InputStream is, String filename);

	InputStream get(String filename);
	
	List<String> findAll();

}
