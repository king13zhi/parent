<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/js/bootstrap-3.3.2-dist/css/bootstrap.css" type="text/css" />
	<link rel="stylesheet" href="/css/core.css" type="text/css" />
	<script type="text/javascript" src="/js/jquery/jquery-2.1.3.js"></script>
	<script type="text/javascript" src="/js/bootstrap-3.3.2-dist/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/jquery.bootstrap.min.js"></script>
	<title>Title</title>
</head>
<body>
<tbody id="xx">

</tbody>
    <script>
        $(function () {
            $.ajax({
                url:"/index1",
                success: function (data) {
                    $("#xx").appendTo(data);
					console.log(data);
				}
            })
		})
    </script>

   <#--欢饮 ${username} 进入成人世界.-->
</body>
</html>