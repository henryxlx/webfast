<#assign submenu = 'auth'/>
<#assign script_controller = 'setting/auth'/>

<#include '/admin/system/user-set-layout.ftl'/>
<#macro blockTitle>注册设置 - 用户相关设置 - ${blockTitleParent}</#macro>

<#macro blockMainContent>

<div class="page-header"><h1>注册设置</h1></div>

<@web_macro.flash_messages />

<form  id="auth-form" class="form-horizontal" method="post" novalidate>
    <div class="form-group">
        <div class="col-md-3 control-label">
            <label >新用户注册</label>
        </div>
        <div class="controls col-md-8 radios">
            <@radios 'register_mode' {'opened':'开启', 'closed':'关闭'} 'auth.registerMode' />
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-3 control-label">
            <label >注册防护机制</label>
        </div>
        <div class="controls col-md-8 ">
            <label class="radio-inline">
                <input type="radio" name="register_protective" id="none" value="none" <#if (auth.registerProtective)!'' == "none">checked="checked"</#if>> 无
            </label>
            <label class="radio-inline">
                <input type="radio" name="register_protective" id="low" value="low" <#if (auth.registerProtective)!'' == "low">checked="checked"</#if>> 低
            </label>
            <label class="radio-inline">
                <input type="radio" name="register_protective" id="middle" value="middle" <#if (auth.registerProtective)!'' == "middle">checked="checked"</#if>> 中
            </label>
            <label class="radio-inline">
                <input type="radio" name="register_protective" id="high" value="high"<#if (auth.registerProtective)!'' == "high">checked="checked"</#if>> 高
            </label>
        </div>

        <div class="controls col-md-8 mtl low register-help" <#if (auth.registerProtective)!'' == "low"><#else>style="display:none;"</#if>>
        <div class="well">
            方案说明：
            <p class="mll mtm">1：注册时需填写验证码。</p>
        </div>
    </div>

    <div class="controls col-md-8 mtl middle register-help" <#if (auth.registerProtective)!'' == "middle"><#else>style="display:none;"</#if>>
    <div class="well">
        方案说明：
        <p class="mll mtm">1：注册时需填写验证码。</p>
        <p class="mll mtm">2：同一IP24小时內只能注册30次。</p>
    </div>
    </div>

    <div class="controls col-md-8 mtl high register-help" <#if (auth.registerProtective)!'' == "high"><#else>style="display:none;"</#if>>
    <div class="well">
        方案说明：
        <p class="mll mtm">1：注册时需填写验证码。</p>
        <p class="mll mtm">2：同一IP24小时內只能注册10次。</p>
        <p class="mll mtm">3：同一IP1小时內只能注册1个账号。</p>
    </div>
    </div>
    <#--
    <#if (auth.registerProtective)!'' != "none">
    <div class="controls col-md-8 mtl"  id="register-help">
        <div class="well">
            方案说明：
            {% if  auth.register_protective|default(null) == "low" %}
            <div class="low">
                <p class="mll mtm">1：注册时需填写验证码。</p>
            </div>
            {% endif %}

            {% if  auth.register_protective|default(null) == "middle" %}
            <div class="middle">
                <p class="mll mtm">1：注册时需填写验证码。</p>
                <p class="mll mtm">2：同一IP24小时內只能注册30次。</p>
            </div>
            {% endif %}

            {% if  auth.register_protective|default(null) == "high" %}
            <div class="high">
                <p class="mll mtm">1：注册时需填写验证码。</p>
                <p class="mll mtm">2：同一IP24小时內只能注册10次。</p>
                <p class="mll mtm">3：同一IP1小时內只能注册1个账号。</p>
            </div>
            {% endif %}
        </div>
    </div>
    </#if> -->
    </div>


    <#--   <div class="form-group">
        <div class="col-md-3 control-label">
            <label >是否开启注册时的验证码</label>
        </div>
        <div class="controls col-md-8 radios">
            {{ radios('captcha_enabled', {1:'开启', 0:'关闭'}, auth.captcha_enabled) }}
            <p class="help-block">开启后，注册时使用验证码</p>
        </div>
    </div> -->

    <div class="form-group">
        <div class="col-md-3 control-label">
            <label >邮箱验证登录</label>
        </div>
        <div class="controls col-md-8 radios">
            <@radios 'email_enabled' {'opened':'开启', 'closed':'关闭'} 'auth.email_enabled' />
        </div>
    </div>
    <div class="help-block" style="margin-left:250px;margin-top:-15px;">开启后,邮箱未验证的用户将无法登录</div>
    <input type="hidden" name="setting_time"  value="{{setting('auth.setting_time') }}" >

    <fieldset>
        <legend>新用户激活邮件设置</legend>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="email_activation_title" >新用户激活邮件标题</label>
            </div>
            <div class="controls col-md-8">
                <input type="text" id="email_activation_title" name="email_activation_title" class="form-control" value="${(auth.emailActivationTitle)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="email_activation_body">新用户激活邮件内容</label>
            </div>
            <div class="controls col-md-8">
                <textarea id="email_activation_body" name="email_activation_body" class="form-control" rows="5">${(auth.emailActivationBody)!}</textarea>
                <div class="help-block">
                    <#--{% verbatim %}-->
                    <div>变量说明：</div>
                    <ul>
                        <li>{{nickname}} 为接收方用户昵称</li>
                        <li>{{sitename}} 为网站名称</li>
                        <li>{{siteurl}} 为网站的地址</li>
                        <li>{{verifyurl}} 为邮箱验证地址</li>
                    </ul>
                    <#--{% endverbatim %}-->
                </div>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend>注册信息字段设置</legend>
        <div class="row">
            <div class="col-md-3 control-label">
                <label for="email_activation_body">注册时需提供</label>
            </div>
            <div class="controls col-md-8 "  id="show-list">
                <#list (auth.registerSort)! as sort>
                {% if sort=='email' %}
                <button type="button" class="btn btn-default btn-xs">邮箱地址</button>
                {% endif %}
                {% if sort=='nickname' %}
                <button type="button" class="btn btn-default btn-xs">昵称</button>
                {% endif %}
                {% if sort=='password' %}
                <button type="button" class="btn btn-default btn-xs">密码</button>
                {% endif %}
                {% if sort=='truename' %}
                <button type="button" class="btn btn-default btn-xs">姓名</button>
                {% endif %}
                {% if sort=='idcard' %}
                <button type="button" class="btn btn-default btn-xs">身份证号码</button>
                {% endif %}
                {% if sort=='mobile' %}
                <button type="button" class="btn btn-default btn-xs">手机号码</button>
                {% endif %}
                {% if sort=='job' %}
                <button type="button" class="btn btn-default btn-xs">职业</button>
                {% endif %}
                {% if sort=='gender' %}
                <button type="button" class="btn btn-default btn-xs">性别</button>
                {% endif %}
                {% if sort=='company' %}
                <button type="button" class="btn btn-default btn-xs">公司</button>
                {% endif %}
                {% if  userFields %}
                {% for field in userFields %}
                {% if sort==field.fieldName %}
                <button type="button" class="btn btn-default btn-xs">{{field.title}}</button>
                {% endif %}
                {% endfor %}
                {% endif %}
                </#list>
                <div class="pull-right "><a href="javascript:" id="show-list-btn">编辑 <span class="
  glyphicon glyphicon-chevron-down"></span></a></div>
            </div>
            <div  class="controls col-md-8 " id="show-register-list">
                <div class="form-group "><ul class="sortable-list" data-role="list">
                </div>
                <div class="form-group"><ul class="register-list sortable-list" data-role="list">
                        <#if (auth.registerFieldNameArray)??>
                        <#list (auth.registerFieldNameArray)! as sort>
                        {% if sort=='email' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="email"></input><input type="hidden"  name="registerFieldNameArray[]" value="email"></input> 邮箱地址<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>

                        {% endif %}
                        {% if sort=='nickname' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="nickname"></input><input type="hidden"  name="registerFieldNameArray[]" value="nickname"></input> 昵称<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>
                        {% endif %}
                        {% if sort=='password' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="password"></input><input type="hidden"  name="registerFieldNameArray[]" value="password"></input><input type="hidden"  name="registerSort[]" value="confirmPassword"></input><input type="hidden"  name="registerFieldNameArray[]" value="confirmPassword"></input> 密码<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>
                        {% endif %}
                        {% if sort=='truename' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" {% for sort in auth.registerSort %}{% if sort=="truename"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="truename"></input><input type="hidden"  name="registerFieldNameArray[]" value="truename"></input> 姓名
                        </li>
                        {% endif %}
                        {% if sort=='mobile' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" {% for sort in auth.registerSort %}{% if sort=="mobile"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="mobile"></input><input type="hidden"  name="registerFieldNameArray[]" value="mobile"></input> 手机号码<span class="text-muted"> (带格式校检)</span>
                        </li>
                        {% endif %}
                        {% if sort=='idcard' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" {% for sort in auth.registerSort %}{% if sort=="idcard"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="idcard"></input><input type="hidden"  name="registerFieldNameArray[]" value="idcard"></input> 身份证号码<span class="text-muted"> (带格式校检)</span>
                        </li>
                        {% endif %}
                        {% if sort=='gender' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" {% for sort in auth.registerSort %}{% if sort=="gender"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="gender"></input><input type="hidden"  name="registerFieldNameArray[]" value="gender"></input> 性别
                        </li>
                        {% endif %}
                        {% if sort=='job' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" {% for sort in auth.registerSort %}{% if sort=="job"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="job"></input><input type="hidden"  name="registerFieldNameArray[]" value="job"></input> 职业
                        </li>
                        {% endif %}
                        {% if sort=='company' %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox"  {% for sort in auth.registerSort %}{% if sort=="company"%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="company"></input><input type="hidden"  name="registerFieldNameArray[]" value="company"></input> 公司
                        </li>
                        {% endif %}
                        <#list userFields! as field>
                        {% if sort==field.fieldName %}
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox"  {% for sort in auth.registerSort %}{% if sort==field.fieldName%}checked=true{% endif %}{% endfor %} name="registerSort[]" value="{{sort}}"></input><input type="hidden"  name="registerFieldNameArray[]" value="{{sort}}"></input> {{field.title}}
                        </li>
                        {% endif %}
                        </#list>
                        </#list> <#-- list (auth.registerFieldNameArray)! as sort -->
                        <#else>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="email"></input><input type="hidden"  name="registerFieldNameArray[]" value="email"></input> 邮箱地址<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="nickname"></input><input type="hidden"  name="registerFieldNameArray[]" value="nickname"></input> 昵称<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" checked=true onclick="return false" name="registerSort[]" value="password"></input><input type="hidden"  name="registerFieldNameArray[]" value="password"></input><input type="hidden"  name="registerSort[]" value="confirmPassword"></input><input type="hidden"  name="registerFieldNameArray[]" value="confirmPassword"></input> 密码<span class="text-muted"> (系统必需，不可取消)</span>
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="truename"></input><input type="hidden"  name="registerFieldNameArray[]" value="truename"></input> 姓名
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="mobile"></input><input type="hidden"  name="registerFieldNameArray[]" value="mobile"></input> 手机号码<span class="text-muted"> (带格式校检)</span>
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="idcard"></input><input type="hidden"  name="registerFieldNameArray[]" value="idcard"></input> 身份证号码<span class="text-muted"> (带格式校检)</span>
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="gender"></input><input type="hidden"  name="registerFieldNameArray[]" value="gender"></input> 性别
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="job"></input><input type="hidden"  name="registerFieldNameArray[]" value="job"></input> 职业
                        </li>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="company"></input><input type="hidden"  name="registerFieldNameArray[]" value="company"></input> 公司
                        </li>
                        <#list userFields! as field>
                        <li class="list-group-item clearfix" data-role="item" >
                            <span class="glyphicon glyphicon-resize-vertical sort-handle"></span>
                            &nbsp;<input type="checkbox" name="registerSort[]" value="{{field.fieldName}}"></input><input type="hidden"  name="registerFieldNameArray[]" value="{{field.fieldName}}"></input> {{field.title}}
                        </li>
                        </#list>
                        </#if> <#-- #if (auth.registerFieldNameArray)?? -->
                    </ul>
                    <span class="help-block pull-right">可拖动调整先后次序.勾选后，在注册时就必须要填写该项目，将会增加注册难度，请谨慎使用</span>
                </div>
                <div style="text-align:center;"><a href="javascript:" id="hide-list-btn">收起 <span class="
glyphicon glyphicon-chevron-up"></span></a></div>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend>欢迎信息设置</legend>

        <div class="form-group" style="display:none;">
            <div class="col-md-3 control-label">
                <label>发送欢迎信息</label>
            </div>
            <div class="controls col-md-8 checkboxs">
                {{ checkboxs('welcome_methods', {'message':'站内私信', 'email':'电子邮件'}, auth.welcome_methods) }}
                <div class="help-block">新用户邮件激活开启时，电子邮件的发送欢迎信息方式无效。</div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="welcome_title">发送欢迎信息</label>
            </div>
            <div class="controls col-md-8 radios">
                <@radios 'welcome_enabled' {'opened':'开启', 'closed':'关闭'} 'auth.welcome_enabled' />
                <div class="help-block">欢迎信以站内私信的方式，发送给新用户。</div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="welcome_sender">欢迎信息发送方</label>
            </div>
            <div class="controls col-md-8">
                <input type="text" id="welcome_sender" name="welcome_sender" class="form-control" value="${(auth.welcomeSender)!}">
                <div class="help-block">通常为这个网站的管理员，请输入用户昵称。</div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="welcome_title">欢迎信息标题</label>
            </div>
            <div class="controls col-md-8">
                <input type="text" id="welcome_title" name="welcome_title" class="form-control" value="${(auth.welcomeTitle)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="welcome_body">欢迎信息内容</label>
            </div>
            <div class="controls col-md-8">
                <textarea id="welcome_body" name="welcome_body" class="form-control" rows="5">${(auth.welcomeBody)!}</textarea>
                <div class="help-block">
                    <#--{% verbatim %}-->
                    <div>注意： 私信长度不能超过1000个字符</div>
                    <div>变量说明：</div>
                    <ul>
                        <li>{{nickname}} 为接收方用户昵称</li>
                        <li>{{sitename}} 为网站名称</li>
                        <li>{{siteurl}} 为网站的地址</li>
                    </ul>
                    <#--{% endverbatim %}-->
                </div>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend>服务条款设置</legend>
        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="user_terms">是否开启服务条款</label>
            </div>
            <div class="controls col-md-8 radios">
                <@radios 'user_terms' {'opened':'开启', 'closed':'关闭'} 'auth.user_terms' />
                <div class="help-block">开启后用户注册时必须同意条款才能注册</div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label for="user_terms_body">条款内容</label>
            </div>
            <div class="controls col-md-8">
                <textarea class="form-control" id="user_terms_body" rows="16" name="user_terms_body" data-image-upload-url="{{ path('editor_upload', {token:upload_token('default')}) }}">${(auth.userTermsBody)!}</textarea>
            </div>
        </div>

    </fieldset>

    <div class="form-group">
        <div class="col-md-3 control-label"></div>
        <div class="controls col-md-8">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
    <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
</form>

</#macro>