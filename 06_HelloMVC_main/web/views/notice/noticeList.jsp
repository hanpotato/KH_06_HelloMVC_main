<%@page import="com.rclass.notice.model.vo.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	List<Notice> list = (List)request.getAttribute("list");
	String pageBar = (String)request.getAttribute("pageBar");
	int cPage = (int)request.getAttribute("cPage");
	int numPerPage = (int)request.getAttribute("numPerPage");
	
	boolean delListFlag = (boolean)request.getAttribute("delListFlag");
%>

<%@ include file="/views/common/header.jsp" %>

<style>
	section#notice-container{width:600px; margin:0 auto; text-align:center;}
	section#notice-container h2{margin:10px 0;}
	table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
	table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0;} 
	input#btn-add{float:right;margin:0 10px 15px;}
	input#btn-delList{float:right;margin:0 0 15px;}
	td.td-title{text-align:left; text-indent: 10px;}
</style>

<section id="notice-container">
	<br>
	<h2>공지사항</h2>
	<% if(loginMember != null && "admin".equals(loginMember.getUserId())) { %>
		<% if(delListFlag) { %>
			<input type="button" value="삭제(임시)글 보기" id="btn-delList" onclick="fn_delList()"/>
		<% } else { %>
			<input type="button" value="삭제(임시)글 숨기기" id="btn-delList" onclick="fn_delList()"/>
		<% } %>
		<input type="button" value="글쓰기" id="btn-add" onclick="fn_noticeForm()"/>
	<% } %>
	
	<script>
		function fn_noticeForm() {
			location.href="<%= request.getContextPath() %>/notice/noticeForm";
		}
		function fn_delList() {
			<% if(delListFlag) { %>
				location.href = "<%= request.getContextPath() %>/notice/noticeList?delListFlag=false";
			<% } else { %>
				location.href = "<%= request.getContextPath() %>/notice/noticeList?delListFlag=true";
			<% } %>
		}
	</script>
	
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
				<td class="td-title">
					<% if(n.getStatus().equals("N")) { %>
						<span>[삭제상태]</span>
					<% } %>
					<a href="<%= request.getContextPath() %>/notice/noticeView?noticeNo=<%= n.getNoticeNo() %>"><%= n.getNoticeTitle() %></a>
				</td>
				<td><%= n.getNoticeWriter() %></td>
				<td>
				<% if(n.getFilepath()!=null) { %>
					<a href="javascript:fn_fileDown('<%= n.getFilepath() %>')">
						<img alt='첨부파일' src="<%=request.getContextPath() %>/images/file.png" width="16px">
					</a>
					<script>
						function fn_fileDown(fname) {
							fname = encodeURIComponent(fname);
							location.href = "<%= request.getContextPath() %>/notice/noticeFileDownload?fname="+fname;
						}
					</script>
				<% } %>
				</td>
				<td><%= n.getNoticeDate() %></td>
			</tr>
		<% } %>
	</table>
	<div id="pageBar">
		<%= pageBar %>
	</div>
</section>

<%@ include file="/views/common/footer.jsp" %>