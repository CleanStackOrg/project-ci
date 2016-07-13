package org.cleanstack.ci.deploy.tools;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Throwables;

public class HTTPClient {

	public void downloadTo(String urlString, String dest) {
		try {
			// CHECK
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			checkState(404 != code, "remote file unreachable for: " + url);
			// DOWNLOAD
			// REDIRECTION
			URLConnection con = url.openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			URL url3 = con.getURL();
			is.close();
			// DOWNLOAD
			FileUtils.copyURLToFile(url3, new File(dest));
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}
}
