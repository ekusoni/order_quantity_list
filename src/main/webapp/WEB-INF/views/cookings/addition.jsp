<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCoo" value="${ForwardConst.ACT_COO.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commInc" value="${ForwardConst.CMD_INCREASE.getValue()}" />
<c:set var="commGain" value="${ForwardConst.CMD_GAIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2>材料 追加ページ</h2>
    <c:if test="${errors !=null}">
        <div id="flush_error">
            入力内容にエラーがあります。<br />
            ・<c:out value="${errors}" /><br />
        </div>
    </c:if>


    <label for="name">料理名</label><br />
    <c:out value="${cooking.name}" />
    <br /><br />
    <form method="POST">
        <c:choose>
            <c:when test="${materialMasters_select !=null }">
                <label for="${AttributeConst.MAT_COL_MATERIALMASTER_ID.getValue()}">材料名</label><br />
                <select name="${AttributeConst.MAT_COL_MATERIALMASTER_ID.getValue()}">
                    <c:forEach var="materialMaster_select" items="${materialMasters_select }">
                        <option value="${materialMaster_select.id}"><c:out value="${materialMaster_select.name}"/></option>
                    </c:forEach>
                </select>
                <button type="submit" formaction="?action=${actCoo}&command=${commInc}">次の画面</button><br /><br />

                <h3>登録済み材料</h3>
                <div class="cooking_material_input">
                <c:forEach var="material" items="${materials}">
                    <c:if test="${cooking.id==material.cooking.id}">
                        <span class="material_show">
                            <c:out value="${material.materialMaster.name}"/>
                            ：<c:out value="${material.amount}"/>
                            <c:out value="${material.materialMaster.unit}" />&nbsp;&nbsp;
                        </span>
                    </c:if>
                </c:forEach>
                </div>
            </c:when>

            <c:when test="${sessionScope.materialMaster !=null}">
                <label for="name">材料名</label><br />
                <c:out value="${materialMaster.name}" />
                <br /><br />

                <label for="${AttributeConst.MAT_AMOUNT.getValue()}">量</label><br />
                <input type="text" name="${AttributeConst.MAT_AMOUNT.getValue()}" />
                <c:out value="${materialMaster.unit}" />
                <button type="submit" formaction="?action=${actCoo}&command=${commInc}">次の画面</button><br /><br />


                <h3>登録済み材料</h3>
                <div class="cooking_material_input">
                    <c:forEach var="material" items="${materials}">
                        <c:if test="${cooking.id==material.cooking.id}">
                            <span class="material_show">
                                <c:out value="${material.materialMaster.name}"/>
                                ：<c:out value="${material.amount}"/>
                                <c:out value="${material.materialMaster.unit}" />&nbsp;&nbsp;
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </c:when>

            <c:when test="${sessionScope.materialTentative != null}">
                <div class="material_tentatives">
                    <c:forEach var="i" begin="0" end="${tentativeLength}" step="1">
                        <span class="material_tentative">
                            <c:out value="${materialTentative[i].materialMaster.name}" />
                            <c:out value="${materialTentative[i].amount}"/>
                            <c:out value="${materialTentative[i].materialMaster.unit}" />
                        </span>&nbsp;&nbsp;
                    </c:forEach>
                </div>

                <button type="submit" formaction="?action=${actCoo}&command=${commInc}">次の材料</button>
                <button type="submit" formaction="?action=${actCoo}&command=${commGain}">材料の確定</button>

            </c:when>
        </c:choose>

       <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />

    </form>


    </c:param>
</c:import>
