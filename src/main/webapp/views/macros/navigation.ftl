<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<#include "/user/logout.ftl"/>
<#macro navigation>
	<div class="masthead clearfix">
    	<div class="inner">
      		<h3 class="masthead-brand"><@spring.message "prototype"/></h3>
      		<nav>
        		<ul class="nav masthead-nav">
					<#if Session["SPRING_SECURITY_CONTEXT"]?exists>
						<li><@logout/></li>
          				<li><a href="home"><@spring.message "home"/></a></li> 				
						<#assign authorities = Session["SPRING_SECURITY_CONTEXT"].authentication.authorities />
						<#if authorities?seq_contains("ROLE_ADMIN")>
							<li><a href="listUsers"><@spring.message "users"/></a></li>
						</#if>
	          		</#if>
        		</ul>
      		</nav>
    	</div>
  	</div>
</#macro>  