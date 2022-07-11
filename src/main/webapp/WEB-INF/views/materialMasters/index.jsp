<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMatM" value="${ForwardConst.ACT_MATM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commSea" value="${ForwardConst.CMD_SEARCH.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>材料 一覧</h2>
        <form method="POST" action="<c:url value='?action=${actMatM}&command=${commSea}' />">
           <input type="radio" name="${AttributeConst.MATERIALM.getValue()}" value="name" required>材料名
           <input type="radio" name="${AttributeConst.MATERIALM.getValue()}" value="unit">単位
           <input type="text" name="${AttributeConst.MATM_WORD.getValue()}" value="${materialMaster.name}" />
        <button type="submit">検索</button><br /><br />
        </form>

        <c:if test="${searchMaterialMasters != null}">
            <h3>材料検索結果</h3>

        <table id="materialMaster_list_search">
            <tbody>
                <tr>
                    <th class="materialMaster_name">材料名</th>
                    <th class="materialMaster_unit">単位</th>
                    <th class="materialMaster_action">操作</th>
                </tr>
                <c:forEach var="searchMaterialMaster" items="${searchMaterialMasters}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="materialMaster_name"><c:out value="${searchMaterialMaster.name}" /></td>
                        <td class="materialMaster_unit"><c:out value="${searchMaterialMaster.unit}" /></td>
                        <td class="materialMaster_action"><a href="<c:url value='?action=${actMatM}&command=${commShow}&id=${searchMaterialMaster.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><br /><br />
        </c:if>

        <table id="material_list">
            <tbody>
                <tr>
                    <th class="materialMaster_name">材料名</th>
                    <th class="materialMaster_unit">単位</th>
                    <th class="materialMaster_action">操作</th>
                </tr>
                <c:forEach var="materialMaster" items="${materialMasters}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="materialMaster_name"><c:out value="${materialMaster.name}" /></td>
                        <td class="materialMaster_unit"><c:out value="${materialMaster.unit}" /></td>
                        <td class="materialMaster_action"><a href="<c:url value='?action=${actMatM}&command=${commShow}&id=${materialMaster.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全${materialMasters_count} 件) <br />
           <c:forEach var="i" begin="1" end="${((materialsMasters_count - 1) / maxRow) + 1}" step="1">
               <c:choose>
                   <c:when test="${i==page}">
                       <c:out value="${i}" />&nbsp;
                   </c:when>
                   <c:otherwise>
                       <a href="<c:url value='action=${actMatM}&xcommand=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                   </c:otherwise>
               </c:choose>
           </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actMatM}&command=${commNew}' />">材料の新規登録</a></p>
    </c:param>
</c:import>
