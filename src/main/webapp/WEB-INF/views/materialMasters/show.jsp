<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMatM" value="${ForwardConst.ACT_MATM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>料理 詳細ページ</h2><br />

    <h3>材料名</h3>
    <c:out value="${materialMaster.name}"/><br /><br />

    <h3>材料の単位</h3>
    <c:out value="${materialMaster.unit}" /><br /><br />

    <c:if test="${sessionScope.login_user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">
        <p>
            <a href="<c:url value='?action=${actMatM}&command=${commEdt}&id=${materialMaster.id}' />">この材料を編集する</a>
        </p>
    </c:if>
    <p>
        <a href="<c:url value='?action=${actMatM}&command=${commIdx}' />">一覧に戻る</a>
    </p>
    </c:param>

</c:import>