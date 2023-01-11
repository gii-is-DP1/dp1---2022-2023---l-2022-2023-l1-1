<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">
    <h2>Players</h2>
    <a class="btn btn-default" href="/users/new">Create new player</a>
    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Online</th>
            <th>Playing</th>
            <th>Audit</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <c:out value="${player.user.username}"/>
                </td>
                <td>
                    <c:out value="${player.online}"/>
                </td>
                <td>
                    <c:out value="${player.playing}"/>
                </td>
                <td>
                    <a href="/users/${player.user.username}/audit">
                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                    </a>
                </td>
                <td>
                    <a href="/users/${player.user.username}/edit">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                </td>
                <td>
                    <a href="/users/${player.user.username}/delete">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="display: flex; flex-direction: row; margin: 0 auto; width: fit-content; gap:15px;">
        <c:if test="${paginaActual != 0}">
            <a href="/users?page=${paginaActual-1}", style="width: 50px; height: 50px; text-decoration: none;" >
                <div class="boton-pagina">
                    <
                </div>
            </a>
        </c:if>
        <c:forEach items="${paginas}" var="pageNumber">

            <a href="/users?page=${pageNumber}", style="width: 50px; height: 50px; text-decoration: none;" >
                <c:if test="${paginaActual == pageNumber}">
                    <div class="boton-pagina-actual">
                        ${pageNumber}
                    </div>
                </c:if>

                <c:if test="${paginaActual != pageNumber}">
                    <div class="boton-pagina">
                        ${pageNumber}
                    </div>
                </c:if>
            </a>
        </c:forEach>
        <c:if test="${paginaActual != paginas.size() - 1}">
            <a href="/users?page=${paginaActual+1}", style="width: 50px; height: 50px; text-decoration: none;" >
                <div class="boton-pagina">
                    >
                </div>
            </a>
        </c:if>
    </div>
</petclinic:layout>
