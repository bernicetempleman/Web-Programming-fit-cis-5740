<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<section>

<h1>Products</h1>

<table class="border">
  <tr>
    <th>Code</th>
    <th>Description</th>
    <th class="right">Price</th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
  </tr>
  <c:choose>
    <c:when test="${products != '[]'}">
      <c:forEach var="product" items="${products}">
      <tr>
        <td><c:out value="${product.code}" /></td>
        <td><c:out value="${product.description}" /></td>
        <td class="right"><c:out value="${product.priceCurrencyFormat}" /></td>
        <td><a href="addProduct?productCode=${product.code}">Edit</a></td>
        <td><a href="deleteProduct?productCode=${product.code}">Delete</a></td>
      </tr>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="5">There are no products found.</td>
      </tr>
    </c:otherwise>
  </c:choose>
</table><br />
    
<form action="addProduct" method="post">
  <input type="submit" value="Add Product" />
</form>

</section>
        
<jsp:include page="/includes/footer.jsp" />