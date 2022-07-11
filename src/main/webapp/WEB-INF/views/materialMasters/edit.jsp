<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMatM" value="${ForwardConst.ACT_MATM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>材料 編集ページ</h2>
    <form method="POST" action="<c:url value='?action=${actMatM}&command=${commUpd}' />">
        <c:import url="_form.jsp" />
        <button type="submit">変更</button>
    </form>

    <p>
        <a href="<c:url value='?action=${actMatM}&command=${commIdx}' />">一覧に戻る</a>
    </p>


    </c:param>
</c:import>