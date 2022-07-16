<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCoo" value="${ForwardConst.ACT_COO.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNex" value="${ForwardConst.CMD_NEXT.getValue()}"/>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>料理 新規登録ページ</h2>
    <c:if test="${errors != null }">
        <div id="flush_error">
            入力内容にエラーがあります。<br />
            ・<c:out value="${errors}" /><br />
        </div>
    </c:if>

    <form method="POST" action="<c:url value='?action=${actCoo}&command=${commNex}' />" >
        <label for="${AttributeConst.COO_NAME.getValue()}">料理名</label>
        <input type="text" name="${AttributeConst.COO_NAME.getValue()}" value="${cooking.name}" />
        <br /><br />


        <input type="hidden" name="${AttributeConst.COO_ID.getValue()}" value="${cooking.id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        <button type="submit">次の画面へ</button>
    </form>


    <p><a href="<c:url value='?action=${actCoo}&command=${commIdx}' />">一覧に戻る</a></p>
    </c:param>
</c:import>