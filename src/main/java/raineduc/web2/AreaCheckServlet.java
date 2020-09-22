package raineduc.web2;

import raineduc.web2.beans.Result;
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

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xCoord = request.getParameter("x-coord");
        String yCoord = request.getParameter("y-coord");
        String[] radiusValues = request.getParameterValues("r-param");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Enumeration<String> names = request.getParameterNames();
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();

        processRequest(request, response);

        try {
            HttpSession session = request.getSession();
            ResultsBean resultsBean = (ResultsBean) session.getAttribute("resultsBean");
            for (String radius: radiusValues) {
                resultsBean.addResult(xCoord, yCoord, radius);
            }
            session.setAttribute("results", resultsBean.getResults());
            getServletContext().getRequestDispatcher("/templates/table.jsp").forward(request, response);
        } catch (ResultInputException e) {
            response.setStatus(400);
            printWriter.println(e.getMessage());
            printWriter.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        HttpSession session = request.getSession();
        ResultsBean resultsBean = (ResultsBean) session.getAttribute("resultsBean");
        session.setAttribute("results", resultsBean.getResults());
        getServletContext().getRequestDispatcher("/results.jsp").forward(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("resultsBean") == null) {
            ResultsBean resultsBean = new ResultsBean();
            session.setAttribute("resultsBean", resultsBean);
        }
    }
}
