<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
<div class=top_menus>
        <h2>発注リスト</h2>

        <c:forEach var="menu" items="${menus}">
            <c:if test="${menu.topDisplay=='one'}">
                <h3>メニュー</h3>

                <hr>

                <div class=top_menu_date><h3>期間</h3>
                    <fmt:parseDate value="${menu.startDate}" pattern='yyyy-MM-dd' var="startDay" type="date" />
                    <fmt:parseDate value="${menu.endDate}" pattern='yyyy-MM-dd' var="endDay" type="date" />
                    <fmt:formatDate value='${startDay}' pattern='yyyy年MM月dd日' />～<fmt:formatDate value='${endDay}' pattern='yyyy年MM月dd日' /><br />
                </div>
                <hr>
                <div class=top_menu>
                <div class=top_menu_morning>
                <h4>朝</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='morning'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>

                <div class=top_menu_noon>
                <h4>昼</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='noon'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>

                <div class=top_menu_evening>
                <h4>夕</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='evening'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>
                </div>
            </c:if>
        </c:forEach><br /><br />

        <hr>

        <h3>発注数</h3>
        <c:forEach var="materialOne" items="${materialOnes}">
            <c:out value="${materialOne.materialMaster.name}" />
            :<c:out value="${materialOne.amount }" />
            <c:out value="${materialOne.materialMaster.unit}" /><br />
        </c:forEach>

        <hr>

        <c:forEach var="menu" items="${menus}">
            <c:if test="${menu.topDisplay=='two'}">

                <h3>メニュー</h3>
            <hr>

                <div class=top_menu_date>
                <h4>期間</h4>
                    <fmt:parseDate value="${menu.startDate}" pattern='yyyy-MM-dd' var="startDay" type="date" />
                    <fmt:parseDate value="${menu.endDate}" pattern='yyyy-MM-dd' var="endDay" type="date" />
                    <fmt:formatDate value='${startDay}' pattern='yyyy年MM月dd日' />～<fmt:formatDate value='${endDay}' pattern='yyyy年MM月dd日' />
                </div>

            <hr>

                <div class=top_menu>
                <div class=top_menu_morning>
                <h4>朝</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='morning'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>


                <div class=top_menu_noon>
                <h4>昼</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='noon'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>

                <div class=top_menu_evening>
                <h4>夕</h4>
                <c:forEach var="cookingSlave" items="${cookingSlaves}">
                    <c:if test="${menu.id==cookingSlave.menu.id && cookingSlave.timeZone=='evening'}">
                        <c:out value="${cookingSlave.cooking.name}" />
                        <c:out value="${cookingSlave.amount}" /><br />
                    </c:if>
                </c:forEach>
                </div>
                </div>
            </c:if>
        </c:forEach><br /><br />

        <hr>

        <h3>発注数</h3>
        <c:forEach var="materialTwo" items="${materialTwos}">
            <c:out value="${materialTwo.materialMaster.name}" />
            :<c:out value="${materialTwo.amount }" />
            <c:out value="${materialTwo.materialMaster.unit}" /><br />
        </c:forEach>
    </div>




    </c:param>

</c:import>