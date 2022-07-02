<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCoo" value="${ForwardConst.ACT_COO.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue() }" />
<c:set var="commAdd" value="${ForwardConst.CMD_ADDITION.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>料理 詳細ページ</h2><br />

    <h3>料理</h3>
    <c:out value="${cooking.name}"></c:out><br /><br />

    <h3>材料</h3>
    <div class="cooking_material">
    <c:forEach var="material" items="${materials}">
        <c:if test="${cooking.id==material.cooking.id}">
        <span class="material_show">
            <c:out value="${material.materialMaster.name}"></c:out>
            :<c:out value="${material.amount}"></c:out>
            <c:out value="${material.materialMaster.unit}"></c:out>&nbsp;&nbsp;
        </span>
        </c:if>
    </c:forEach>
    </div>


    <p>
        <a href="<c:url value='?action=${actCoo}&command=${commEdt}&id=${cooking.id}' />">この料理を編集する</a>
    </p>
    <p>
        <a href="<c:url value='?action=${actCoo}&command=${commAdd}&id=${cooking.id}' />">材料を追加する</a>
    </p>
    <p>
        <a href="<c:url value='?action=${actCoo}&command=${commIdx}' />">一覧に戻る</a>
    </p>








    </c:param>
</c:import>