package fr.lteconsulting.training.javaee.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter( urlPatterns = "/*" )
public class CorsFilter implements Filter
{
	private static final String ALLOWED_DOMAINS_REGEXP = ".*localhost:4200";

	public void init( FilterConfig config ) throws ServletException
	{
	}

	public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain ) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		String origin = req.getHeader( "Origin" );

		if( origin != null && origin.matches( ALLOWED_DOMAINS_REGEXP ) )
			resp.addHeader( "Access-Control-Allow-Origin", origin );

		if( filterChain != null )
			filterChain.doFilter( req, resp );
	}

	public void destroy()
	{
	}
}