<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>统一认证系统</title>
<link href="/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="login_box">
      <div class="login_l_img"><img src="/images/login-img.png" /></div>
      <div class="login">
          <div class="login_logo"><a href="#"><img src="/images/login_logo.png" /></a></div>
          <div class="login_name">
               <p>统一认证系统</p>
          </div>
          <form method="post" action="/auth/signin">
              <input name="username" type="text"  value="用户名" onfocus="this.value=''" onblur="if(this.value==''){this.value='用户账号'}">
              <span id="password_text" onclick="this.style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus();" >密码</span>
			  <input name="password" type="password" id="password" style="display:none;" onblur="if(this.value==''){document.getElementById('password_text').style.display='block';this.style.display='none'};"/>
              <input value="sign in" style="width:100%;" type="submit">
          </form>
      </div>
      <div class="copyright">email: liuweijw.github@foxmail.com</div>
</div>
</body>
</html>
