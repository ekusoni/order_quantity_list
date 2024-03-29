<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst"  %>


<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>利用者　新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${actUse}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
            <button type="submit">登録</button>
        </form>

        <p><a href="<c:url value='?action=${actUse}&command=${commIdx}' />">一覧に戻る</a></p>
    </c:param>
</c:import>


