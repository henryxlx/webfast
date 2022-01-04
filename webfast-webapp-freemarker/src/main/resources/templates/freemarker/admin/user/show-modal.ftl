<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>个人详细信息</#macro>

<#macro blockBody>

<table class="table table-striped table-condenseda table-bordered">
  <tr>
    <th width="25%">用户名</th>
    <td width="75%">
      <a class="pull-right" href="${ctx}/user/${user.id}" target="_blank">个人主页</a>
      ${user.username}
    </td>
  </tr>

  <tr>
    <th>Email</th>
    <td>${user.email}</td>
  </tr>

  <tr>
    <th>用户组</th>
    <td>
      <#list roles! as role>
          <#if user.roles?contains(role.roleName)>
              ${role.label}
          </#if>
      </#list>
    </td>
  </tr>

  <tr>
    <th>注册时间/IP</th>
    <td>
      ${user.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}
      /
      ${user.createdIp} ${convertIP(user.createdIp)}
    </td>
  </tr>

  <tr>
    <th>最近登录时间/IP</th>
    <td> 
      <#if user.loginTime == 0>
          --
      <#else>
          ${user.loginTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}
      </#if>
      <#if user.loginIp??>
           / ${user.loginIp} ${convertIP(user.loginIp)}
      </#if>
     </td>
  </tr>

  <tr>
    <th>姓名</th>
    <td>${profile.truename!'暂无'}</td>
  </tr>

  <tr>
    <th>性别</th>
    <td>
      <#if profile.gender??>
        <#if profile.gender == 'male'>男性</#if>
        <#if profile.gender == 'female'>女性</#if>
        <#if profile.gender == 'secret'>秘密</#if>
      <#else>
        暂无
      </#if>
    </td>
  </tr>

   <tr>
    <th>身份证号</th>
    <td>${profile.idcard!'暂无'}</td>
  </tr>

  <tr>
    <th>手机号码</th>
    <td>
      <#if user.verifiedMobile??>
        ${user.verifiedMobile!}<span class="text-success">(已绑定)</span>
      <#else>
        ${profile.mobile!'暂无'}
      </#if>
    </td>
  </tr>

  <tr>
    <th>公司</th>
    <td>${profile.company!'暂无'}</td>
  </tr>

  <tr>
    <th>职业</th>
    <td>${profile.job!'暂无'}</td>
  </tr>


  <tr>
    <th>头衔</th>
    <td>${profile.title!'暂无'}</td>
  </tr>

  <tr>
    <th>个人签名</th>
    <td>${profile.signature!'暂无'}</td>
  </tr>

  <tr>
    <th>自我介绍</th>
    <td>${profile.about!'暂无'}</td>
  </tr>

  <tr>
    <th>个人网站</th>
    <td>${profile.site!'暂无'}</td>
  </tr>

  <tr>
    <th>微博</th>
    <td>${profile.weibo!'暂无'}</td>
  </tr>

  <tr>
    <th>微信</th>
    <td>${profile.weixin!'暂无'}</td>
  </tr>

  <tr>
    <th>QQ</th>
    <td>${profile.qq!'暂无'}</td>
  </tr>

</table>
<hr>
<table class="table table-striped table-condenseda table-bordered">
  <#if fields??>
  <#list fields! as field>
    <tr>
        <th width="25%">{{field.title}}</th>
        <td>
          {% if profile[field.fieldName] %}
            {% if field.type=="date"%}
            {{ profile[field.fieldName]|date("Y-m-d") }}
            {% else %}
            {{ profile[field.fieldName] }}
            {% endif %}
          {% else %}
            暂无
          {% endif %}
        </td>
    </tr>
  </#list>
  </#if>
  </table>
</#macro>

<#macro blockFooter>
  <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
</#macro>