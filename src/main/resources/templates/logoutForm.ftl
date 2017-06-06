[#import "spring.ftl" as spring]

<form action="[@spring.url "/logout" /]" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign Out"/></div>
</form>
