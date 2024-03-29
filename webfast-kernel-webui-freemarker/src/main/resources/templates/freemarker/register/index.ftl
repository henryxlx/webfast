<#assign script_controller = 'auth/register'/>

<@block_title '注册'/>

<#include '/layout.ftl'>

<#macro blockContent>
    <div class="row row-6">
        <div class="col-md-6 col-md-offset-3 ptl">
            <#if isRegisterEnabled??>
                <div class="panel panel-default panel-page">
                    <div class="panel-heading"><h2>注册</h2></div>

                    <form id="register-form" class="form-vertical" method="post" action="">
                <@web_macro.flash_messages />
                <#if registerSort?? && registerSort?is_enumerable>
                <#list registerSort! as field>
                <#if field == 'truename'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">姓名</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control">
                    </div>
                </div>
                <#elseif field == 'email'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">邮箱地址</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}" required="required" class="form-control" data-url="${ctx}/register/email/check" data-explain="填写你常用的邮箱作为登录帐号">
                        <p class="help-block">填写你常用的邮箱作为登录帐号</p>
                    </div>
                </div>
                <#elseif field == 'username'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">用户名称</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}" required="required" class="form-control" data-url="${ctx}/register/username/check" data-explain="该怎么称呼你？ 中、英文均可，最长14个英文或7个汉字">
                        <p class="help-block">该怎么称呼你？ 中、英文均可，最长14个英文或7个汉字</p>
                    </div>
                </div>
                <#elseif field == 'job'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">职业</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control">
                    </div>
                </div>
                <#elseif field == 'gender'>
                <div class="form-group">
                    <div class="controls">
                        <label class="control-label required" for="register_${field!}" style="padding: 0 20px 0 0px;">性别</label>&nbsp;
                        <input type="radio" id="register_${field!}_0" name="${field!}" required="required" value="male" checked=true>
                        <label for="profile_gender_0" class="required" style="padding: 0 20px 0 0px;">男</label>
                        <input type="radio" id="register_${field!}_1" name="${field!}" required="required" value="female" >
                        <label for="profile_gender_1" class="required" >女</label>
                    </div>
                </div>
                <#elseif field == 'company'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">公司</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control">
                    </div>
                </div>
                <#elseif field == 'mobile' && (setting('cloud_sms.sms_enabled', '0') == '1' && setting('cloud_sms.sms_registration', 'off') == 'on')>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">手机号码</label>
                    <div class="controls" >
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control" data-role="mobile">
                        <p class="help-block">输入手机号码</p>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label required" for="sms_code">短信验证码</label>
                    <div class="controls row" >
                        <div class = "col-md-8" >
                            <input type="text" class="form-control" id="sms-code" name="sms_code" data-explain="输入短信验证码" required="required" data-url="${ctx}/edu_cloud/sms_check/sms_registration">
                            <p class="help-block">输入短信验证码</p>
                        </div>
                        <div class="col-md-4">
                    <span class="btn btn-default btn-sm js-sms-send" data-url="${ctx}/edu_cloud/sms_send">
                      <span id="js-time-left"></span>
                      <span id="js-fetch-btn-text">获取短信验证码</span>
                   </span>
                        </div>
                    </div>
                </div>

                <#elseif field == 'mobile' && (setting('cloud_sms.sms_enabled', '0') == '0' || setting('cloud_sms.sms_registration', 'off') == 'off')>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">手机号码</label>
                    <div class="controls " >
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control" >
                    </div>
                </div>
                <#elseif field == 'idcard'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">身份证号</label>
                    <div class="controls">
                        <input type="text" id="register_${field!}" name="${field!}"  class="form-control">
                    </div>
                </div>
                <#elseif field == 'password'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">密码</label>
                    <div class="controls">
                        <input type="password" id="register_${field!}" name="${field!}" required="required" class="form-control" data-explain="5-20位英文、数字、符号，区分大小写">
                        <p class="help-block">5-20位英文、数字、符号，区分大小写</p>
                    </div>
                </div>
                <#elseif field == 'confirmPassword'>
                <div class="form-group">
                    <label class="control-label required" for="register_${field!}">确认密码</label>
                    <div class="controls">
                        <input type="password" id="register_${field!}" name="${field!}" required="required" class="form-control" data-explain="再输入一次密码">
                        <p class="help-block">再输入一次密码</p>
                    </div>
                </div>
                </#if>

                <#list userFields! as userField>
                <#if field==userField.fieldName>
                <#if userField.type=="text">
                <div class="form-group">
                    <label for="${(userField.fieldName)!}" class="control-label ">${(userField.title)!}</label>
                    <div class="controls">
                        <textarea id="${(userField.fieldName)!}" name="${(userField.fieldName)!}" class=" form-control "></textarea>
                        <div class="help-block" style="display:none;"></div>
                    </div>
                </div>
                <#elseif userField.type=="int">
                <div class="form-group">
                    <label for="${(userField.fieldName)!}" class=" control-label">${(userField.title)!}</label>
                    <div class="controls">
                        <input type="text" id="${(userField.fieldName)!}" placeholder="最大值为9位整数" name="${(userField.fieldName)!}" class="${(userField.type)} form-control" data-widget-cid="widget-5" data-explain="">
                        <div class="help-block" style="display:none;"></div>
                    </div>
                </div>
                <#elseif userField.type=="float">
                <div class="form-group">
                    <label for="${(userField.fieldName)!}" class="control-label">${(userField.title)!}</label>
                    <div class=" controls">
                        <input type="text" id="${(userField.fieldName)!}" placeholder="保留到2位小数" name="${(userField.fieldName)!}" class="${(userField.type)} form-control" data-widget-cid="widget-5" data-explain="" >
                        <div class="help-block" style="display:none;"></div>
                    </div>
                </div>
                <#elseif userField.type=="date">
                <div class="form-group">
                    <label for="${(userField.fieldName)!}" class="control-label">${(userField.title)!}</label>
                    <div class=" controls">
                        <input type="text" id="${(userField.fieldName)!}" name="${(userField.fieldName)!}" class="${(userField.type)} form-control" data-widget-cid="widget-5" data-explain="" >
                        <div class="help-block" style="display:none;"></div>
                    </div>
                </div>
                <#elseif userField.type=="varchar">
                <div class="form-group">
                    <label for="${(userField.fieldName)!}" class="control-label">${(userField.title)!}</label>
                    <div class=" controls">
                        <input type="text" id="${(userField.fieldName)!}" name="${(userField.fieldName)!}" class="form-control" data-widget-cid="widget-5" data-explain="" >
                        <div class="help-block" style="display:none;"></div>
                    </div>
                </div>
                </#if>
                </#if>
                </#list>
                </#list>
                <#else>
                <div class="form-group">
                    <label class="control-label required" for="register_email">邮箱地址</label>
                    <div class="controls">
                        <input type="text" id="register_email" name="email" required="required" class="form-control" data-url="${ctx}/register/email/check" data-explain="填写你常用的邮箱作为登录帐号">
                        <p class="help-block">填写你常用的邮箱作为登录帐号</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label required" for="register_username">用户名称</label>
                    <div class="controls">
                        <input type="text" id="register_username" name="username" required="required" class="form-control" data-url="${ctx}/register/username/check" data-explain="该怎么称呼你？ 中、英文均可，最长14个英文或7个汉字">
                        <p class="help-block">该怎么称呼你？ 中、英文均可，最长14个英文或7个汉字</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label required" for="register_password">密码</label>
                    <div class="controls">
                        <input type="password" id="register_password" name="password" required="required" class="form-control" data-explain="5-20位英文、数字、符号，区分大小写">
                        <p class="help-block">5-20位英文、数字、符号，区分大小写</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label required" for="register_confirmPassword">确认密码</label>
                    <div class="controls">
                        <input type="password" id="register_confirmPassword" name="confirmPassword" required="required" class="form-control" data-explain="再输入一次密码">
                        <p class="help-block">再输入一次密码</p>
                    </div>
                </div>
                </#if>

                <#if setting('auth.captcha_enabled', '0') == '1'>
                <div class="form-group">
                    <label class="control-label required" for="captcha_num">验证码</label>
                    <div class="controls row" >
                        <div class = "col-md-7" >
                            <input type="text" class="form-control" id="captcha_num" name="captcha_num" maxlength="5" data-explain="输入验证码" required="required"  data-url="${ctx}/register/captcha/check" >
                        </div>
                        <div class="col-md-5">
                            <img src="${ctx}/captcha_num" data-url="${ctx}/captcha_num" id="getcode_num" title="看不清，点击换一张" style="cursor:pointer;" >
                        </div>
                        <div class="col-md-12 help-block">输入验证码</div>
                    </div>

                </div>
                </#if>

                <#if setting('auth.user_terms')?? && setting('auth.user_terms') == 'opened'>
                <div class="form-group">
                    <div class="controls">
                        <label>
                            <input type="checkbox" id="user_terms" checked="checked">我已阅读并同意<a href="${ctx}/userterms" target="_blank">《服务协议》</a>
                        </label>
                    </div>
                </div>

                </#if>

                <div class="form-group">
                    <div class="controls">
                        <button type="submit" id="register-btn" data-submiting-text="正在提交" class="btn btn-primary btn-large btn-block">注册</button>
                    </div>
                </div>

                <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

            </form>

            <#if setting('login_bind.enabled')?? >
            <div class="social-logins">
                <@renderController path='/login/oauth2LoginsBlock?targetPath=' + targetPath! />
            </div>
            </#if>

        </div><!-- /panel -->
        <#else>
        <div class="panel-heading"><h2>系统暂时关闭注册，请联系管理员</h2></div>
        </#if>
    </div>
</div>
</#macro>