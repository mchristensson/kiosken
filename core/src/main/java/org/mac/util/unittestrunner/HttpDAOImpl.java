package org.mac.util.unittestrunner;

import org.springframework.stereotype.Component;

@Component
public class HttpDAOImpl implements HttpDAO {

	private String url;

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

}
