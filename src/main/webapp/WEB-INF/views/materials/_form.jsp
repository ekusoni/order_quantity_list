<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<label for="${AttributeConst.MAT_NAME.getValue()}">材料名</label><br />
<input type="text" name="${AttributeConst.MAT_NAME.getValue()}" value="${material.name}" />
<br /><br />


<label for="${AttributeConst.MAT_UNIT.getValue()}">単位</label><br />
<input type="text" name="${AttributeConst.MAT_UNIT.getValue()}" value="${material.unit}" />
<br /><br />


<input type="hidden" name="${AttributeConst.MAT_ID.getValue()}" value="${material.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue() }" value="${_token}" />
