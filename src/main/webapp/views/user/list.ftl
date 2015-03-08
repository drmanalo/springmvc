<#include "/macros/header.ftl"/>
<#include "/macros/footer.ftl"/>
<@header/>
	<#if user_added??>
    <div class="alert alert-success">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        ${ user_added } <@spring.message "user.added"/>
    </div>
    </#if>
	<#if user_updated??>
    <div class="alert alert-info">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        ${ user_updated } <@spring.message "user.been.updated"/>
    </div>
    </#if>
	<#if user_deleted??>
    <div class="alert alert-danger">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        ${ user_deleted } <@spring.message "user.deleted"/>
    </div>
    </#if>
	<h3><@spring.message "users"/></h3>
	<a href="addUser" class="btn btn-xs btn-primary pull-right"><@spring.message "user.create"/></a>
    <table class="table table-condensed">
    	<thead>
    		<tr>
    			<th><@spring.message "user.email"/></th>
    			<th><@spring.message "user.role"/></th>
    			<th><@spring.message "user.updated"/></th>
    			<th></th>
    		</tr>	
    	</thead>
    	<tbody>
    		<#list users as user>
			<tr>
				<td>${ user.username }</td>
				<td>${ user.role }</td>
				<td>${ user.updated ! "-" }</td>
				<td>
					<a href="editUser?id=${user.id}" class="btn btn-xs btn-primary"><@spring.message "user.edit"/></a>
                    <a href="javascript:deleteRow('deleteUser?id=${user.id}');" class="btn btn-xs btn-danger"><@spring.message "user.delete"/></a>
                </td>    
			</tr>
        	</#list>
    	</tbody>
    </table>
<@footer/>