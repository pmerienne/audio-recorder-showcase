package com.google.gwt.audio.recorder.showcase.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ResourceBundle extends ClientBundle {

	public static ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

	@Source("close-icon.png")
	ImageResource closeIcon();

	Style style();

}
