[#import "spring.ftl" as spring]
[#import "page.ftl" as page]

[@page.page "Files"]

[#include "menu.ftl" /]
<hr>

[#if !(hasParent??) || !hasParent]
    <a href="[@spring.url "/files" /]">..</a>
[/#if]

[#list files as file]
<div>
    [#if file.directory]
        <a href="[@spring.url "/files/directory?url=${file.url}" /]">${file.name}</a>
    [#else]
        <a href="[@spring.url "/files/file?url=${file.url}" /]">${file.name}</a>
    [/#if]
</div>
[/#list]
<hr>
[#include "logoutForm.ftl" /]

[/@page.page]
