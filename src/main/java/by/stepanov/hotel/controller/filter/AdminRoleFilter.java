package by.stepanov.hotel.controller.filter;

import by.stepanov.hotel.entity.Role;
import by.stepanov.hotel.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminRoleFilter",
        urlPatterns = {"/adminCabinet/*", "/roomAdministration/*", "/roomTypeAdministration/*"})
public class AdminRoleFilter implements Filter {

    public static final String USER = "user";
    public static final String LOGIN = "login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User)req.getSession().getAttribute(USER);

        if (user == null || !Role.ADMIN.equals(user.getRole())){
            resp.sendRedirect(LOGIN);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
