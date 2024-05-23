
package pack;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import modele.*;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private AuthBean authBean;

    @EJB
    private UserBean userBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String type = request.getParameter("user-type");
        boolean userType;
        
        // convertir userType en boolean
        if (type.equals("professeur")) {
            userType = true;
        } else {
            userType = false;
        }
        
        if (password.equals(confirmPassword)) {
            userBean.createUser(email, password, userType);
            // Enregistrement dans la base de données
            

            response.sendRedirect("success.html");
        } else {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.html");
            dispatcher.forward(request, response);
        }
    }
}
