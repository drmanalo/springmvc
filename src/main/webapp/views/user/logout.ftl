<#macro logout>
	<form action="logout" method="post">
		<input type="submit" class="btn" id="nav-logout" value="<@spring.message 'logout'/>"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</#macro>