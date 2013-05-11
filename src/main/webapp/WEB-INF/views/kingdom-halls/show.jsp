<%--
    Author     : oliver
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="pageTitle" value="Kingdom Hall" />
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <body>
        <%@ include file="/WEB-INF/views/common/titlebar.jsp" %>
        <div class="container-fluid">
            <h1>Kingdom Hall: ${kingdomHall.name}</h1>
            ${kingdomHall}
        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
        </div>
     <script type="text/javascript" charset="utf-8" src="<c:url value='/javascript/circuits.js' />" ></script>
    </body>
</html>
