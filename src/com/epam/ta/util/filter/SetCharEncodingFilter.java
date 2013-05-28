package com.epam.ta.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharEncodingFilter implements Filter {
	private static final String PARAM_NAME_ENCODING = "encoding";

	private static final String DEFAULT_ENCODING = "utf-8";
	
	private String encoding;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain next) throws IOException, ServletException {
		String reqEncoding = request.getCharacterEncoding();
		if (!encoding.equalsIgnoreCase(reqEncoding)) {
			request.setCharacterEncoding(encoding);
		}
		next.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter(PARAM_NAME_ENCODING);
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
		}
	}

	public void destroy() {
		encoding = null;		
	}
}
