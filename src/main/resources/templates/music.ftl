[#import "spring.ftl" as spring]
[#import "page.ftl" as page]

[@page.page "Files"]

[#include "menu.ftl" /]
<hr>

<div id="nirvana-player" data-media-library-url="[@spring.url "/music/playlist"/]"></div>

<hr>
[#include "logoutForm.ftl" /]

<script data-main="js/nirvana_player/main.js" src="[@spring.url "/requirejs/require.js" /]"></script>
[/@page.page]
