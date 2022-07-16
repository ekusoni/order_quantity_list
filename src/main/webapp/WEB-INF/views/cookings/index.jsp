<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>


<c:set var="actCoo"  value="${ForwardConst.ACT_COO.getValue()}"/>
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}"/>
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commSea" value="${ForwardConst.CMD_SEARCH.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>料理　一覧</h2>
        <form method="POST" action="<c:url value='?action=${actCoo}&command=${commSea}' />">
            <input type="radio" name="${AttributeConst.COOKING.getValue()}" value="cookingName" required>料理名
            <input type="radio" name="${AttributeConst.COOKING.getValue()}" value="materialName">材料
            <input type="text" name="${AttributeConst.COO_WORD.getValue()}" value="${cooking.name}" />
        <button type="submit">検索</button><br /><br />
        </form>

        <c:if test="${searchCookings !=null}">
            <h3>料理検索結果</h3>

        <table id="cooking_list_search">
            <tbody>
                <tr>
                    <th class="cooking_name">料理</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_action">操作</th>
                </tr>
                <c:forEach var="searchCooking" items="${searchCookings}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="cooking_name"><c:out value="${searchCooking.name}" /></td>
                        <td class="cooking_material">
                        <c:forEach var="material" items="${materials}">
                            <c:if test="${searchCooking.id ==material.cooking.id && searchCount != 1}">
                                <c:out value="${material.materialMaster.name}" />&nbsp;&nbsp;
                                <c:out value="${material.amount}" />
                                <c:out value="${material.materialMaster.unit}"/>
                                <c:set var="searchCount" value="1" />
                            </c:if>
                        </c:forEach>
                        </td>
                        <c:set var="searchCount" value="0"></c:set>
                        <td class="cooking_material">
                        <c:forEach var="material" items="${materials}">
                                <c:if test="${searchCooking.id ==material.cooking.id && searchCount != 3 }">
                                    <c:choose>
                                        <c:when test="${searchCount == 0}"><c:set var="searchCount" value="1" /></c:when>
                                        <c:when test="${searchCount == 2}">
                                            <c:out value="${material.materialMaster.name}" />&nbsp;&nbsp;
                                            <c:out value="${material.amount}" />
                                            <c:out value="${material.materialMaster.unit}"/>
                                            <c:set var="searchCount" value="3" />
                                        </c:when>
                                    </c:choose>
                                </c:if>
                                <c:if test="${searchCooking.id ==material.cooking.id && searchCount == 1 }"><c:set var="searchCount" value="2"></c:set></c:if>
                        </c:forEach>
                        </td>
                        <td class="cooking_action"><a href="<c:url value='?action=${actCoo}&command=${commShow}&id=${searchCooking.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><br /><br />
        </c:if>


        <c:if test="${searchMaterials !=null}">
            <h3>料理検索結果</h3>

        <table id="cooking_list_search">
            <tbody>
                <tr>
                    <th class="cooking_name">料理</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_material">材料</th>
                    <th class="cooking_action">操作</th>
                </tr>
                <c:forEach var="noDuplicationCooking" items="${noDuplicationCookings}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="cooking_name"><c:out value="${noDuplicationCooking.name}" /></td>
                        <c:forEach var="searchMaterial" items="${searchMaterials}">
                            <c:if test="${noDuplicationCooking.id ==searchMaterial.cooking.id && searchCount != 1}">
                                <td class="cooking_material">
                                    <c:out value="${searchMaterial.materialMaster.name}" />&nbsp;&nbsp;
                                    <c:out value="${searchMaterial.amount}" />
                                    <c:out value="${searchMaterial.materialMaster.unit}"/>
                                    <c:set var="searchCount" value="1" />
                                </td>
                            </c:if>
                        </c:forEach>
                        <c:set var="searchCount" value="0"></c:set>
                        <td class="cooking_material">
                        <c:forEach var="searchMaterial" items="${searchMaterials}">
                                <c:if test="${noDuplicationCooking.id ==searchMaterial.cooking.id && searchCount != 3 }">
                                    <c:choose>
                                        <c:when test="${searchCount == 0}"><c:set var="searchCount" value="1" /></c:when>
                                        <c:when test="${searchCount == 2}">
                                            <c:out value="${searchMaterial.materialMaster.name}" />&nbsp;&nbsp;
                                            <c:out value="${searchMaterial.amount}" />
                                            <c:out value="${searchMaterial.materialMaster.unit}"/>
                                            <c:set var="searchCount" value="3" />
                                        </c:when>
                                    </c:choose>
                                </c:if>
                                <c:if test="${noDuplicationCooking.id ==searchMaterial.cooking.id && searchCount == 1 }"><c:set var="searchCount" value="2"></c:set></c:if>
                        </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><br /><br />
        </c:if>

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
        <c:if test="${materialMasters.size() != 0 && sessionScope.login_user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">
            <p><a href="<c:url value='?action=${actCoo}&command=${commNew}' />">新しい料理の登録</a></p>
        </c:if>
    </c:param>
</c:import>