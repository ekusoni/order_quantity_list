<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>利用者　一覧</h2>
        <table id="user_list">
            <tbody>
                <tr>
                    <th>ユーザー番号</th>
                    <th>氏名</th>
                    <th>判断</th>
                </tr>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${user.code}" /></td>
                        <td><c:out value="${user.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${user.deleteFlag==AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    (削除済み)
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='?action=${actUse}&command=${commShow}&id=${user.id}' />">詳細へ</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
             (全 ${users_count} 件)<br />
           <c:forEach var="i" begin="1" end="${((users_count-1)/ maxRow)+1}" step="1">
               <c:choose>
                   <c:when test="${i==page}">
                       <c:out value="${i}" />&nbsp;
                   </c:when>
                   <c:otherwise>
                        <a href="<c:url value='?action=${actUse}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                   </c:otherwise>
               </c:choose>
           </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actUse}&command=${commNew}' />">新規利用者の登録</a></p>


    </c:param>
</c:import>
