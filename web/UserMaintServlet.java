/*
Bernice Templeman
CIS 5740/ Spring 2016
Music Store Product Maintenance 
*/

package music.admin;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.Pattern;
import music.business.User;
import music.data.UserDB;

public class UserMaintServlet extends HttpServlet 
{

 @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
        // get the products and place into session var
        ServletContext sc = getServletContext();
    
        HttpSession session = request.getSession();
        session.setAttribute("users", UserDB.selectUsers());
        session.setAttribute("error", null);
    
        String action = request.getParameter("action");
        
        if (action == null)
            action = "displayUsers";
        
        String url="";
       
    if (action.equals("displayUsers"))
        { 
            session.setAttribute("users", UserDB.selectUsers());
            url = "/admin/users.jsp";
        }
        
    else if  (action.equals("addUser"))
    {
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("email") != null) 
        {
            session.setAttribute("user", UserDB.selectUser(request.getParameter("email")));
        } 
        else 
        {
            session.setAttribute("user", null);
        }
        
        url = "/admin/addUser.jsp";
    }
        
    else if (action.equals( "addUserConfirmed"))
    {
        // create temp product
        User user = UserDB.selectUser(request.getParameter("email"));
        Boolean isInsert = false;
        if (user == null) 
        {
            isInsert = !isInsert;
            user = new User();
        }
        
        // populate these first, in case of Double error
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        // store the Product object in the session
        session.setAttribute("user", user);
        
        String message = "";
        
        //check if all values complete
        if ((email.isEmpty() || firstName.isEmpty()) || lastName.isEmpty())
        {
            // forward to the view to get missing parameters
            url = "/admin/addUser.jsp";
                                
            if (email.isEmpty())
                    message = "You must enter an email for the user.";
            if (firstName.isEmpty())
                    message = "You must enter a first name for the user.";
            if (lastName.isEmpty())
                    message = "You must enter a last name for the user.";
            request.setAttribute("message", message);
        }
        else //all complete
        {
  
                    if (isInsert) 
                    {
                        UserDB.insert(user);
                    } 
                    else 
                    {
                        UserDB.update(user);
                    }
                    // store the Products in the session
                    session.setAttribute("users", UserDB.selectUsers());
                    url = "/admin/users.jsp";
        }//all complete
    
    }//end if add confirmed
                
    else if (action.equals( "deleteUser"))
    {
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("email") != null) 
        {
            session.setAttribute("user", UserDB.selectUser(request.getParameter("email")));
        } else 
        {
            session.setAttribute("user", null);
        }
        
        url = "/admin/deleteUser.jsp";
    }
        
    else if (action.equals( "deleteUserConfirmed"))
    {
        User delUser = (User) session.getAttribute("user");
        if (delUser != null) 
        {
            UserDB.delete(delUser);
        }
        
        session.setAttribute("users", UserDB.selectUsers());
        url = "/admin/users.jsp";
        
    }

    sc.getRequestDispatcher(url).forward(request, response);
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }  
}