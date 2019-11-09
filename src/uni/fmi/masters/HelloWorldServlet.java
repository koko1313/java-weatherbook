package uni.fmi.masters;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uni.fmi.masters.beans.CommentBean;
import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repository.JPACommentRepository;
import uni.fmi.masters.repository.JPAUserRepository;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JPAUserRepository repo = new JPAUserRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String router = request.getParameter("router");
		
		switch(router) {
			case "login":
				goToLogin(request, response);
				break;
			case "register":
				registerUser(request, response);
				break;
			case "insertComment":
				insertComment(request, response);
				break;
		}
		
	}
	
	private void insertComment(HttpServletRequest request, HttpServletResponse response) {
		String city = request.getParameter("city");
		String temp = request.getParameter("temp");
		String comment = request.getParameter("comment");
		String user = request.getParameter("user");
		
		JPAUserRepository jpaUser = new JPAUserRepository();
		
		CommentBean commentEntity = new CommentBean();
		commentEntity.setCity(city);
		commentEntity.setTemp(Double.parseDouble(temp));
		commentEntity.setComment(comment);
		commentEntity.setUser(jpaUser.findById(Integer.parseInt(user)));
		
		JPACommentRepository jpaComment = new JPACommentRepository();
		
		jpaComment.insert(commentEntity);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response) {
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		
		if(password.equals(repeatPassword)) {
			UserBean user = new UserBean(username, email, password);
			
			if(repo.createUser(user)) {
				request.setAttribute("user", user);
				redirect("profile.jsp", request, response);
			} else {
				request.setAttribute("message", "Registration unsuccessfull!");
				redirect("error.jsp", request, response);
			}
		} else {
			request.setAttribute("message", "Password missmatch!");
			redirect("error.jsp", request, response);
		}		
		
	}

	private void redirect(String page, HttpServletRequest request, HttpServletResponse response) {
		
		RequestDispatcher rd = request.getRequestDispatcher(page);
		
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}

	private void goToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserBean user = repo.loginUser(username, password);
		
		if(user != null) {
			request.setAttribute("user", user);
			
			JPACommentRepository repo = new JPACommentRepository();
			List<CommentBean> comments = repo.getAllCommentsByUser(user);
			
			request.setAttribute("comments", comments);
			
			redirect("home.jsp", request, response);
		} else {			
			request.setAttribute("message", "Wrong password information!");
			redirect("error.jsp", request, response);
		}
	}

}
