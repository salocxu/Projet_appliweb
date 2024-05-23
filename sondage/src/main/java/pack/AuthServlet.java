
package pack;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import modele.*;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String userType = request.getParameter("user-type");
        
        if (password.equals(confirmPassword)) {
            Personne user = new Personne(email, password);
            // Enregistrement dans la base de données
            // UserDAO.save(newUser);

            response.sendRedirect("success.html");
        } else {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.html");
            dispatcher.forward(request, response);
        }
    }
}
