package raineduc.web2;

import raineduc.web2.beans.ResultInputException;
import raineduc.web2.beans.ResultsBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/results")
@MultipartConfig
public class AreaCheckServlet extends HttpServlet {
    @EJB
    private ResultsBean resultsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xCoord = request.getParameter("x-coord");
        String yCoord = request.getParameter("y-coord");
        String radius = request.getParameter("r-param");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Enumeration<String> names = request.getParameterNames();
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();

        try {
            resultsBean.addResult(xCoord, yCoord, radius);
            HttpSession session = request.getSession();
            session.setAttribute("results", resultsBean.getResults());
            getServletContext().getRequestDispatcher("/templates/table.jsp").forward(request, response);
        } catch (ResultInputException e) {
            response.setStatus(400);
            printWriter.println(e.getMessage());
            printWriter.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("results", resultsBean.getResults());
        getServletContext().getRequestDispatcher("/templates/table.jsp").forward(request, response);
    }
}
