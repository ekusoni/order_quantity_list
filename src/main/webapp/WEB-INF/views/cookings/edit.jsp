<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="actCoo" value="${ForwardConst.ACT_COO.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDes" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <c:if test="${errors != null}">
            <div id="flush_error">
            入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" /><br />
            </c:forEach>

        </div>
        </c:if>
        <h2>料理 編集ページ</h2>

        <form method="POST">

        <label for="${AttributeConst.COO_NAME.getValue()}">料理</label><br />
        <input type="text" name="${AttributeConst.COO_NAME.getValue()}" value="${cooking.name}" />
        <br /><br />



        <div class="cooking_material_input">
            <c:forEach var="material" items="${materials}">
                <c:if test="${cooking.id==material.cooking.id}">
                    <span class="material_show">
                        <label for="${AttributeConst.MAT_AMOUNT.getValue()}"><c:out value="${material.materialMaster.name}">の量</c:out></label>
                        <input class="material_input" type="text" name="${AttributeConst.MAT_AMOUNT.getValue()}" value="${material.amount}" />
                        <c:out value="${material.materialMaster.unit }"></c:out>
                        <button class="material_input" type="submit" formaction="?action=${actCoo}&command=${commDes}&id=${material.id}">削除</button>
                     </span>&nbsp;&nbsp;
                </c:if>
            </c:forEach>
        </div><br /><br />

        <input type="hidden" name="${AttributeConst.COO_ID.getValue()}" value="${cooking.id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />


        <button type="submit" formaction="?action=${actCoo}&command=${commUpd}">再登録</button>

        </form>





    </c:param>
</c:import>
