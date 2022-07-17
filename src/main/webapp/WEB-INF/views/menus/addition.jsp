<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commInc" value="${ForwardConst.CMD_INCREASE.getValue()}" />
<c:set var="commGain" value="${ForwardConst.CMD_GAIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>料理 追加ページ</h2>
    <c:if test="${errors !=null }">
        <div id="flush_error">
            入力内容にエラーがあります<br />
            ・<c:out value="${errors}" /><br />
        </div>
    </c:if>

    <form method="POST">
        <c:choose>
            <c:when test="${cookings != null }">
                <label for="${AttributeConst.COS_COL_COOKING_ID.getValue()}">料理名</label><br />
                <select name="${AttributeConst.COS_COL_COOKING_ID.getValue()}">
                    <c:forEach var="cooking" items="${cookings}">
                        <option value="${cooking.id}"><c:out value="${cooking.name}" /></option>
                    </c:forEach>
                </select><br /><br />

                <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                <input class="cookingSlave_amount_input" type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave.amount}"/><br /><br />

                <label for="${AttributeConst.COOKINGSL_TIMEZONE.getValue()}">時間帯</label>&nbsp;
                <select name="${AttributeConst.COOKINGSL_TIMEZONE.getValue()}">
                    <option value="morning"<c:if test="${cookingSlave.timeZone=='morning'}">selected</c:if>>朝</option>
                    <option value="noon"<c:if test="${cookingSlave.timeZone=='noon'}">selected</c:if>>昼</option>
                    <option value="evening"<c:if test="${cookingSlave.timeZone=='evening'}">selected</c:if>>夕</option>
                </select><br /><br />
                <button type="submit" formaction="?action=${actMen}&command=${commInc}">次の画面</button><br /><br />

            </c:when>
            <c:otherwise>
                <div class="cooking_material_input">
                    <c:forEach var="i" begin="0" end="${tentativeLength}" step="1">
                        <span class="material_show">
                            <c:out value="${cookingSlaveTentative[i].cooking.name}" />
                            <c:out value="${cookingSlaveTentative[i].amount}" />&nbsp;&nbsp;
                        </span>
                    </c:forEach><br />
                </div>
                <button type="submit" formaction="?action=${actMen}&command=${commInc}">次の画面</button>&nbsp;
                <button type="submit" formaction="?action=${actMen}&command=${commGain}">料理の確定</button><br />
            </c:otherwise>
        </c:choose>

    <h3>登録済み料理</h3>
        <div class="menu_show">
            <div class="cookingSlave_morning">
                <h3>朝</h3>
                    <c:forEach var="cookingSlave" items="${cookingSlaves}">
                        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='morning'}">
                             <c:out value="${cookingSlave.cooking.name}" />
                             <c:out value="${cookingSlave.amount}" />個<br />
                        </c:if>
                    </c:forEach>
            </div>
            <div class="cookingSlave_noon">
                <h3>昼</h3>
                    <c:forEach var="cookingSlave" items="${cookingSlaves}">
                        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='noon'}">
                            <c:out value="${cookingSlave.cooking.name}" />
                            <c:out value="${cookingSlave.amount}" />個<br />
                        </c:if>
                    </c:forEach>
            </div>
            <div class="cookingSlave_evening">
                <h3>夕</h3>
                    <c:forEach var="cookingSlave" items="${cookingSlaves}">
                        <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='evening'}">
                            <c:out value="${cookingSlave.cooking.name}" />
                            <c:out value="${cookingSlave.amount}" />個<br />
                        </c:if>
                    </c:forEach>
           </div>
        </div>

        <input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
    </form>

    <p>
        <a href="<c:url value='?action=${actMen}&command=${commIdx}' />">一覧に戻る</a>
    </p>
    </c:param>
</c:import>