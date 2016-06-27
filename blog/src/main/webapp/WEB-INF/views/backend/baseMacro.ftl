<#--最基本的后台模板宏，定义head 及css，js引入-->
<#compress>
<#macro base baseJs basecss=[]> 
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>Backend-Chu Lung's Blog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon"
	href="${staticsPath}/blog/images/favicon.ico"
	type="image/x-icon" />
    <!-- Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://static.chulung.com/statics/backend/css/backend.css" rel="stylesheet"/>
    <#list basecss as c>
	<link href="${staticsPath}${c}"  rel="stylesheet"/>
	</#list>
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>

				<#nested>
	<script>var staticsPath="${staticsPath}"</script>
	<script>var module="${moduleName}"</script>
	<script data-main='${staticsPath}/backend/js/${baseJs}.js' src="https://cdn.bootcss.com/require.js/2.2.0/require.min.js"></script>
</html>
</#macro> 
</#compress>