<#assign script_controller = 'default/message' />

<#if title?? && title != ''>
<#else>
  <#assign typeTitles = {'info':'提示信息', 'warning':'警告信息', 'error':'错误提示'} />
  <#assign title = typeTitles[type!'info'] />
  <#if title?? && title != ''>
  <#else>
    <#assign title = '提示信息'/>
  </#if>
</#if>

<@block_title '${title}'/>

<#include '/layout.ftl'>

<#macro blockHeadScripts>${script!}</#macro>

<#macro blockContent>

  <div id="page-message-container" class="page-message-container" data-goto="${ctx}/${gotoUrl!}"
       data-duration=${duration!}>
    <div class="page-message-panel">
      <div class="page-message-heading">
      <h2 class="page-message-title">${title}</h2>
    </div>
    <div class="page-message-body">${message!}</div>
  </div>
</div>
</#macro>