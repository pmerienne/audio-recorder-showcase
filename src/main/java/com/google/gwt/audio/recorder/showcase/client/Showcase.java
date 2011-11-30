package com.google.gwt.audio.recorder.showcase.client;

import com.google.gwt.audio.recorder.showcase.client.resource.ResourceBundle;
import com.google.gwt.audio.recorder.showcase.client.ui.Application;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Showcase implements EntryPoint {

	@Override
	public void onModuleLoad() {
		ResourceBundle.INSTANCE.style().ensureInjected();
		Application application = new Application();
		RootPanel.get().add(application);
	}
}
