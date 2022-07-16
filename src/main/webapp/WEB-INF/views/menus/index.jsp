<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMen" value="${ForwardConst.ACT_MENU.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush !=null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>メニュー 一覧</h2>
        <table id="menu_list">
            <tbody>
                <tr>
                    <th class="menu_startDate">開始日</th>
                    <th class="menu_endDate">終了日</th>
                    <th class="menu_cooking">料理</th>
                    <th class="menu_cooking">料理</th>
                    <th class="menu_topPage">トップページ(最大2つまで)</th>
                    <th class="menu_action">操作</th>
                </tr>
                <c:forEach var="menu" items="${menus}" varStatus="status">
                    <fmt:parseDate value="${menu.startDate}" pattern='yyyy-MM-dd' var="startDay" type="date"/>
                    <fmt:parseDate value="${menu.endDate}" pattern='yyyy-MM-dd' var="endDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="menu_statDate"><fmt:formatDate value='${startDay}' pattern='yyyy年MM月dd日'/></td>
                        <td class="menu_endDate"><fmt:formatDate value='${endDay}' pattern='yyyy年MM月dd日'/></td>
                        <td class="menu_cooking">
                        <c:forEach var="cookingSlave" items="${cookingSlaves}">
                            <c:if test="${menu.id ==cookingSlave.menu.id && count != 1}">
                                <c:out value="${cookingSlave.cooking.name}" />&nbsp;&nbsp;
                                <c:out value="${cookingSlave.amount}" />個
                                <c:set var="count" value="1" />
                            </c:if>
                        </c:forEach>
                        </td>
                        <c:set var="count" value="0" />
                        <td class="menu_cooking">
                        <c:forEach var="cookingSlave" items="${cookingSlaves}">
                            <c:if test="${menu.id ==cookingSlave.menu.id && count != 3}">
                                <c:choose>
                                    <c:when test="${count == 0}"><c:set var="count" value="1" /></c:when>
                                    <c:when test="${count == 2 }">
                                        <c:out value="${cookingSlave.cooking.name}" />&nbsp;&nbsp;
                                        <c:out value="${cookingSlave.amount }" />個
                                        <c:set var="count" value="3" />
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${menu.id ==cookingSlave.menu.id && count == 1}"><c:set var="count" value="2" /></c:if>
                        </c:forEach>
                        </td>
                        <td class="menu_topPage">
                            <c:choose>
                                <c:when test="${menu.topDisplay=='one'}">
                                    上に登録済み
                                </c:when>
                                <c:when test="${menu.topDisplay=='two'}">
                                    下に登録済み
                                </c:when>
                                <c:otherwise>未登録</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="menu_action"><a href="<c:url value='?action=${actMen}&command=${commShow}&id=${menu.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <div id="pagination">
            (全　${menus_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((menus_count -1 ) /  maxRow) +  1}" step="1">
                <c:choose>
                    <c:when test="${i==page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                    <a href="<c:url value='?action=${actMen}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${cookings.size() != 0 && sessionScope.login_user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">
            <p><a href="<c:url value='?action=${actMen}&command=${commNew}' />">新規メニューの登録</a></p>
        </c:if>
    </c:param>
</c:import>