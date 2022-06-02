<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMatM" value="${ForwardConst.ACT_MATM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>材料 一覧</h2>
        <table id="material_list">
            <tbody>
                <tr>
                    <th class="materialMaster_name">材料名</th>
                    <th class="materialMaster_unit">単位</th>
                </tr>
                <c:forEach var="materialMaster" items="${materialMasters}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="materialMaster_name"><c:out value="${materialMaster.name}" /></td>
                        <td class="materialMaster_unit"><c:out value="${materialMaster.unit}" /></td>
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
