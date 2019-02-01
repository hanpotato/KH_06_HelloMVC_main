<%@page import="com.rclass.notice.model.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Notice n = (Notice) request.getAttribute("notice");
%>

<%@ include file="/views/common/header.jsp" %>

<style>
    section#notice-container{width:600px; margin:0 auto; margin-top: 20px; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;position:relative;}
        span#fname {
    	position:absolute;
    	left:35px;
    	top:5px;
    	width:285px;
    	background-color:#fff;
    }
</style>

<section id="notice-container">
	<table id="tbl-notice">
		<tr>
			<th>제목</th>
			<td>
				<% if(n.getStatus().equals("N")) { %>
					<span>[삭제상태]</span>
				<% } %>
				<%= n.getNoticeTitle() %>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= n.getNoticeWriter() %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<% if(n.getFilepath() != null ) { %>
					<a href="javascript:fn_fileDown('<%= n.getFilepath() %>')">
						<img alt="첨부파일" src="<%= request.getContextPath() %>/images/file.png" width="16px"/>
						<span id="fname"><%= n.getFilepath() %></span>
					</a>
					<script>
						function fn_fileDown(fname) {
							fname = encodeURIComponent(fname);
							location.href="<%= request.getContextPath() %>/notice/noticeFileDownload?fname="+fname;
						}
					</script>
				<% } %>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><%= n.getNoticeContent() %></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="수정하기" onclick="fn_updateNotice()"/>
				<% if(n.getStatus().equals("Y")) { %>
					<input type="button" value="삭제(임시)" onclick="fn_deleteNotice()"/>
				<% } else { %>
					<input type="button" value="복구하기" onclick="fn_restoreNotice()"/>
					<input type="button" value="삭제하기(완전삭제)" onclick="fn_dropNotice()"/>
				<% } %>				
			</td>
		</tr>
	</table>
	<script>
		function fn_updateNotice() {
			location.href = "<%= request.getContextPath() %>/notice/noticeUpdate?no=<%= n.getNoticeNo() %>";
		}
		function fn_deleteNotice() {
			location.href = "<%= request.getContextPath() %>/notice/noticeDelete?no=<%= n.getNoticeNo() %>";
		}
		function fn_restoreNotice() {
			location.href = "<%= request.getContextPath() %>/notice/noticeRestore?no=<%= n.getNoticeNo() %>";
		}
		function fn_dropNotice() {
			location.href = "<%= request.getContextPath() %>/notice/noticeDrop?no=<%= n.getNoticeNo() %>&fileName=<%= n.getFilepath() %>";
		}
	</script>
</section>

<%@ include file="/views/common/footer.jsp" %>