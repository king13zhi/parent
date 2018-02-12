<div class="el-header">
	<div class="container" style="position: relative;">
		<ul class="nav navbar-nav navbar-right">
			<li><a href="/">首页</a></li>
			<#--判断logininfo是否为空,如果为空的话显示用户名,否则就是登录和快速注册-->
        <#if logininfo??>>
			<li>
				<a class="el-current-user" href="/personal">${logininfo.username}</a>
			</li>
        <#else>
			<li><a href="/login.html">登录</a></li>
			<li><a href="/register.html">快速注册</a></li>
        </#if>
        <li>
			<a href="/recharge">账户充值 </a></li>
			<li><a href="/logout">注销 </a></li>
			<li><a href="#">帮助</a></li>
		</ul>
	</div>
</div>
