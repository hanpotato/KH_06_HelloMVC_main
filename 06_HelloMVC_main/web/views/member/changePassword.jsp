<%@page import="com.rclass.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String userId = request.getParameter("userId");
	boolean flag = (boolean)request.getAttribute("flag");
	
	String msg = (String)request.getAttribute("msg");
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>패스워드 변경</title>
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
	<style>
		@import url('https://fonts.googleapis.com/css?family=Sunflower:300');
		body{font-family:'Sunflower', sans-serif;}
		div#chagePassword-container {
			background-color:#B2EBF4;
			text-align:center;
			width:450px;
			height:250px;
			display:flex;
			align-items: center;
			justify-content: center;
		}
		div#chagePassword-container table {
			margin:0 auto;
			border-spacing:20px;
			text-align:left;
		}
		div#chagePassword-container table tr:last-of-type td {
			text-align:center;
		}
		div#chagePassword-container div span{
			font-size: 20px;
			font-weight: bold;
		}
		#passwordC1, #passwordC2 {font-size:10px;}
	</style>
</head>

<body>
	<% if(flag) { %>
	<script>
		$(function () {
			$('#password_new').on('keyup', function () {
				if ($('#password').val() == $('#password_new').val()) {
					$('#passwordC1').html("현재 비밀번호와 일치 합니다.");
					$('#passwordC1').css('color', 'red');
				}
				else {
					$('#passwordC1').html("사용 가능한 비밀번호입니다.");
					$('#passwordC1').css('color', 'green');
				}
			})
			$('#password_ck').on('keyup', function () {
				if ($('#password_new').val() != $('#password_ck').val()) {
					$('#passwordC2').html("비밀번호가 일치하지않습니다.");
					$('#passwordC2').css('color', 'red');
				}
				else {
					$('#passwordC2').html("비밀번호가 일치합니다.");
					$('#passwordC2').css('color', 'green');
				}
			})
		})
		
		function password_validate() {
			var pwNew=$('#password_new').val();
			var pwCk=$('#password_ck').val();
			if(pwNew!=pwCk) {
				alert("입력한 패스워드가 일치하지 않습니다.");
				$("#password_new").select();
				return false;
			}
			return true;
		}
		
		
		/* function password_validate() {
			if ($('#password').val() == $('#password_new').val()) {
				alert("현재 비밀번호와 일치합니다. 다시 입력해주세요.");
				$('#password_new').val("");
				$('#password_ck').val("");
				$('#password_new').focus();
				$('#passwordC1').html("변경할 비밀번호를 입력하세요.");
				$('#passwordC2').html("변경할 비밀번호를 확인하세요.");
				$('#passwordC1').css('color', 'black');
				$('#passwordC2').css('color', 'black');
				return false;
			}
			if ($('#password_new').val() != $('#password_ck').val()) {
				alert("변경할 비밀번호 확인이 일치하지 않습니다. 다시 입력해주세요.");
				$('#password_new').val("");
				$('#password_ck').val("");
				$('#password_new').focus();
				$('#passwordC1').html("변경할 비밀번호를 입력하세요.");
				$('#passwordC2').html("변경할 비밀번호를 확인하세요.");
				$('#passwordC1').css('color', 'black');
				$('#passwordC2').css('color', 'black');
				return false;
			}
			return true;
			self.close();
		} */

	</script>
	<div id="chagePassword-container">
		<form name="chagePassword" action="<%= request.getContextPath() %>/changePasswordEnd?userId=<%= userId %>" method="post">
			<table>
				<tr>
					<th>현재 비밀번호</th>
					<td>
						<input type="password" name="password" id="password" required />						
					</td>
				</tr>
				<tr>
					<th>변경할 비밀번호</th>
					<td>
						<input type="password" name="password_new" id="password_new" required /><br>
						<span id="passwordC1">변경할 비밀번호를 입력하세요.</span>
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="password_ck" required /><br>
						<span id="passwordC2">변경할 비밀번호를 확인하세요.</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" onclick="return password_validate();" value="변경" />
						&nbsp;
						<input type="button" onclick="self.close()" value="닫기" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="userId" value="<%= userId %>" />
			<!-- input : type hidden은 사용자한테는 안보여주고 값이 필요할때 사용하면 된다.-->
		</form>
	</div>
	<% } else { %>
	<div id="chagePassword-container">
		<div>
			<span><%= msg %></span><br><br>
			<input type="button" value="돌아가기" onclick="location.href='<%= request.getContextPath() %>/changePassword?userId=<%= userId %>'" />
			&nbsp;
			<input type="button" value="닫기" onclick="self.close()" />
		</div>
	</div>
	<% } %>
</body>

</html>