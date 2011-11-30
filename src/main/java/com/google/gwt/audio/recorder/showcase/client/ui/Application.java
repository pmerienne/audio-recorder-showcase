package com.google.gwt.audio.recorder.showcase.client.ui;

import java.util.List;

import com.google.gwt.audio.recorder.showcase.client.ui.RecordPopup.PopupCallback;
import com.google.gwt.audio.recorder.showcase.shared.model.Record;
import com.google.gwt.audio.recorder.showcase.shared.service.RecordService;
import com.google.gwt.audio.recorder.showcase.shared.service.RecordServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Application extends Composite {

	private static ApplicationUiBinder uiBinder = GWT.create(ApplicationUiBinder.class);

	interface ApplicationUiBinder extends UiBinder<Widget, Application> {
	}

	private final static RecordServiceAsync recordService = GWT.create(RecordService.class);

	@UiField
	RecordTable recordTable;

	private RecordPopup recordPopup;

	public Application() {
		initWidget(uiBinder.createAndBindUi(this));
		this.recordPopup = new RecordPopup();

		loadRecordList();
	}

	private void loadRecordList() {
		recordService.findAll(new AsyncCallback<List<Record>>() {
			@Override
			public void onSuccess(List<Record> result) {
				recordTable.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Records cannot be loaded : " + caught.getMessage());
			}
		});
	}

	@UiHandler("newRecordButton")
	protected void onNewRecordClicked(ClickEvent event) {
		this.recordPopup.center();
		this.recordPopup.show(new PopupCallback() {
			@Override
			public void onClose() {
				loadRecordList();
				recordPopup.clean();
			}
		});
	}
}
