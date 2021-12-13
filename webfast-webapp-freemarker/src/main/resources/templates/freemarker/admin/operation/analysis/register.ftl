<#assign script_controller = 'analysis/register' />
<#assign href = 'admin/operation/analysis/register' />
<#assign menu = 'register' />

<#include '/admin/operation/analysis/layout.ftl'/>

<#macro blockAnalysisBody>
<div class="col-md-12">
    <#if tab! == "trend">
    <div id="line-data"></div>
    <input id="data"  type="hidden" value="${myData!}" />

    <input id="registerStartDate"  type="hidden" value="${registerStartDate!}" />

    <#elseif tab! == "detail">
        <#include '/admin/operation/analysis/table.ftl' />
    </#if>
</div>

</#macro>