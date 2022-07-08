<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>


<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actMatM" value="${ForwardConst.ACT_MATM.getValue()}" />
<c:set var="actCoo" value="${ForwardConst.ACT_COO.getValue()}" />
<c:set var="actAut" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="発注量リスト" /></title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1><a href="<c:url value='/?action=${actTop}&command=${commIdx}' />">発注量リスト</a></h1>&nbsp;&nbsp;&nbsp;&nbsp;
                <c:if test="${sessionScope.login_user !=null}">
                    <c:if test="${sessionScope.login_user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">
                        <a href="<c:url value='?action=${actUse}&command=${commIdx}' />">利用者一覧</a>
                        <a href="<c:url value='?action=${actMatM}&command=${commIdx}' />">材料</a>
                        <a href="<c:url value='?action=${actCoo}&command=${commIdx}' />">料理</a>
                        <a href="<c:url value='?action=${actMen}&command=${commIdx}' />">メニュー</a>
                    </c:if>
                </c:if>
            </div>
            <c:if test="${sessionScope.login_user != null}">
                <div id="user_name">
                    <c:out value="${sessionScope.login_user.name}" />
                    &nbsp;さん&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='?action=${actAut}&command=${commOut}' />">ログアウト</a>

                </div>
            </c:if>
        </div>
        <div id="content">${param.content}</div>
    </div>
</body>
</html>