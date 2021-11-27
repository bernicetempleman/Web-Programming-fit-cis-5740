<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bt" uri="/WEB-INF/tlds/music.tld" %>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />
    
<section>

<h1>Product</h1>
<p><bt:ifEmptyMark color="blue" field=""/> marks required fields</p>
<p><i>${message}</i></p>

<form action="addConfirmed" method="post">
  <label for="prodCode">Code:</label> 
    <input type="text" name="prodCode" value="<c:out value="${product.code}" />"  />
    <bt:ifEmptyMark field="${product.code}"/><br />
  <label for="prodDescription">Description:</label> 
    <input type="text" name="prodDescription" size="50" value="<c:out value="${product.description}" />" />
    <bt:ifEmptyMark field="${product.description}"/><br />
   
<c:choose>
    <c:when test="${product.price=='0.0'}">
             
    <label for="prodPrice">Price:</label> 
    <input type="text" name="prodPrice"  value=""/>
    <bt:ifEmptyMark field="${product.price}"/><br /><br />
         
    </c:when>    
    <c:otherwise>
         
    <label for="prodPrice">Price:</label> 
    <input type="text" name="prodPrice" value="<c:out value="${product.price}" />" />
     <bt:ifEmptyMark color="blue" field="${product.price}"/><br> 
     
    </c:otherwise>
</c:choose>  
    
<br/>

<table>
  <tr>
    <td>
      <form action="addConfirmed" method="post">
        <input type="submit" value="Update Product">
      </form>
    </td>
    <td>
      <form action="prodList" method="post">
        <input type="submit" value="View Product">
      </form>
    </td>
  </tr>
</table>

    
</section>

<jsp:include page="/includes/footer.jsp" />