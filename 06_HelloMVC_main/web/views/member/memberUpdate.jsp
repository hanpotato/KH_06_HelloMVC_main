<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Member result = (Member)request.getAttribute("result");
	System.out.println(result.toString());
%>

<%@ include file="/views/common/header.jsp" %>

<section id='enroll-container'>
	<script>
		$(function () {
	 		$('#password_2').on('keyup',function () {
				if($('#password_').val() != $('#password_2').val()){
					$('#passwordC').html("비밀번호가 일치하지않습니다.");
					$('#passwordC').css('color','red');
				}
				else {
					$('#passwordC').html("비밀번호가 일치합니다.");
					$('#passwordC').css('color','green');
				}
			})
		})
		$(function () {
			$('#userId_').val("<%= result.getUserId() %>");
			$('#userName').val("<%= result.getUserName() %>");
			$('#age').val("<%= result.getAge() %>");
			$('#email').val("<%= result.getEmail() %>");
			$('#phone').val("<%= result.getPhone() %>");
						
			<% if(result.getGender()=="M") { %>
				$('#gender0')[0].checked = true;
			<% } else { %>
				$('#gender1')[0].checked = true;
			<% } %>
			
			$('#address').val("<%= result.getAddress() %>");
			
			var hobby = "<%= result.getHobby() %>";
			
			var hobbys = hobby.split(",");
			var hobbys2 = $("input[name='hobby']");
			
 			for(var i=0;i<hobbys.length;i++) {
				for(var j=0;j<hobbys2.length;j++) {
					if(hobbys[i] == hobbys2[j].value) {
						hobbys2[j].checked = true;
					}
				}
			}
		})
	</script>
	<h2>회원가입정보 수정</h2>
	<form action="<%= request.getContextPath() %>/memberUpdateEnd" method="post">
		<table>
			<tr>
				<th>
					아이디
				</th>
				<td>
					<input type="text" name="userId" id="userId_" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th>
					패스워드
				</th>
				<td>
					<input type="password" name="password" id="password_" required/>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>
					<input type="password" id="password_2" required/><br>
					<span id='passwordC'>패스워드를 확인하세요.</span>
					
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="userName" id="userName" required/>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>
					<input type="number" name="age" id="age" />
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<input type="email" placeholder="abc@dse.com" name="email" id="email" />
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required/>
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<input type="radio" name="gender" id="gender0" value="M" /><label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F" /><label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>
					주소
				</th>
				<td>
					<input type="text" name="address" id="address" />
				</td>
			</tr>
			<tr>
				<th>취미</th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="독서"><label for="hobby0">독서</label>
					<input type="checkbox" name="hobby" id="hobby1" value="꽃꽂이"><label for="hobby1">꽃꽂이</label>
					<input type="checkbox" name="hobby" id="hobby2" value="십자수"><label for="hobby2">십자수</label><br>
					<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="승마"><label for="hobby4">승마</label>
					<input type="checkbox" name="hobby" id="hobby5" value="펜싱"><label for="hobby5">펜싱</label>
				</td>
			</tr>
		</table>
		<input type="submit" value="정보수정" />
		<input type="button" value="회원탈퇴" onclick="location.href='<%= request.getContextPath() %>/delMember'"/>
	</form>
</section>

<%@ include file="/views/common/footer.jsp" %>