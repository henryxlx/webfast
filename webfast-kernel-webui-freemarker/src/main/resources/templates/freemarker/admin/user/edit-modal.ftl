<#assign modalSize = 'large'/>

<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>编辑用户信息</#macro>

<#macro blockBody>

<form id="user-edit-form" class="form-horizontal" method="post" action="${ctx}/admin/user/${user.id}/edit">

  <div class="row form-group">
    <div class="col-md-2 control-label">
      <label for="truename">姓名</label>
    </div>
    <div class="col-md-7 controls">
      <input type="text" id="truename" name="truename" class="form-control" value="${profile.truename!}" >
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-2 control-label">
      <label for="gender">性别</label>
    </div>
    <div class="col-md-7 controls radios">
      <div id="gender">
        <input type="radio" id="gender_0" name="gender" value="male"
        <#if profile.gender?? && profile.gender == 'male'> checked="checked" </#if>>
        <label for="gender_0">男</label>

        <input type="radio" id="gender_1" name="gender" value="female"
        <#if profile.gender?? && profile.gender == 'female'> checked="checked" </#if>>
        <label for="gender_1">女</label>
      </div>

    </div>
  </div>

    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="idcard">身份证号</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="idcard" name="idcard" class="form-control" value="${profile.idcard!}">
     </div>
    </div>

    <#if user.verifiedMobile != ''>
      <div class="row form-group">
        <div class="col-md-2 control-label"> 
          <label for="mobile">手机号码</label>
        </div>
        <div class="col-md-7 controls">
          <div class="control-text"> ${user.verifiedMobile!}<span class="text-success">(已绑定)</span> </div>
       </div>
      </div>
    <#else>
      <div class="row form-group">
        <div class="col-md-2 control-label"> 
          <label for="mobile">手机号码</label>
        </div>
        <div class="col-md-7 controls">
          <input type="text" id="mobile" name="mobile" class="form-control" value="${profile.mobile!}">
       </div>
      </div>
    </#if>

    <div class="row form-group">
        <div class="col-md-2 control-label"> 
          <label for="company">公司</label>
        </div>
        <div class="col-md-7 controls">
          <input type="text" id="company" name="company" class="form-control" value="${profile.company!}">
       </div>
    </div>
    
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="job">职业</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="job" name="job" class="form-control" value="${profile.job!}">
     </div>
    </div>

    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="title">头衔</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="title" name="title" class="form-control" value="${profile.title!}">
     </div>
    </div>
    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="signature">个人签名</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="signature" name="signature" class="form-control" value="${profile.signature!}">
     </div>
    </div>
    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="about">自我介绍</label>
      </div>
      <div class="col-md-7 controls">
        <textarea id="aboutme" name="aboutme" data-image-upload-url="${ctx}/editor/upload?token=upload_token('default')">${profile.aboutme!}</textarea>
      </div>
    </div>
    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="site">个人主页</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="site" name="site" class="form-control" value="${profile.site!}">
     </div>
    </div>
    <p></p>
  <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="weibo">微博</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="weibo" name="weibo" class="form-control" value="${profile.weibo!}">
     </div>
    </div>
    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="qq">QQ</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="qq" name="qq" class="form-control" value="${profile.qq!}">
     </div>
    </div>
    <p></p>
    <div class="row form-group">
      <div class="col-md-2 control-label"> 
        <label for="weixin">微信</label>
      </div>
      <div class="col-md-7 controls">
        <input type="text" id="weixin" name="weixin" class="form-control" value="${profile.weixin!}">
     </div>
    </div> 

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

    <#if fields??>
          <hr>
          {% for field in fields %}
          {% if field.type=="text" %}
          <div class="form-group">
            <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
            <div class="col-md-7 controls">
              <textarea id="{{field.fieldName}}" name="{{field.fieldName}}" class="{{field.type}} form-control" >{{ profile[field.fieldName]|default('') }}</textarea>
              <div class="help-block" style="display:none;"></div>
            </div>
          </div>
          {% elseif field.type=="int" %}
          <div class="form-group">
            <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
            <div class="col-md-7 controls">
              <input type="text" id="{{field.fieldName}}" placeholder="最大值为9位整数" name="{{field.fieldName}}" class="{{field.type}} form-control"  value="{{ profile[field.fieldName]|default('') }}">
              <div class="help-block" style="display:none;"></div>
            </div>
          </div>
          {% elseif field.type=="float" %}
          <div class="form-group">
            <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
            <div class="col-md-7 controls">
              <input type="text" id="{{field.fieldName}}" placeholder="保留到2位小数" name="{{field.fieldName}}" class="{{field.type}} form-control" value="{{ profile[field.fieldName]|default('') }}">
              <div class="help-block" style="display:none;"></div>
            </div>
          </div>
          {% elseif field.type=="date" %}
          <div class="form-group">
            <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
            <div class="col-md-7 controls">
              <input type="text" id="{{field.fieldName}}" name="{{field.fieldName}}" class="{{field.type}} form-control"   value="{% if profile[field.fieldName] %}{{profile[field.fieldName]}}{% endif %}">
              <div class="help-block" style="display:none;"></div>
            </div>
          </div>
          {% elseif field.type=="varchar" %}
          <div class="form-group">
            <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
            <div class="col-md-7 controls">
              <input type="text" id="{{field.fieldName}}" name="{{field.fieldName}}" class="form-control"  value="{{ profile[field.fieldName]|default('') }}">
              <div class="help-block" style="display:none;"></div>
            </div>
          </div>
          {% endif %}
          {% endfor %}
    </#if>

</form>
</#macro>

<#macro blockFooter>
  <button id="edit-user-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary pull-right" data-toggle="form-submit" data-target="#user-edit-form">保存</button>
  <button type="button" class="btn btn-link pull-right" data-dismiss="modal">取消</button>
  <script>app.load('user/edit-modal')</script>
</#macro>