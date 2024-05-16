package pack;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.JsonObject;

@WebServlet("/Serv")
public class AuthServlet extends HttpServlet {

    @EJB
    private AuthBean authBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        JsonObject jsonResponse = new JsonObject();

        try {
            if ("ajoutpersonne".equals(op)) {
                System.out.println("Authenticating user: " + mail);
                boolean success = authBean.authenticate(mail, mdp);
                jsonResponse.addProperty("success", success);
            } else if ("nouvelutilisateur".equals(op)) {
                System.out.println("Adding new user: " + mail);
                authBean.addUser(mail, mdp);
                jsonResponse.addProperty("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred: " + e.getMessage());
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
