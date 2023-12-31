package com.example.demo.filter;
import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JWTFilter extends GenericFilterBean
{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

HttpServletRequest httpReq = (HttpServletRequest) request;

HttpServletResponse httpRes = (HttpServletResponse) response;

String authHeader = httpReq.getHeader("authorization");

		System.out.println("recieved token :"+authHeader);

if(authHeader==null || !authHeader.startsWith("Bearer"))
{
	throw new ServletException("Missing or invalid authentication header");
}
	


	String jwtToken = authHeader.substring(7);


	Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();

		httpReq.setAttribute("loginId", claims);
		chain.doFilter(request, response);
	/*if(claims.getId().equals("user")) {

		httpReq.setAttribute("loginId", claims);
		chain.doFilter(request, response);
	}
	else
		throw new ServletException("Missing or invalid authentication header");
*/
	}

}



