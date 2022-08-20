<#assign script_controller = 'analysis/user-sum' />
<#assign href = 'admin/operation/analysis/user-sum' />
<#assign menu = 'user-sum' />

<#include '/admin/operation/analysis/layout.ftl'/>

<#macro blockAnalysisBody>
  <div class="col-md-12">

              <#if tab=="trend" >
	          <div id="line-data"></div>
	           <input id="data"  type="hidden" value='${chartData!}'>
	          </input> <input id="userSumStartDate"  type="hidden" value='${userSumStartDate!}'>
	          </input>
              <#elseif tab=="detail">
                  <#include '/admin/operation/analysis/user-sum-table.ftl' />
              </#if>
  </div>
</#macro>
