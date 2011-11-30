package com.google.gwt.audio.recorder.showcase.client.ui;

import com.google.gwt.audio.recorder.showcase.client.resource.ResourceBundle;
import com.google.gwt.audio.recorder.showcase.shared.model.Record;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;

public class RecordTable extends CellTable<Record> {

	private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private DateTimeFormat df = DateTimeFormat.getFormat(DATE_PATTERN);

	public RecordTable() {
		// Name
		TextColumn<Record> filenameColumn = new TextColumn<Record>() {
			@Override
			public String getValue(Record record) {
				return record.getFilename();
			}
		};
		this.addColumn(filenameColumn, "Filename");

		// Creation date
		TextColumn<Record> creationDateColumn = new TextColumn<Record>() {
			@Override
			public String getValue(Record record) {
				return df.format(record.getCreationDate());
			}
		};
		this.addColumn(creationDateColumn, "Creation date");

		// Download link
		Column<Record, String> downloadColumn = new Column<Record, String>(new DecoratedClickableTextCell()) {
			@Override
			public String getValue(Record record) {
				return "Download";
			}
		};
		downloadColumn.setFieldUpdater(new FieldUpdater<Record, String>() {
			@Override
			public void update(int index, Record record, String value) {
				Window.Location.replace(GWT.getModuleBaseURL() + "file?filename=" + record.getFilename());
			}
		});
		this.addColumn(downloadColumn, "Link");

		// Size
		this.setWidth("100%", true);
		this.setColumnWidth(filenameColumn, "35%");
		this.setColumnWidth(creationDateColumn, "30%");
		this.setColumnWidth(downloadColumn, "30%");
	}

	private class DecoratedClickableTextCell extends ClickableTextCell {

		@Override
		protected void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<div class=\"" + ResourceBundle.INSTANCE.style().link() + "\">");
				sb.append(value);
				sb.appendHtmlConstant("</div>");
			}
		}

	}

}
