/*
Bernice Templeman
CIS 5740/ Spring 2016
Music Store Product Maintenance 
*/
package music.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import music.business.Product;
import music.data.ProductDB;

public class ProductController extends HttpServlet 
{
 @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {  
    HttpSession session = request.getSession();
    session.setAttribute("error", null);
    
    String requestURI = request.getRequestURI();
    String url = "";
    
    if (requestURI.endsWith("/addProduct"))
    {
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("productCode") != null) 
        {
            session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
        } 
        else 
        {
            session.setAttribute("product", null);
        }

        url = "/admin/prodAdd.jsp";
    }
    else if (requestURI.endsWith("/addConfirmed"))
    {
        // create temp product
        Product product = ProductDB.selectByCode(request.getParameter("prodCode"));
        Boolean isInsert = false;
        if (product == null) 
        {
            isInsert = !isInsert;
            product = new Product();
        }
        
        // get parameters from the request
        String prodCode = request.getParameter("prodCode");
        String prodDescription = request.getParameter("prodDescription");
        String prodPrice = request.getParameter("prodPrice");
        
        product.setCode(prodCode);
        product.setDescription(prodDescription);
        
        double prodPriceD = 0.0;
        String message = "";
        boolean match = false;
        
        if(!prodPrice.isEmpty())
        {
            //check if price is valid double value          
            String doublePattern = "([0-9].*)\\.([0-9].*)";
            match = Pattern.matches(doublePattern, (request.getParameter("prodPrice")));
            if (match)
            {
                try
                {
                    prodPriceD = Double.valueOf(request.getParameter("prodPrice"));

                }
                catch(Exception e)
                {
                    product.setPrice(prodPriceD);
                    session.setAttribute("product", product);
                    match = false;
                    message = "You must enter a valid price for the product. Example: 1.00";
                    request.setAttribute("message", message);
                    url = "/admin/prodAdd.jsp";
  
                }
            }
        }// prod price is not empty
        else //prod price is empty
        {
                // store the Product object in the session
                match = false;
                message = "You must enter a valid price for the product. Example: 1.00";
                request.setAttribute("message", message);
                url = "/admin/prodAdd.jsp";             
        }
          
        product.setPrice(prodPriceD);
        session.setAttribute("product", product);
        
        if (match)
        {      
        //check if all values complete
        if ((prodCode.isEmpty() || prodDescription.isEmpty()) || prodPrice.isEmpty())
        {
            // forward to the view to get missing parameters
            url = "/admin/prodAdd.jsp";
                                
            if (prodCode.isEmpty())
                message = "You must enter a code for the product.";
            else if (prodDescription.isEmpty())
                message = "You must enter a description for the product.";
            else if (prodPrice.isEmpty())
            
                message = "You must enter a price for the product.";
            request.setAttribute("message", message);
            
        }
        else //all complete
        {
            //check if price is valid double value          
            String doublePattern = "([0-9].*)\\.([0-9].*)";
            match = Pattern.matches(doublePattern, (request.getParameter("prodPrice")));
           
            if(match) //check if 2 decimal places
            {
                String[] splitter = (request.getParameter("prodPrice")).split("\\.");
                splitter[0].length();   // Before Decimal Count
                int decimalLength = splitter[1].length();  // After Decimal Count

                if (decimalLength == 2)
                {     
                    // decide between update and insert
                    if (!isInsert) 
                    {
                        ProductDB.update(product);
                    } 
                    else
                    {
                        ProductDB.insert(product);
                    }          
                    session.setAttribute("products", ProductDB.selectAll());
                    url = "/admin/prodList.jsp";
                }
                else  
                {
                    message = "You must enter a valid price for the product. Example: 1.00";
                    request.setAttribute("message", message);
                    url = "/admin/prodAdd.jsp"; 
                }
                
            }//if match
            else
            {
                message = "You must enter a valid price for the product. Example: 1.00";
                request.setAttribute("message", message);
                url = "/admin/prodAdd.jsp"; 
            }
        }//end if all complete
        
        }//end if match
        else
        {
                message = "You must enter a valid price for the product. Example: 1.00";
                request.setAttribute("message", message);
                url = "/admin/prodAdd.jsp"; 
         }
    }//end add confirmed
        
    else if (requestURI.endsWith("/deleteProduct"))
    {
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("productCode") != null) 
        {
            session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
        } 
        else 
        {
            session.setAttribute("product", null);
        }

        url = "/admin/prodDelete.jsp";
    }//end delete product
    
    else if (requestURI.endsWith("/deleteConfirmed"))
    {  
        Product delProduct = (Product) session.getAttribute("product");
        if (delProduct != null) 
        {
            ProductDB.delete(delProduct);
        }

        session.setAttribute("products", ProductDB.selectAll());
        url = "/admin/prodList.jsp";
    }//end delete confirmed
    
    else if (requestURI.endsWith("/prodList"))
    {
        // if none of those, show all products
        session.setAttribute("products", ProductDB.selectAll());
        url = "/admin/prodList.jsp";
    }//end display products
        
    if (url.equals("") != true)
    {
        System.out.println("url=" + url);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    else
    {
        // display 404 error page
        response.sendError(404);
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }  
}
