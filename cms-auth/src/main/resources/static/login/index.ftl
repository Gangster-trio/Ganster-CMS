<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>CMS manage</title>    <script
        src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/layui/css/layui.css">

    <script src="/layui/layui.all.js" type="text/javascript"></script>
    <script src="/util/util.js"></script>
</head>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"></div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="" id="userinfo">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    username
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="/logout">退了</a></li>
        </ul>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
    <#list treelist as tree>
        <li class="layui-nav-item layui-nav-itemed">
            <a href="javascript:;">${tree.module.moduleName}</a>
            <dl class="layui-nav-child">
                    <#list tree.list as module>
                        <dd><a onclick="showAtRight('/fragement${module.moduleUrl}')">${module.moduleName}</a></dd>
                    </#list>
            </dl>
        </li>
    </#list>
            </ul>
        </div>
    </div>
    <div class="layui-body" id="content">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;text-align: center;">欢迎使用Ganster-CMS后台管理</div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © Gangster-trio CMS.
    </div>
</div>
<!--<script src="/layui/"></script>-->
</body>
</html>

<script type="text/javascript">
    var current_column_id = -1;
</script>

