/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.tags;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.regex.Pattern;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;

public class IfEmptyMarkTag extends TagSupport {

    private String field;
    private String color = "blue";

    public void setField(String field) {
        this.field = field;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int doStartTag() throws JspException 
    {
        boolean match = false;
                
        try 
        {
            JspWriter out = pageContext.getOut();
            String doublePattern = "([0-9].*)\\.([0-9].*)";
            match = Pattern.matches(doublePattern, field);
            if(match)
            {
                if(Double.valueOf(field)<=0)
                {
                    match = true;
                   
                }
                else 
                {
                 //check if 2 decimal places
                    String[] splitter = (field.trim().split("\\."));
                    splitter[0].length();   // Before Decimal Count
                    int decimalLength = splitter[1].length();  // After Decimal Count
                    if (decimalLength == 2)
                        match = false;
                    else 
                        match = true;
                }
            }
            
            
            if ((field == null || field.length() == 0)|| match) 
            {

                out.print("<font color=" + color + "> *</font>");
                
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return SKIP_BODY;
    }
}