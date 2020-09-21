package raineduc.web2.filters;

import javax.servlet.*;
import java.io.IOException;

public class ForbiddenFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute("message", "Сюда низзя!");
        request.getServletContext().getRequestDispatcher("/templates/error.jsp").forward(request, response);
    }
}
