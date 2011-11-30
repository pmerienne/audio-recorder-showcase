package com.google.gwt.audio.recorder.showcase.server.servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.audio.recorder.showcase.server.repository.FileRepository;
import com.google.gwt.audio.recorder.showcase.server.repository.RecordRepository;
import com.google.gwt.audio.recorder.showcase.shared.model.Record;

public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = -9085825831036930899L;

	private final static Logger LOGGER = Logger.getLogger(FileServlet.class);

	private FileRepository fileRepository;

	private RecordRepository recordRepository;

	private final static Integer BUFSIZE = 4096;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
			LOGGER.info("Uploading file");
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				String filename = null;
				InputStream file = null;
				// Parse the request
				@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (!item.isFormField()) {
						file = item.getInputStream();
						filename = item.getName();
					}
				}
				if (file == null || filename == null) {
					String errorMsg = "There is a missing parameter in the request.";
					LOGGER.error(errorMsg);
					throw new ServletException(errorMsg);
				} else if (filename.equals("") || filename.equals("null")) {
					String errorMsg = "No filename was specified.";
					LOGGER.error(errorMsg);
					throw new ServletException(errorMsg);
				}
				this.fileRepository.save(file, filename);
				this.recordRepository.save(new Record(filename));
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			LOGGER.error(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get filename
		String filename = req.getParameter("filename");
		if (filename == null) {
			String errorMsg = "There is a missing parameter in the request.";
			LOGGER.error(errorMsg);
			throw new ServletException(errorMsg);
		}

		// Find requested file
		InputStream is = this.fileRepository.get(filename);
		if (is == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, filename + " not found.");
		}

		// Set response headers
		ServletOutputStream os = resp.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filename);
		resp.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		// Write file content
		byte[] buffer = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(is);
		int length = 0;
		while ((in != null) && ((length = in.read(buffer)) != -1)) {
			os.write(buffer, 0, length);
		}

		// Close the closeable
		in.close();
		os.flush();
		os.close();
	}

	/**
	 * La surchage de cette methode permet de charger le context applicatif de
	 * Spring et ainsi de récupérer l'instance du repository.
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
		this.fileRepository = applicationContext.getBean(FileRepository.class);
		this.recordRepository = applicationContext.getBean(RecordRepository.class);
	}
}
