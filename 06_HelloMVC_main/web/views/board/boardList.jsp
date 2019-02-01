<%@page import="com.rclass.board.model.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	List<Board> list = (List<Board>)request.getAttribute("list");
	String pageBar = (String)request.getAttribute("pageBar");
	int cPage = (int)request.getAttribute("cPage");
	int numPerPage = (int)request.getAttribute("numPerPage");

%>

<%@ include file="/views/common/header.jsp" %>

<style>
	section#board-container{width:600px; margin:0 auto; text-align:center;}
	section#board-container h2{margin:10px 0;}
	table#tbl-board{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
	table#tbl-board th, table#tbl-board td {border:1px solid; padding: 5px 0;} 
	input#btn-add{float:right;margin:0 10px 15px;}
	input#btn-delList{float:right;margin:0 0 15px;}
	td.td-title{text-align:left; text-indent: 10px;}
</style>

<section id="board-container">
	<br>
	<h2>자유게시판</h2>
	<% if(loginMember!=null) {%>
		<input type="button" value="글쓰기" id="btn-add" onclick="fn_boardForm()"/>
	<% } %>
	<script>
		function fn_boardForm() {
			location.href="<%= request.getContextPath() %>/board/boardForm";
		}
	</script>
	<table id="tbl-board">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>첨부파일</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<% if(list!=null) { %>
			<% for(Board bo : list) { %>
				<tr>
					<td><%= bo.getBoardNo() %></td>
					<td class="td-title"><a href="<%= request.getContextPath() %>/board/boardView?boardNo=<%= bo.getBoardNo() %>">
						<%= bo.getBoardTitle() %>
					</a></td>
					<td><%= bo.getBoardWriter() %></td>
					<td>
						<% if(bo.getBoardOriginalFilename()!=null) { %>
							<img alt="첨부파일" src="<%= request.getContextPath() %>/images/file.png" width="16px">
						<% } %>
					</td>
					<td><%= bo.getBoardDate() %></td>
					<td><%= bo.getBoardReadcount() %></td>
				</tr>
			<% } %>
		<% } %>
	</table>
	<div id="pageBar">
		<%= pageBar %>
	</div>
</section>

<%@ include file="/views/common/footer.jsp" %>