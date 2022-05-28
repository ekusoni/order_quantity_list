<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>


<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConsr.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">



        <h2>id:${user.id}の利用者情報 詳細ページ</h2>


        <table>
            <tbody>
                <tr>
                    <th>ユーザー番号</th>
                    <td><c:out value="${user.code}" /></td>
                </tr>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${user.name}" /></td>
                </tr>
                <tr>
                    <th>権限</th>
                    <td><c:choose>
                            <c:when test="${user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">作成者</c:when>
                            <c:otherwise>閲覧者</c:otherwise>
                        </c:choose></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actUse}&command=${commEdit}&id=${user.id}' />">この利用者情報を編集する</a>
        </p>

        <p>
            <a href="<c:url value='?action=${actUse}&command=${commIdx}' />">一覧に戻る</a>

    </c:param>

</c:import>
