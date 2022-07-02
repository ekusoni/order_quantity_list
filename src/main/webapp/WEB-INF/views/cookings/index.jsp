<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="actCoo"  value="${ForwardConst.ACT_COO.getValue()}"/>
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}"/>
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>料理　一覧</h2>
        <table id="cooking_list">
            <tbody>
                <tr>
                    <th class="cooking_name">料理</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_action">操作</th>
                </tr>
                <c:forEach var="cooking" items="${cookings}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="cooking_name"><c:out value="${cooking.name}" /></td>
                        <td class="cooking_material">
                        <c:forEach var="material" items="${materials}">
                            <c:if test="${cooking.id ==material.cooking.id && count != 1}">
                                <c:out value="${material.materialMaster.name}" />&nbsp;&nbsp;
                                <c:out value="${material.amount}" />
                                <c:out value="${material.materialMaster.unit}"/>
                                <c:set var="count" value="1" />
                            </c:if>
                        </c:forEach>
                        </td>
                        <c:set var="count" value="0"></c:set>
                        <td class="cooking_material">
                        <c:forEach var="material" items="${materials}">
                                <c:if test="${cooking.id ==material.cooking.id && count != 3 }">
                                    <c:choose>
                                        <c:when test="${count == 0}"><c:set var="count" value="1" /></c:when>
                                        <c:when test="${count == 2}">
                                            <c:out value="${material.materialMaster.name}" />&nbsp;&nbsp;
                                            <c:out value="${material.amount}" />
                                            <c:out value="${material.materialMaster.unit}"/>
                                            <c:set var="count" value="3" />
                                        </c:when>
                                    </c:choose>
                                </c:if>
                                <c:if test="${cooking.id ==material.cooking.id && count == 1 }"><c:set var="count" value="2"></c:set></c:if>
                        </c:forEach>
                        </td>
                        <td class="cooking_action"><a href="<c:url value='?action=${actCoo}&command=${commShow}&id=${cooking.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <div id="pagination">
            (全 ${cookings_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((cookings_count -1 ) / maxRow) +1}" step="1">
                <c:choose>
                    <c:when test="${i==page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                    <a href="<c:url value='?action=${actCoo}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actCoo}&command=${commNew}' />">新しい料理の登録</a></p>
    </c:param>
</c:import>