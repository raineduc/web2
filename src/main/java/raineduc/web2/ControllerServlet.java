package raineduc.web2;

import static raineduc.web2.utils.StringUtils.isEmptyOrNull;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
public class ControllerServlet extends HttpServlet {

    private final String ROOT_PATH = "/";
    private final String RESULTS_PATH = "/results";

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
        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/areaCheck");
        requestDispatcher.forward(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        ServletContext context = getServletContext();

        switch (path) {
            case (ROOT_PATH):
                context.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case (RESULTS_PATH):
                context.getRequestDispatcher("/areaCheck").forward(request, response);
        }
    }
}