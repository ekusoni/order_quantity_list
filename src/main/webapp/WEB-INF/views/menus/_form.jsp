<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<fmt:parseDate value="${menu.startDate}" pattern="yyyy-MM-dd" var="startDay" type="date" />
<label for="${AttributeCost.MENU_DATE_START.getValue()}">開始日</label><br />
<input type="date" name="${AttributeConst.MENU_DATE_START.getValue()}" value="<fmt:formatDate value='${startDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<fmt:parseDate value="${menu.endDate}" pattern="yyyy-MM-dd" var="endDay" type="date" />
<label for="${AttributeConst.MENU_DATE_END.getValue()}">終了日</label><br />
<input type="date" name="${AttributeConst.MENU_DATE_END.getValue()}" value="<fmt:formatDate value='${endDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="${AttributeConst.MENU_MORNING_AMOUNT.getValue()}">朝の料理の種類</label><br />
<input type="text" name="${AttributeConst.MENU_MORNING_AMOUNT.getValue()}" value="${morning_amount}"/>
<br /><br />

<label for="${AttirbuteConst.MENU_NOON_AMOUNT.getVlue()}">昼の料理の種類</label><br />
<input type="text" name="${AttributeConst.MENU_NOON_AMOUNT.getValue()}" value="${noon_amount}"/>
<br /><br />

<label for="${AttributeConst.MENU_EVENING_AMOUNT.getValue()}">夕の料理の種類</label><br />
<input type="text" name="${AttributeConst.MENU_EVENING_AMOUNT.getValue()}" value="${evening_amount}"/>
<br /><br />

<input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />


