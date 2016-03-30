package com.sampleAuth.utility;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		Context.getContext().clearSession();

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getRequestURI().substring(18);

		if (path.contains("auth") || path.equals("user/add")) {
			chain.doFilter(req, resp);
		} else {
			String sesionKey = extractSessionKey((HttpServletRequest) req);

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, PUT, DELETE, POST");
			response.setHeader("Access-Control-Allow-Headers", "session");

			if (sesionKey != null) {
				Context.getContext().updateSession(sesionKey);
				chain.doFilter(req, resp);
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private String extractSessionKey(HttpServletRequest req) {
		String header = req.getHeader("session");
		if (header != null) {
			return header;
		}
		return null;
	}

}
