<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNex" value="${ForwardConst.CMD_NEXT.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>メニュー 新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${actMen}&command=${commNex}' />">
            <c:import url="_form.jsp" />
            <button type="submit">次の画面</button>
        </form>

        <p><a href="<c:url value='?action=${actMen}&command=${commIdx}' />">一覧に戻る</a></p>
    </c:param>
</c:import>
