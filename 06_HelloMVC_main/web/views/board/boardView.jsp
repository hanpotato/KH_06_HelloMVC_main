<%@page import="com.rclass.board.model.vo.BoardComment"%>
<%@page import="java.util.List"%>
<%@page import="com.rclass.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Board bo = (Board)request.getAttribute("bo");
	List<BoardComment> comments = (List)request.getAttribute("comments");
%>

<%@ include file="/views/common/header.jsp" %>

<style>
    section#board-container{width:600px; margin:0 auto; margin-top: 20px; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
    
    table#tbl-comment textarea{margin: 4px 0 0 0;}
    table#tbl-comment button.btn-insert2{width:60px; height:23px; color:white; background:#3300ff; position:relative; top:-5px; left:10px;}
</style>

<section id="board-container">
	<h2>자유게시판 상세내용</h2>
		<table id="tbl-board">
			<tr>
				<th>글번호</th>
				<td><%= bo.getBoardNo() %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><%= bo.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= bo.getBoardWriter() %></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><%= bo.getBoardReadcount() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<% if(bo.getBoardOriginalFilename()!=null) { %>
						<a href="javascript:fn_fileDown('<%= bo.getBoardRenamedFilename() %>','<%= bo.getBoardOriginalFilename() %>')">
							<img alt="첨부파일" src="<%= request.getContextPath() %>/images/file.png" width="16px">
							<span><%= bo.getBoardOriginalFilename() %></span>
						</a>
						<script>
							function fn_fileDown(rName,oName) {
								rName = encodeURIComponent(rName);
								oName = encodeURIComponent(oName);
								location.href="<%= request.getContextPath() %>/board/boardFileDownload?rName="+rName+"&oName="+oName;
							};
						</script>
					<% } %>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><%= bo.getBoardContent() %></td>				
			</tr>
			<% if(loginMember!=null&&(loginMember.getUserId().equals("admin")||loginMember.getUserId().equals(bo.getBoardWriter()))) { %>
				<tr>
					<td colspan="2">
						<input type="button" value="수정하기" onclick="fn_updateNotice()"/>
						<input type="button" value="삭제하기" onclick="fn_deleteNotice()"/>
					</td>
				</tr>
			<% } %>
		</table>
	</form>
	<div id="comment-container">
		<div class="comment-editor">
			<form name="commentFrm" action="<%= request.getContextPath() %>/board/boardCommentInsert" method="post">
				<input type="hidden" name="boardRef" value="<%= bo.getBoardNo() %>"/>
				<input type="hidden" name="boardCommentWriter" value="<%=loginMember!=null?loginMember.getUserId():"" %>"/>
				<input type="hidden" name="boardCommentLevel" value="1"/>
				<input type="hidden" name="boardCommentRef" value="0"/>
				<textarea name = "boardCommentContent" cols="60" rows="3"></textarea>
				<button type="submit" id="btn-insert">등록</button>
			</form>
		</div>
		<table id="tbl-comment">
			<% if(comments!=null) {
				for(BoardComment bc:comments) { 
					if(bc.getBoardCommentLevel()==1) { %>
					<tr class="level1">
						<td>
							<sub class="comment-writer"><%= bc.getBoardCommentWriter() %></sub>
							<sub class="comment-date"><%= bc.getBoardCommentDate() %></sub>
							<br/>
							<%= bc.getBoardCommentContent() %>
						</td>
						<td>
							<button class="btn-reply" value="<%= bc.getBoardCommentNo() %>">답글</button>
							<% if(loginMember!=null&&(bc.getBoardCommentWriter().equals(loginMember.getUserId())||"admin".equals(loginMember.getUserId()))) { %>
								<button class="btn-delete" value="<%= bc.getBoardCommentNo() %>">삭제</button>
							<% } %>
						</td>
					</tr>
					<% } else { %>
					<tr class='level2'>
						<td>
							<sub><%=bc.getBoardCommentWriter() %></sub>
							<sub><%=bc.getBoardCommentDate() %></sub>
							<br/>
							<%= bc.getBoardCommentContent() %>
						</td>
						<td></td>
					</tr>
					<% } %>
			<% }
			}%>
		</table>
		<script>
			$(".btn-reply").on("click", function(){
				<% if(loginMember!=null) { %>
					var tr = $("<tr></tr>");
					var html = "<td style='display:none;text-align:left;' colspan='2'>";
					html+="<form action='<%= request.getContextPath() %>/board/boardCommentInsert' method='post'>";
					html+="<input type='hidden' name='boardRef' value='<%= bo.getBoardNo() %>'/>";
					html+="<input type='hidden' name='boardCommentWriter' value='<%= loginMember.getUserId() %>'/>";
					html+="<input type='hidden' name='boardCommentLevel' value='2'/>";
					html+="<input type='hidden' name='boardCommentRef' value='"+$(this).val()+"'/>";
					html+="<textarea cols='60' rows='1' name='boardCommentContent'></textarea>";
					html+="<button type='submit' class='btn-insert2'>등록</button>";
					html+="</form></td>";
					tr.html(html);
					tr.insertAfter($(this).parent().parent()).children("td").slideDown(800);
					
					$(this).off("click");
					
					tr.find("form").submit(function() {
						if(<%= loginMember == null %>) {
							fn_loginAlert();
							event.preventDefault();
							return;
						}
						var content = $(this).children('textarea').val().trim().length;
						if(content==0) {
							alert("내용을 입력하세요");
							event.preventDefault();
							return;
						}
					});
				<% } else { %>
					fn_loginAlert();
				<% } %>
			});
			$(".btn-delete").on("click", function() {
				if(!confirm('정말로 삭제하시겠습니까?')) return;
				location.href="<%= request.getContextPath() %>/board/boardCommentDelete?boardNo=<%= bo.getBoardNo() %>&delNo="+$(this).val();
			})
		</script>
	</div>
	<script>
		$(function() {
			$("[name=boardCommentContent]").focus(function() {
				if(<%= loginMember==null %>) {
					fn_loginAlert();
					$('[name=userId]').focus();
				}
			})
			$("[name=commentFrm]").submit(function(e) {
				if(<%= loginMember==null %>) {
					fn_loginAlert();
					e.preventDefault();
					return;
				}
				var len=$("textarea[name=boardCommentContent]").val().trim().length;
				if(len==0) {
					alert("내용을 입력하세요.");
					e.preventDefault();
				}
			});
		})
		function fn_loginAlert() {
			alert("로그인 후 이용할 수 있습니다.");
			$('[name=userId]').focus();
		} 
	</script>
</section>

<%@ include file="/views/common/footer.jsp" %>