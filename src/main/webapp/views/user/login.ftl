<#include "/macros/header.ftl"/>
<#include "/macros/navigation.ftl"/>
<#include "/macros/footer.ftl"/>
<#import "/spring.ftl" as spring />
<@header/>
<@navigation/>
	<div class="container">
		<form class="form-signin">
        	<h2 class="form-signin-heading">Sign In</h2>
        	<label class="sr-only" for="inputEmail">Email address</label>
        	<input type="text" autofocus="" placeholder="Email" class="form-control" id="inputEmail">
        	<label class="sr-only" for="inputPassword">Password</label>
        	<input type="password" placeholder="Password" class="form-control" id="inputPassword">
        	<button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
      	</form>
    </div>
<@footer/>