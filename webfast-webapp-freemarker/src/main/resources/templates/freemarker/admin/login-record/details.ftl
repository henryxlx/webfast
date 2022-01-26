<#assign modal_class = 'modal-lg' />
<#include '/bootstrap-modal-layout.ftl' />

<#macro blockTitle>${user.username}登录日志</#macro>
<#macro blockBody>

<div id="login-record-details">

  <table class="table table-bordered" id="">
    <thead>
      <tr>
        <th>登录时间</th>
        <th>登录IP</th>
        <th>登录地点</th>
      </tr>
    </thead>
    <tbody>
      <#list loginRecords! as loginRecord>
        <tr>
          <td>${loginRecord.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
          <td>${loginRecord.ip}</td>
          <td>${loginRecord.location!}</td>
        </tr>
      </#list>
    </tbody>
  </table>
  <@web_macro.paginator loginRecordPaginator />
</div>

</#macro>

<#macro blockFooter>
  <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
</#macro>