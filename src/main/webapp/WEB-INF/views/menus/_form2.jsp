<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:out value="${errors}" /><br />
    </div>
</c:if>

<h3>朝</h3>
<c:forEach var="i" begin="1" end="${morning_amount}">
    <label for="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">料理名</label><br />
    <select name="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">
        <c:forEach var="cooking" items="${cookings }">
            <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
        </c:forEach>
    </select><br /><br />
    <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
    <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i].amount}" /><br /><br />
</c:forEach><br /><br />

<h3>昼</h3>
<c:forEach var="i" begin="1" end="${noon_amount}">
    <label for="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">料理名</label><br />
    <select name="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">
        <c:forEach var="cooking" items="${cookings }">
            <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
        </c:forEach>
    </select><br /><br />
    <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
    <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i+morning_amount-1].amount}" /><br /><br />
</c:forEach><br /><br />

<h3>夕</h3>
<c:forEach var="i" begin="1" end="${evening_amount}">
    <label for="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">料理名</label><br />
    <select name="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">
        <c:forEach var="cooking" items="${cookings }">
            <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
        </c:forEach>
    </select><br /><br />
    <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
    <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i+morning_amount+noon_amount-1].amount}" /><br /><br />
</c:forEach>
<br /><br />

<input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
