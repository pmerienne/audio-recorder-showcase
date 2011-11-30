package com.google.gwt.audio.recorder.showcase.client.ui;

import com.google.gwt.audio.recorder.client.Recorder;
import com.google.gwt.audio.recorder.client.RecorderControl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RecordPopup extends DialogBox {

	private static RecordPopupUiBinder uiBinder = GWT.create(RecordPopupUiBinder.class);

	interface RecordPopupUiBinder extends UiBinder<Widget, RecordPopup> {
	}

	@UiField
	TextBox filename;

	@UiField
	RecorderControl recorderControl;

	private PopupCallback popupCallback;

	private Recorder recorder;

	public RecordPopup() {
		// UI
		super(false);
		setWidget(uiBinder.createAndBindUi(this));
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.setModal(true);

		// Recorder
		this.recorder = new Recorder("images/upload.png");
		this.recorder.setUploadURL(GWT.getModuleBaseURL() + "file");
		this.recorderControl.setRecorder(recorder);

		this.clean();
	}

	@UiHandler("filename")
	protected void onFilenameChange(ValueChangeEvent<String> event) {
		this.recorder.setFilename(event.getValue());
		this.recorderControl.getPlaybackButton().setEnabled(false);
	}

	@UiHandler("close")
	protected void onClose(ClickEvent event) {
		this.hide();
	}

	public void show(PopupCallback callback) {
		super.show();
		this.popupCallback = callback;
	}

	@Override
	public void hide() {
		super.hide();
		if (this.popupCallback != null) {
			this.popupCallback.onClose();
		}
	}

	public interface PopupCallback {
		void onClose();
	}

	public void clean() {
		this.recorder.setFilename("filename.wav");
		this.filename.setValue("filename.wav");
	}

}
