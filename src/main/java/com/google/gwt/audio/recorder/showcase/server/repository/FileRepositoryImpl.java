package com.google.gwt.audio.recorder.showcase.server.repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Repository("fileRepository")
public class FileRepositoryImpl implements FileRepository, InitializingBean {

	@Autowired
	private MongoTemplate mongoTemplate;

	private GridFS gridFS;

	@Override
	public void save(InputStream is, String filename) {
		// Delete old files if exists
		List<GridFSDBFile> oldFiles = this.gridFS.find(filename);
		for (GridFSDBFile file : oldFiles) {
			this.gridFS.remove(file);
		}
		// Saving new one
		GridFSInputFile inputFile = this.gridFS.createFile(is, filename);
		inputFile.save();
	}

	@Override
	public InputStream get(String filename) {
		// Delete old files if exists
		GridFSDBFile file = this.gridFS.findOne(filename);
		return file == null ? null : file.getInputStream();
	}

	@Override
	public List<String> findAll() {
		List<String> filenames = new ArrayList<String>();
		Iterator<DBObject> iterator = this.gridFS.getFileList().iterator();
		while (iterator.hasNext()) {
			DBObject dbObject = iterator.next();
			filenames.add(dbObject.get("filename").toString());
		}
		return filenames;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.gridFS = new GridFS(this.mongoTemplate.getDb());
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
