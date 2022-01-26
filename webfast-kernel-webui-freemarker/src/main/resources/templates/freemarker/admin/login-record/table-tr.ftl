<tr id="user-table-tr-${user.id}">
    <td>
        <strong><@admin_macro.user_link user/></strong>
    </td>
    <td>
        ${user.email}
    </td>
    <td>
        <span class="text-sm">${logRecord.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</span>
        <br>
        <a class="text-muted text-sm" href="http://www.baidu.com/s?wd=${logRecord.ip}" target="_blank">${logRecord.ip}</a>
    </td>
    <td>
        ${logRecord.location!}
    </td>
    <td>
        <a href="#modal" data-toggle="modal" data-url="${ctx}/admin/login-record/${user.id}/details" data-url="" class="btn btn-default btn-sm">查看</a>
    </td>
</tr>