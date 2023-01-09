<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="statistics">
    <h2>Statistics</h2>
    <table class="table table-striped">
        <tr>
            <th>Games played</th>
            <td><b><c:out value="${statistics.get(0)} " /></b></td>
        </tr>
        <tr>
            <th>Winned games</th>
            <td><c:out value="${statistics.get(1)}" /></td>
        </tr>
        <tr>
            <th>Lost games</th>
            <td><c:out value="${statistics.get(2)}" /></td>
        </tr>
        <tr>
            <th>Victory %</th>
            <td><c:out value="${statistics.get(3)} %" /></td>
        </tr>
        <tr>
            <th>Loss %</th>
            <td><c:out value="${statistics.get(4)} %" /></td>
        </tr>
        <tr>
            <th>Total time played</th>
            <td><c:out value="${statistics.get(5)} minutes" /></td>
        </tr>
        <tr>
            <th>Global time played</th>
            <td><c:out value="${statistics.get(6)} minutes" /></td>
        </tr>
        <tr>
            <th>Average time played</th>
            <td><c:out value="${statistics.get(7)} minutes" /></td>
        </tr>
        <tr>
            <th>Global average time played</th>
            <td><c:out value="${statistics.get(8)} minutes" /></td>
        </tr>
        <tr>
            <th>Maximum time played</th>
            <td><c:out value="${statistics.get(9)} minutes" /></td>
        </tr>
        <tr>
            <th>Global maximum time played</th>
            <td><c:out value="${statistics.get(10)} minutes" /></td>
        </tr>
        <tr>
            <th>Minimum time played</th>
            <td><c:out value="${statistics.get(11)} minutes" /></td>
        </tr>
        <tr>
            <th>Global minimum time played</th>
            <td><c:out value="${statistics.get(12)} minutes" /></td>
        </tr>
    
        
        
    
    </table>

</petclinic:layout>