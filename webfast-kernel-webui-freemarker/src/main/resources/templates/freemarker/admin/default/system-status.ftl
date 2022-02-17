<table class="table table-condensed table-bordered table-striped table-hover" >
    <tr>
        <td width="15%">系统信息</td>
        <td width="85%">
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            <#list systemMap! as key, value>
                ${value}&nbsp;
            </#list>
        </td>
    </tr>
    <tr>
        <td>Java语言</td>
        <td>
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            <#list javaMap! as key, value>
                ${value}&nbsp;
            </#list>
        </td>
    </tr>
    <tr>
        <td>Java虚拟机</td>
        <td>
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            <#list jvmMap! as key, value>
                ${value}&nbsp;
            </#list>
        </td>
    </tr>
    <tr>
        <td>数据库</td>
        <td>
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            ${dbinfo!}
        </td>
    </tr>
    <tr>
        <td>数据库驱动</td>
        <td>
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            ${driverinfo!}
        </td>
    </tr>
    <tr>
        <td>服务器</td>
        <td>
            <span class="glyphicon glyphicon-info-sign text-info"></span>
            ${serverInfo!} (Servlet版本:${servletVersion!})
        </td>
    </tr>
    <tr>
        <td>内存图示</td>
        <td>
            <table align=left border="0" cellpadding="0" cellspacing="0" width="95%">
                <td valign=top width="80%">
                    <table  border="0" cellpadding="0" cellspacing="0" width="100%">
                        <td bgcolor="#CC3333" width="${percentageUsed}%">
                            <a title="Used Memory" >
                                <img src="${ctx}/images/spacer.gif"
                                     height=15
                                     width="100%"
                                     border=0 title="Used Memory ( ${percentageUsed} %)">
                            </a>
                        </td>

                        <td bgcolor="#00CC00" width="${percentageFree}%">
                            <a title="Free Memory" >
                                <img src="${ctx}/images/spacer.gif"
                                     height=15
                                     width="100%"
                                     border=0 title="Free Memory ( ${percentageFree} %)">
                            </a>
                        </td>
                    </table>
                </td>
                <td valign=top width="10%" align=left nowrap><b>&nbsp;&nbsp;${percentageFree} % 剩余 </b></td>
            </table>
        </td>
    </tr>
</table>