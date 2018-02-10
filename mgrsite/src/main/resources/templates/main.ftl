<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>jQuery</title>
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.3.4/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.3.4/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-easyui-1.3.4/jquery.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>

	<script type="text/javascript">
		$(function () {
			//重复选择器提取
			var indexTabs = $('#index_tabs');
            var commonVal =  function (node) {
				console.log(node);    //id: 2, text: "员工管理" ,加载的是一个node节点对象
				//如果 这个节点的属性缺失
				if (!node.attributes || !node.attributes.url) {       //employee.html  如果这两个属性没有的话也就是说建立tabs会url等异常.
					alert("这个节点有问题");
					return;
				}

				//如果存在该选项卡面板时,选中
				var exist = indexTabs.tabs('exists', node.text);  //通过选项卡标题判断
				if (exist) {
					//选中
					indexTabs.tabs('select', node.text);   //通过选项卡标题选中
					return;
				}

				//如果不存在就创建选项卡面板
				indexTabs.tabs('add', {
					title: node.text,
					fit: true,
					fitColumns: true,
					selected: true,
					closable: true,
					href: node.attributes.url            //node对象很重要
				})
			};
			//----------------------------------------------------

			//创建选项卡
			$('#index_tree').tree({
				url: '/loadMenuByParentId?title='+document.getElementById("index_menu").getAttribute("title"),     //的节点数据(检索远程数据的URL地址)
				fit: true,
				fitColumns: true,          //列饱满撑开
				//点击一个节点触发(去加载一个选项卡)
				onClick:commonVal
			});
		});

	</script>
</head>
<body>
<!--layout easyui方式[布局]-->
<div id="index_layout" class="easyui-layout" data-options="fit:true">
	<!--标题部分-->
	<div data-options="region:'north'" style="height:70px;background: #5bc0de;color: white">
		<div><img src="#"></div>
		<div><span>aaa</span></div>
	</div>

	<!--菜单栏 accordion+彩单数组成,也就是需要什么直接往父容器中嵌入就可以了. -->
	<div data-options="region:'west'" style="width:200px;">
		<!--accordion组件-->
		<div id="index_accordion" class="easyui-accordion" data-options="fit:true">
			<!--菜单列表组件-->
			<div id="index_menu" title="用户管理" data-options="iconCls:'icon-ok'">
				<!--菜单树 最好用js的方式,如果默认的的easyui的话,样式都是固定的,格式不好拓展-->
				<ul id="index_tree" style="padding: 10px"></ul>
			</div>

			<div id="index_menu1" title="安全管理" data-options="iconCls:'icon-ok'">
				<!--菜单树 最好用js的方式,如果默认的的easyui的话,样式都是固定的,格式不好拓展-->
				<ul id="index_tree1" style="padding: 10px"></ul>
			</div>

			<div id="index_menu2" title="审核项目" data-options="iconCls:'icon-ok'">
				<!--菜单树 最好用js的方式,如果默认的的easyui的话,样式都是固定的,格式不好拓展-->
				<ul id="index_tree2" style="padding: 10px"></ul>
			</div>

			<div id="index_menu3" title="平台管理" data-options="iconCls:'icon-ok'">
				<!--菜单树 最好用js的方式,如果默认的的easyui的话,样式都是固定的,格式不好拓展-->
				<ul id="index_tree3" style="padding: 10px"></ul>
			</div>
		</div>
	</div>

	<!--选择卡和表格数据显示区域-->
	<div data-options="region:'center'"> <!--region:是什么属性?-->
		<div id="index_tabs" class="easyui-tabs" data-options="fit:true">    <!--选项卡大小铺满父容器-->
		</div>
	</div>
</div>
</body>
</html>