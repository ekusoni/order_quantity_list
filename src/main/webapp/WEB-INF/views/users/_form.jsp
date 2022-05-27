<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>
<label for="${AttributeConst.USE_CODE.getValue()}">ユーザー番号</label><br />
<input type="text" name="${AttributeConst.USE_CODE.getValue()}" value="${user.code}" />
<br /><br />


<label for="${AttributeConst.USE_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.USE_NAME.getValue()}" value="${user.name}"/>
<br /><br />


<label for="${AttributeConst.USE_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.USE_PASS.getValue()}">
<br /><br />


<label for="${AttributeConst.USE_AUTHOR_FLAG.getValue()}">権限</label><br />
<select name="${AttributeConst.USE_AUTHOR_FLAG.getValue()}">
    <option value="${AttributeConst.ROLE_AUTHOR.getIntegerValue()}"<c:if test="${user.authorFlag==AttributeConst.ROLE_AUTHOR.getIntegerValue()}">selected</c:if>>作成者</option>
    <option value="${AttributeCondt.ROLE_VIEWER.getIntegerValue()}"<c:if test="${user.authorFlag==AttributeConst.ROLE_VIEWER.getIntegerValue()}">selected</c:if>>閲覧者</option>
</select>
<br /><br />
<input type="hidden" name="${AttributeConst.USE_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConse.TOKEN.getValue()}" value="${_token}" />


