<#assign menu = 'logs'/>
<#assign script_controller = 'app/logs'/>

<@block_title '应用更新日志'/>

<#include '/admin/app/layout.ftl'/>

<#macro blockMain>
  <style>
    .table .popover {
      max-width: 400px;
    }
  </style>

  <div class="page-header">
    <h1>应用更新日志</h1>
  </div>

  <table class="table table-striped table-hover">
    <tbody>
      <#list entries! as log>
        <tr>
          <td>${log.label!}  ${log.logDate!} </br>
            ${log.details!}
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</#macro>