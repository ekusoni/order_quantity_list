<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDes" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <c:if test="${errors != null}">
            <div id="flush_error">
                入力内容にエラーがあります。<br />
                <c:out value="${errors}" /><br />
            </div>
        </c:if>



        <h2>メニュー編集ページ</h2>

        <form method="POST">

        <h3>朝</h3>
            <div class="cookingSlave_morning">
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${cookingSlave.timeZone=='morning'}">
                        <label for="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">料理名</label><br />
                        <select name="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">
                            <c:forEach var="cooking" items="${cookings}">
                                <option value="${cooking.id}"<c:if test="${cookingSlave.cooking.id==cooking.id }">selected</c:if>><c:out value="${cooking.name}"/></option>
                            </c:forEach>
                        </select><br />
                        <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                        <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave.amount}" />
                        <button type="submit" formaction="?action=${actMen}&command=${commDes}&id=${cookingSlave.id}">削除</button><br /><br />
                    </c:if>
                </c:forEach>
            </div>

        <h3>昼</h3>
            <div class="cookingSlave_noon">
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${cookingSlave.timeZone=='noon'}">
                        <label for="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">料理名</label><br />
                        <select name="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">
                            <c:forEach var="cooking" items="${cookings}">
                                <option value="${cooking.id}"<c:if test="${cookingSlave.cooking.id==cooking.id }">selected</c:if>><c:out value="${cooking.name}"/></option>
                            </c:forEach>
                        </select><br />
                        <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                        <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave.amount}" />
                        <button type="submit" formaction="?action=${actMen}&command=${commDes}&id=${cookingSlave.id}">削除</button><br /><br />
                    </c:if>
                </c:forEach>
            </div>

        <h3>夕</h3>
            <div class="cookingSlave_evening">
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${cookingSlave.timeZone=='evening'}">
                        <label for="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">料理名</label><br />
                        <select name="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">
                            <c:forEach var="cooking" items="${cookings}">
                                <option value="${cooking.id}"<c:if test="${cookingSlave.cooking.id==cooking.id }">selected</c:if>><c:out value="${cooking.name}"/></option>
                            </c:forEach>
                        </select><br />
                        <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                        <input type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave.amount}" />
                        <button type="submit" formaction="?action=${actMen}&command=${commDes}&id=${cookingSlave.id}">削除</button><br /><br />
                    </c:if>
                </c:forEach>
            </div>

        <input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />

        <button type="submit" formaction="?action=${actMen}&command=${commUpd}">再登録</button>

        </form>

    </c:param>
</c:import>