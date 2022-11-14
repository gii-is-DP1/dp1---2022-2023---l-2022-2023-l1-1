<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="achievements/prueb">
    <h2>Progress</h2>
    <table id="progressTable" class="table table-striped">
        <thead>
        <tr>
            <th>Achievement</th>
            <th>Description</th>
            <th>Completed Percentage</th>
            <th>Completed</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${progress}" var="progress">
            <tr>
                <td>
                    <c:out value="${progress.achievement}"/>
                </td>
                <td>
                    <c:out value="${progress.achievement.actualDescription}"/>
                </td>
                <td>
                    <c:out value="${progress.completedPercentage}%"/>
                </td>
                <td>
                    <c:if test="${progress.completed}"> <c:out value="Completed"/> </c:if>
                    <c:if test="${progress.completed == false}"> <c:out value="In progress"/> </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
 
</petclinic:layout>