<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commAdd" value="${ForwardConst.CMD_ADDITION.getValue()}" />
<c:set var="commTop" value="${ForwardConst.CMD_TOP.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>メニュー 詳細ページ</h2>

    <h3>期間</h3>
    <fmt:parseDate value="${menu.startDate}" pattern='yyyy-MM-dd' var="startDay" type="date" />
    <fmt:parseDate value="${menu.endDate}" pattern='yyyy-MM-dd' var="endDay" type="date" />
    <fmt:formatDate value='${startDay}' pattern='yyyy年MM月dd日' />～<fmt:formatDate value='${endDay}' pattern='yyyy年MM月dd日' /><br />


    <div class="menu_show">
    <div class="cookingSlave_morning">
    <h3>朝</h3>
    <c:forEach var="cookingSlave" items="${cookingSlaves}">
        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='morning'}">
            <c:out value="${cookingSlave.cooking.name}" />
            <c:out value="${cookingSlave.amount }" />個<br />
        </c:if>
    </c:forEach>
    </div>

    <div class="cookingSlave_noon">
    <h3>昼</h3>
    <c:forEach var="cookingSlave" items="${cookingSlaves}">
        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='noon'}">
            <c:out value="${cookingSlave.cooking.name}" />
            <c:out value="${cookingSlave.amount }" />個<br />
        </c:if>
    </c:forEach>
    </div>

    <div class="cookingSlave_evening">
    <h3>夕</h3>
        <c:forEach var="cookingSlave" items="${cookingSlaves}">
        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='evening'}">
            <c:out value="${cookingSlave.cooking.name}" />
            <c:out value="${cookingSlave.amount }" />個<br />
        </c:if>
    </c:forEach>
    </div>
    </div>

    <c:if test="${menu.topDisplay=='no'}">
        <form method="POST" action="<c:url value='?action=${actMen}&command=${commTop}' />">
            <input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
            <button type="submit">トップメニューに登録</button>
        </form>
    </c:if>

    <c:if test="${sessionScope.login_user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">
        <p>
            <a href="<c:url value='?action=${actMen}&command=${commEdt}&id=${menu.id}' />">このメニューを編集する</a>
        </p>
        <p>
            <a href="<c:url value='?action=${actMen}&command=${commAdd}&id=${menu.id}' />">料理を追加する</a>
        </p>
    </c:if>

    <p>
        <a href="<c:url value='?action=${actMen}&command=${commIdx}' />">一覧に戻る</a>
    </p>

    </c:param>
</c:import>
