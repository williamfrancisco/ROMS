<%--
    Document   : kingdomhallShow
    Created on : 30-Jun-2012, 13:19:18
    Author     : oliver.elder.esq
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="pageTitle" value="Kingdom Halls" />
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <body>
        <%@ include file="/WEB-INF/views/common/titlebar.jsp" %>
        <div class="container-fluid">
            <h1>Kingdom Halls</h1>
            <div class="entity-list-results">
                <table class="table table-bordered table-striped table-hover" id="kingdom-hall-list">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Town</th>
                            <th>Post Code</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${kingdomHalls}" var="kingdomHall">
                            <tr>
                                <td>${kingdomHall.kingdomHallId}</td>
                                <td>${kingdomHall.name}</td>
                                <td>${kingdomHall.town}</td>
                                <td>${kingdomHall.postCode}</td>
                                <td><a class="btn btn-success" href="<c:url value="/kingdom-halls/${kingdomHall.kingdomHallId}" />">View</a>&nbsp;
                                    <a class="btn btn-warning" href="<c:url value="/kingdom-halls/${kingdomHall.kingdomHallId}/edit" />">Edit</a>&nbsp;
                                    <a class="btn btn-danger" href="delete">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th><input type="text" name="search_name" value="Search names" class="search_init" /></th>
                            <th><input type="text" name="search_town" value="Search towns" class="search_init" /></th>
                            <th><input type="text" name="search_postcode" value="Search post code" class="search_init" /></th>
                            <th></th>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <div class="entity-list-add-new">
                <a class="btn btn-primary" href="<c:url value="/kingdom-halls/new" />">Create new kingdom hall</a>
            </div>
            <p>&nbsp;</p>
            <ul class="breadcrumb">
              <li><a href="<c:url value="/" />">ROMS</a> <span class="divider">/</span></li>
              <li class="active">Kingdom Halls</li>
            </ul>
        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
        </div>
        <script type="text/javascript" charset="utf8" src="<c:url value='/javascript/kingdom-halls.js' />" ></script>
        </body>
</html>