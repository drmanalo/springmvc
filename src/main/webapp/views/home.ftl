<#include "/macros/header.ftl"/>
<#include "/macros/footer.ftl"/>
<#import "/spring.ftl" as spring />
<@header/>
	<div class="inner cover">
		<#if Session["SPRING_SECURITY_CONTEXT"]?exists>
			<h1 class="cover-heading"><@spring.message "welcome"/> ${Session["SPRING_SECURITY_CONTEXT"].authentication.name}!</h1>
		</#if>
		<p class="lead"><@spring.message "lead"/></p>
    	<p class="lead">
			<a href="#" class="btn btn-lg btn-default"><@spring.message "learn.more"/></a>
    	</p>
    </div>
<@footer/>