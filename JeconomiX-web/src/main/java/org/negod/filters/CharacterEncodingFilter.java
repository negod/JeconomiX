/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.filters;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Joakim
 */
@WebFilter
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            String username = request.getParameter("j_username");
            String rememberme = request.getParameter("rememberme");
            chain.doFilter(request, response);
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
