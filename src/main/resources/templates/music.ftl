[#import "spring.ftl" as spring]
[#import "page.ftl" as page]

[@page.page "Files"]

[#include "menu.ftl" /]
<hr>

<div id="player-root"
     data-media-library-url="[@spring.url "/music/medialibrary"/]"
     data-track-url="[@spring.url "/music/track"/]"
     data-icons-url="[@spring.url "/images/icons"/]"
></div>

<hr>
[#include "logoutForm.ftl" /]

<script data-main="js/nirvana_player/main.js" src="[@spring.url "/requirejs/require.js" /]"></script>
[/@page.page]
