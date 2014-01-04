<%--
    Author     : oliver
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="pageTitle" value="Kingdom Hall: ${kingdomHall.name}" />
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <body>
        <%@ include file="/WEB-INF/views/common/titlebar.jsp" %>
            <h1>Kingdom Hall #${kingdomHall.kingdomHallId}: ${kingdomHall.name}</h1>
            <hr />
            <dl class="dl-horizontal">
                <dt>Address:</dt>
                <dd>
                    <address>
                        <c:if test="${!empty kingdomHall.street}">${kingdomHall.street}<br /></c:if>
                        <c:if test="${!empty kingdomHall.town}">${kingdomHall.town}<br /></c:if>
                        <c:if test="${!empty kingdomHall.county}">${kingdomHall.county}<br /></c:if>
                        <c:if test="${!empty kingdomHall.postcode}">${kingdomHall.postcode}<br /></c:if>
                    </address>
                </dd>
                <dt>Ownership Type:</dt>
                <dd>
                    <c:choose>
                        <c:when test="${!empty ownershipValue}">
                            ${ownershipValue}
                        </c:when>
                        <c:otherwise>Ownership Type is unknown</c:otherwise>
                    </c:choose>
                </dd>
                <dt>Used By:</dt>
                <dd>
                    <c:choose>
                        <c:when test="${!empty congregations}">
                            <c:forEach items="${congregations}" var="congregation" varStatus="loop">
                                <a href="<c:url value="${congregation.uri}" />">
                                ${congregation.name}
                                </a>
                                ${!loop.last ? ', ' : ''}
                            </c:forEach>
                        </c:when>
                        <c:otherwise><i>No congregations meet at this Hall</i></c:otherwise>
                    </c:choose>
                </dd>
                <dt>Title Holder:</dt>
                <dd>
                    <c:choose>
                        <c:when test="${!empty kingdomHall.titleHoldingCongregation}">
                            <a href="<c:url value="${kingdomHall.titleHoldingCongregation.uri}" />">
                            ${kingdomHall.titleHoldingCongregation.name}
                            </a>
                        </c:when>
                        <c:otherwise>Unknown</c:otherwise>
                    </c:choose>
                </dd>
            </dl>

            <br />
            <ol class="breadcrumb">
                <li><a href="<c:url value="/" />">Edifice</a></li>
                <li><a href="<c:url value="/kingdom-halls" />">Kingdom Halls</a></li>
                <li class="active">#${kingdomHall.kingdomHallId}: ${kingdomHall.name}</li>
            </ol>

        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
     <script type="text/javascript" src="<c:url value='/javascript/kingdom-halls.js' />" ></script>
    </body>
</html>
