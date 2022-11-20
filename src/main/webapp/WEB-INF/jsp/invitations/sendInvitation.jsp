<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="invitations">
    <jsp:body>
        <h2>
            Send an invitation
        </h2>
        <form:form modelAttribute="invitation"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${invitation.id}"/>
            <div class="form-group has-feedback">
                <div class="control-group">
                    <petclinic:selectField label="To" name="recipient" names="${players}" size="3"/>
                </div>
                <petclinic:inputField label="Message" name="message"/>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Send</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
