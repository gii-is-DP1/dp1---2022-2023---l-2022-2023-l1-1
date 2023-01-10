<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="ranking">
    <h2>Ranking</h2>
    <table class="table table-striped">

        <c:forEach items="${rankingMap}" var="entry">
            <tr>
                <th>Rank </th>
                <td><c:out value="Player: ${entry.key.user.username}" /></td>
                <td><c:out value="Victories: ${entry.value}" /></td>
            </tr>
        </c:forEach>
    </table>

</petclinic:layout>