package raineduc.web2;

import static raineduc.web2.utils.StringUtils.isEmptyOrNull;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * A simple servlet taking advantage of features added in 3.0.
 * </p>
 *
 * @author Pete Muir
 *
 */
@SuppressWarnings("serial")
@WebServlet("/hit")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xCoord = req.getParameter("x-coord");
        String yCoord = req.getParameter("y-coord");
        String radius = req.getParameter("r-param");

        if (isEmptyOrNull(xCoord) && isEmptyOrNull(yCoord) && isEmptyOrNull(radius)) {
//            ServletContext context = getServletContext();
//            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/");
//            requestDispatcher.forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        ServletContext context = getServletContext();
        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/results");
        requestDispatcher.forward(req, resp);
    }

}