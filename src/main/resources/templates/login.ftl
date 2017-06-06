[#import "spring.ftl" as spring]
[#import "page.ftl" as page]

[@page.page "Login"]

<form action="[@spring.url "/login" /]" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>

[/@page.page]
