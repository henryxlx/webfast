<#assign script_controller = 'analysis/login' />
<#assign href = 'admin/operation/analysis/login' />
<#assign menu = 'login' />

<#include '/admin/operation/analysis/layout.ftl'/>

<#macro blockAnalysisBody>
  <div class="col-md-12">

              <#if tab == "trend">
	          <div id="line-data"></div>
	           <input id="data"  type="hidden" value='${data!}'>
	          </input> <input id="loginStartDate"  type="hidden" value='${loginStartDate!}'>
	          </input>
              <#elseif tab == "detail" >
                  <#include '/admin/operation/analysis/login-table.ftl' />
              </#if>
  </div>
</#macro>
