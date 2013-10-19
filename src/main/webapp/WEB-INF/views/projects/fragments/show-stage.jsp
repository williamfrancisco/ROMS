<%--
    Show an individual project stage
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="stage-${stage.id}" class="panel panel-default">
    <div class="panel-heading">
        <div class="project-stage-type-name col-sm-4"><h4>${stage.type.name}: ${stage.type.description}</h4></div>
        <div class="project-stage-status col-sm-2"><h4>${stage.status}</h4></div>
        <div class="drag-move pull-right">
            <span class="glyphicon glyphicon-align-justify"></span>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="panel-body">
        <div class="col-sm-2">
            <div>
                <c:choose>
                    <c:when test="${!empty stage.createdTime}">
                        <fmt:formatDate value="${stage.createdTime}" pattern="yyyy-MM-dd" />
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-sm-2">
            <div>
                <c:choose>
                    <c:when test="${!empty stage.startedTime}">
                        <fmt:formatDate value="${stage.startedTime}" pattern="yyyy-MM-dd" />
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-sm-2">
            <div>
                <c:choose>
                    <c:when test="${!empty stage.completedTime}">
                        <fmt:formatDate value="${stage.completedTime}" pattern="yyyy-MM-dd" />
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="project-counts">
                <div class="project-count a-project-count" title="" data-toggle="tooltip"
                     data-original-title="Not started: ${stage.createdActivityCount} of ${stage.totalActivityCount}">
                    <span class="badge badge-important">${stage.createdActivityCount}</span>
                </div>
                <div class="project-count a-project-count" title="" data-toggle="tooltip"
                     data-original-title="In progress: ${stage.startedActivityCount} of ${stage.totalActivityCount}">
                    <span class="badge badge-warning">${stage.startedActivityCount}</span>
                </div>
                <div class="project-count a-project-count" title="" data-toggle="tooltip"
                     data-original-title="Completed: ${stage.completedActivityCount} of ${stage.totalActivityCount}">
                    <span class="badge badge-success">${stage.completedActivityCount}</span>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-edifice pull-right">
            <a class="accordion-toggle pull-right" data-toggle="collapse" data-parent="#accordion-stage-${stage.id}" href="#collapse-stage-${stage.id}">
                <span class="glyphicon glyphicon-plus"></span>
            </a>
        </button>
        <div class="clearfix"></div>
        <div class="accordion" id="accordion-stage-${stage.id}">
            <div class="accordion-group">
                <div id="collapse-stage-${stage.id}" class="accordion-body collapse in">
                    <div class="accordion-inner">
                        <br>
                        <c:choose>
                            <c:when test="${!empty stage.activities}">
                                <h3>Activities</h3>
                                <div id="project-stage-${stage.id}-activities">
                                    <c:forEach var="activity" items="${stage.activities}">
                                        <%@ include file="show-stage-activity.jsp" %>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>No activities defined</c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
