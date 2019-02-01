<%@page import="com.rclass.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Board bo = (Board)request.getAttribute("bo");
%>

<%@ include file="/views/common/header.jsp" %>

<style>
    section#board-container{width:600px; margin:0 auto; margin-top: 20px; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>

<section id="board-container">
	<h2>자유게시판 상세내용</h2>
		<table id="tbl-board">
			<tr>
				<th>제목</th>
				<td><%= bo.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= bo.getBoardWriter() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<% if(bo.getBoardOriginalFilename()!=null) { %>
						<img alt="첨부파일" src="<%= request.getContextPath() %>/images/file.png" width="16px">
					<% } %>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><%= bo.getBoardContent() %></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="수정하기" onclick="fn_updateNotice()"/>
					<input type="button" value="삭제하기" onclick="fn_deleteNotice()"/>
				</td>
			</tr>
		</table>
	</form>
</section>

<%@ include file="/views/common/footer.jsp" %>