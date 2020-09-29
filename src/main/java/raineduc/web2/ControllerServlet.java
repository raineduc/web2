package raineduc.web2;

import raineduc.web2.beans.ResultsBean;

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
import javax.servlet.http.HttpSession;

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
    private final String TABLE_PATH = "/resultsTable";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xCoord = req.getParameter("x-coord");
        String yCoord = req.getParameter("y-coord");
        String[] radiusValues = req.getParameterValues("r-param");

        if (isEmptyOrNull(xCoord) && isEmptyOrNull(yCoord) && (radiusValues == null || radiusValues.length == 0)) {
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
            case (TABLE_PATH):
                processRequest(request, response);
                HttpSession session = request.getSession();
                ResultsBean resultsBean = (ResultsBean) session.getAttribute("resultsBean");
                session.setAttribute("results", resultsBean.getResults());
                context.getRequestDispatcher("/templates/table.jsp").forward(request, response);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("resultsBean") == null) {
            ResultsBean resultsBean = new ResultsBean();
            session.setAttribute("resultsBean", resultsBean);
        }
    }
}