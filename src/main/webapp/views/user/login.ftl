<#include "/macros/header.ftl"/>
<#include "/macros/navigation.ftl"/>
<#include "/macros/footer.ftl"/>
<@header/>
<@navigation/>
<div class="form-signin">
	<form name="f" method="post" action="login">
		<#if RequestParameters.error??>
			<div class="alert alert-danger"><@spring.message "bad.credentials"/></div>
		</#if>
		<#if RequestParameters.logout??>
			<div class="alert alert-success"><@spring.message "logout.message"/></div>
		</#if>
		<#if RequestParameters.expired??>
			<div class="alert alert-danger"><@spring.message "session.expired"/></div>
		</#if>
		<input type="text" class="form-control" placeholder='<@spring.message "user.email"/>' name="username" />
		<input type="password" class="form-control" placeholder='<@spring.message "user.password"/>' name="password" />
		<label for="remember_me" style="font-weight:normal;"><@spring.message "remember.me"/></label>
		<input id="remember_me" name="remember-me" type="checkbox" style="margin:5px 2px;"/>
		<button class="btn btn-lg btn-primary btn-block" type="submit"><@spring.message "login"/></button>
	</form>
</div>
<@footer/>
<@footer/>