<#include "/macros/header.ftl"/>
<#include "/macros/footer.ftl"/>
<@header/>
	<h3><@spring.message "user.create"/></h3>
	<@spring.bind "form"/>
	<@spring.bind "roleMap"/>
	<form method="post" action="saveUser" modelAttribute="form" class="form-horizontal">
		<@spring.formHiddenInput "form.id"/>
		<@spring.formHiddenInput "form.action"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-group">
    		<label for="username" class="col-sm-2 control-label"><@spring.message "user.email"/></label>
  			<div class="col-sm-4">
    			<@spring.formInput "form.username", "class='form-control' placeholder='Email'"/>
				<@spring.showErrors "<br/>", "label label-danger"/>
    		</div>
  		</div>
		<div class="form-group">
    		<label for="password" class="col-sm-2 control-label"><@spring.message "user.password"/></label>
    		<div class="col-sm-4">
    			<@spring.formPasswordInput "form.password", "class='form-control' placeholder='Password'"/>
				<@spring.showErrors "<br/>", "label label-danger"/>
    		</div>
  		</div>
  		<div class="form-group">
    		<label for="role" class="col-sm-2 control-label"><@spring.message "user.role"/></label>
			<div class="col-sm-4">
				<@spring.formSingleSelect "form.role", roleMap, "class='form-control'"/>
			</div>
		</div>
		<div class="form-group">
    		<label for="role" class="col-sm-2 control-label"><@spring.message "user.active"/></label>
			<div class="col-sm-4">
				<@spring.formCheckbox "form.active"/>
			</div>
		</div>
  		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-3">
      			<button type="submit" class="btn btn-default"><@spring.message "user.save"/></button>
    		</div>
  		</div>
	</form>
<@footer/>