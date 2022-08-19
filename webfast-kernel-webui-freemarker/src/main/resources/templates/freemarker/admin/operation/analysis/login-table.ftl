      <table id="user-table" class="table table-striped table-hover" data-search-form="#user-search-form">
          <thead>
            <tr>
              <th>用户名</th>
              <th>IP地址</th>
              <th>登录时间</th>
            </tr>
          </thead>
          <tbody>
            <#list loginDetail! as data>
              <tr>
                <td><@admin_macro.user_link users['' + data.userId]/></td>
                <td>${(data.ip)!}</td>
                <td>${data.createdTime?number_to_date?string('yyyy-MM-dd HH:mm:ss')}</td>
              </tr>
            </#list>
          </tbody>
      </table>    
      <@web_macro.paginator paginator!/>