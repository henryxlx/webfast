<#assign script_controller = 'auth/password-reset'/>

<@block_title '重设密码'/>

<#include '/layout.ftl'>

<#macro blockContent>
    <div class="row row-6">
        <div class="col-md-6 col-md-offset-3 ptl">
            <div class="panel panel-default panel-page">
                <div class="panel-heading"><h2>重设密码</h2></div>

                <ul class="nav nav-tabs mbl">

                <li class="active js-find-by-email">
                    <a style="cursor: pointer;">邮箱地址
                    </a>
                </li>
                <#if setting('cloud_sms.sms_forget_password')! == 'on'>
                <li class="js-find-by-mobile">
                    <a style="cursor: pointer;">手机号码
                    </a>
                </li>
                </#if>

            </ul>

            <#if errorMessage??> <div id="alertxx" class="alert alert-danger">${errorMessage}</div> </#if>

            <form id="password-reset-form" class="form-vertical" method="post">
                <div class="form-group">
                    <#-- {{ form_label(form.email, '邮箱地址', {label_attr:{class:'control-label'}}) }}-->
                    <label class="control-label required" for="form_email">邮箱地址</label>

                    <div class="controls">
                        <#-- {{ form_widget(form.email, {attr:{class:'form-control'}}) }} -->
                        <input type="email" id="form_email" name="email" required="required" class="form-control" />

                        <p class="help-block">请输入你在${setting('site.name')!}注册时填写的邮箱地址</p>
                    </div>
                </div>

                <div class="form-group">
                    <div class="controls">
                        <#-- {{ form_rest(form) }} -->
                        <input type="hidden" id="password-reset-form_token" name="_form_token" value="13a6333bab0fb99ba83d29b7600b958402f53a0a" />

                        <button type="submit" class="btn btn-primary" data-loading-text="正在发送重设密码邮件...">重设密码</button>
                    </div>
                </div>

                <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

            </form>


            <form id="password-reset-by-mobile-form" class="form-vertical" action="{{ path('password_reset_by_sms') }}" method="post" style="display: none;">
                {# <div class="form-group">
                    <label class="control-label required" for="nickname">用户名</label>
                    <div class="controls">
                        <input type="text" id="nickname" name="nickname"  class="form-control" data-role="nickname">
                    </div>
                </div> #}

                <div class="form-group">
                    <label class="control-label required" for="mobile">手机号码</label>
                    <div class="controls">
                        <input type="text" id="mobile" name="mobile"  class="form-control" data-role="mobile">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label required" for="sms_code">短信验证码</label>
                    <div class="controls row" >
                        <div class = "col-md-8" >
                            <input type="text" class="form-control" id="sms-code" name="sms_code" data-explain="输入短信验证码" required="required" data-url="{{path('edu_cloud_sms_check',{type:'sms_forget_password'})}}">
                        </div>

                        <div class="col-md-4">
              <span class="btn btn-default btn-sm pull-right js-sms-send" data-url="{{ path('edu_cloud_sms_send') }}">
                <span id="js-time-left"></span>
                <span id="js-fetch-btn-text">获取短信验证码{# 秒后重新获取 #}</span>
             </span>
                        </div>

                        <div class="col-md-12 help-block"></div>
                    </div>
                </div>

                {# <div class="form-group">
                    <label class="control-label required" for="captcha_num">验证码</label>
                    <div class="controls row" >
                        <div class = "col-md-8" >
                            <input type="text" class="form-control" id="captcha_num" name="captcha_num" maxlength="5" data-explain="输入验证码" required="required"  data-url="{{path('register_captcha_check')}}" >
                        </div>
                        <div class="col-md-4">
                            <img class="pull-right" src="${ctx}/captcha_num" data-url="${ctx}/captcha_num" id="getcode_num" title="看不清，点击换一张" style="cursor:pointer;" >
                        </div>
                        <div class="col-md-12 help-block">输入验证码</div>
                    </div>

                </div> #}

                <div class="form-group">
                    <div class="controls">
                        <button type="submit" class="btn btn-primary" data-loading-text="提交中...">重设密码</button>
                    </div>
                </div>

                <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
            </form>

        </div><!-- /panel -->

    </div>

</div>
</#macro>