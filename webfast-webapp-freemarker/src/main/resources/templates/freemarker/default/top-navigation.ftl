<ul class="nav navbar-nav">
    <#list navigations! as nav>
    <#if nav.isOpen gt 0>
        <#if nav.children??><#assign subNavs = nav.children/></#if>
    <li class="<#if subNavs??> dropdown </#if><#if siteNav?? && siteNav == nav.url>active </#if>">
        <a href="<#if nav.url??>${nav.url!navigation_url!}<#else>javascript:;</#if>" class="<#if subNavs??> dropdown-toggle </#if>" <#if nav.isNewWin gt 0>target="_blank"</#if> <#if subNavs??> data-toggle="dropdown" </#if>>${nav.name} <#if subNavs??> <b class="caret"></b></#if></a>
        <#if subNavs??>
        <ul class="dropdown-menu">
            <#list subNavs! as subNav>
            <#if subNav.isOpen gt 0>
            <li><a href="${subNav.url!navigation_url!}"  <#if subNav.isNewWin gt 0>target="_blank"</#if>>${subNav.name}</a></li>
            </#if>
            </#list>
        </ul>
        </#if>
    </li>
    </#if>
    </#list>
</ul>