package com.google.gwt.audio.recorder.showcase.shared.model;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

	private static final long serialVersionUID = -3845438557469615852L;

	private String id;

	private String filename;

	private Date creationDate;

	public Record() {
		this.creationDate = new Date();
	}

	public Record(String filename) {
		super();
		this.filename = filename;
		this.creationDate = new Date();
	}

	public Record(String id, String filename) {
		super();
		this.id = id;
		this.filename = filename;
		this.creationDate = new Date();
	}

	public Record(String id, String filename, Date creationDate) {
		super();
		this.id = id;
		this.filename = filename;
		this.creationDate = creationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", filename=" + filename + ", creationDate=" + creationDate + "]";
	}

}
