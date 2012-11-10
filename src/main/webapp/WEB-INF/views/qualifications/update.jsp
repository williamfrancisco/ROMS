<%-- 
    Document   : update
    Created on : 23-Aug-2012, 20:23:17
    Author     : Tina
--%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="pageTitle" value="Create/Edit Qualification" />
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <body>
      <div class="container"> 
        <%@ include file="/WEB-INF/views/common/titlebar.jsp" %>
        <h1>Qualification</h1>
        <c:url var="formAction" value="/qualifications" />
        <form:form commandName="qualification" method="post" action="${formAction}">
            <form:label path="qualification">Name <form:input path="qualification" /></form:label>
                <input type="submit" />
        </form:form>

        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
      </div>
    </body>
</html>
