<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>メニュー 新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${actMen}&command=${commCrt}' />">
            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:out value="${errors}" /><br />
                </div>
            </c:if>
            <div class="menu_next">
                <div class="menu_next_morning">
                <h3>朝</h3>
                <c:forEach var="i" begin="1" end="${morning_amount}">
                    <div class="menu_next_cooking">
                        <div class="menu_next_cooking_name">
                            <label for="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">料理名</label><br />
                            <select name="${AttributeConst.COS_COL_MORNING_COOKING_ID.getValue()}">
                                <c:forEach var="cooking" items="${cookings }">
                                    <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
                                </c:forEach>
                            </select>&nbsp;<br />
                        </div>
                    <div class="menu_next_amount">
                        <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                        <input class="cookingSlave_amount_input"type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i].amount}" /><br /><br />
                    </div>
                  </div>
                </c:forEach>
                </div>

                <div class="menu_next_noon">
                <h3>昼</h3>
                <c:forEach var="i" begin="1" end="${noon_amount}">
                    <div class="menu_next_cooking">
                        <div class="menu_next_cooking_name">
                            <label for="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">料理名</label><br />
                            <select name="${AttributeConst.COS_COL_NOON_COOKING_ID.getValue()}">
                                <c:forEach var="cooking" items="${cookings }">
                                    <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
                                </c:forEach>
                            </select>&nbsp;<br />
                        </div>
                        <div class="menu_next_cooking_amount">
                            <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                            <input class="cookingSlave_amount_input"type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i+morning_amount-1].amount}" /><br /><br />
                        </div>
                    </div>
                </c:forEach>
                 </div>

                <div class="menu_next_evening">
                <h3>夕</h3>
                <c:forEach var="i" begin="1" end="${evening_amount}">
                    <div class="menu_next_cooking">
                        <div class="menu_next_cooking_name">
                            <label for="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">料理名</label><br />
                            <select name="${AttributeConst.COS_COL_EVENING_COOKING_ID.getValue()}">
                                <c:forEach var="cooking" items="${cookings }">
                                    <option value="${cooking.id}"><c:out value="${cooking.name}"></c:out></option>
                                </c:forEach>
                            </select>&nbsp;<br />
                        </div>
                        <div class="menu_next_cooking_amount">
                            <label for="${AttributeConst.COOKINGSL_AMOUNT.getValue()}">個数</label><br />
                            <input class="cookingSlave_amount_input"type="text" name="${AttributeConst.COOKINGSL_AMOUNT.getValue()}" value="${cookingSlave[i+morning_amount+noon_amount-1].amount}" /><br /><br />
                        </div>
                        </div>
                </c:forEach>&nbsp;<br />
                </div>

                <input type="hidden" name="${AttributeConst.MEN_ID.getValue()}" value="${menu.id}" />
                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />


             </div>

             <button type="submit">登録</button>
        </form>

        <p><a href="<c:url value='?action=${actMen}&command=${commIdx}' />">一覧に戻る</a></p>
    </c:param>
</c:import>

