<ul class="site-footer-links">
    <#list navigations! as nav>
    <#if nav.isOpen gt 0>
    <li><a href="${nav.url!navigation_url!}" <#if nav.isNewWin gt 0>target="_blank"</#if>>${nav.name}</a></li>
    </#if>
    </#list>
</ul>