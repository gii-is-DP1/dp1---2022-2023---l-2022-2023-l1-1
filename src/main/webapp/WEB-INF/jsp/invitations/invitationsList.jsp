<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="invitations">
    <h2>Invitations</h2>
    <a href="/invitations/send"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Send invitation</a>
    <table id="invitationsTable" class="table table-striped">
        <thead>
        <tr>
            <th>From</th>
            <th>Message</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${invitations}" var="invitation">
            <tr>
                <td>
                    <c:out value="${invitation.sender}"/>
                </td>
                <td>
                    <c:out value="${invitation.message}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
