<%@page import="com.rclass.notice.model.vo.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	List<Notice> list = (List)request.getAttribute("list");
	String pageBar = (String)request.getAttribute("pageBar");
	int cPage = (int)request.getAttribute("cPage");
	int numPerPage = (int)request.getAttribute("numPerPage");
%>

<%@ include file="/views/common/header.jsp" %>

<style>
	section#notice-container{width:600px; margin:0 auto; text-align:center;}
	section#notice-container h2{margin:10px 0;}
	table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
	table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0; text-align:center;} 
</style>

<section id="notice-container">
	<br>
	<h2>공지사항</h2>
	<table id="tbl-notice">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>첨부파일</th>
			<th>작성일</th>
		</tr>
		<% for(Notice n:list) { %>
			<tr>
				<td><%= n.getNoticeNo() %></td>
				<td><%= n.getNoticeTitle() %></td>
				<td><%= n.getNoticeWriter() %></td>
				<td>
				<% if(n.getFilepath()!=null) { %>
					<img alt='첨부파일' src="<%=request.getContextPath() %>/images/file.png" width="16px">
				<% } %>
				</td>
				<td><%= n.getNoticeDate() %></td>
			</tr>
		<% } %>
	</table>
	<br>
	<%= pageBar %>
</section>

<%@ include file="/views/common/footer.jsp" %>