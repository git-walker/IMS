<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${fns:getConfig('productName')}</title>

	<meta http-equiv="progma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
	    var ctx = '${ctx}';
	</script>
</head>
<body class="no-skin">
	<div class="navbar navbar-default navbar-collapse" id="navbar">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons,.navbar-menu">
				<span class="sr-only">Toggle user menu</span>
				<c:if test="${empty fns:getUser().photo}">
					<img id="navbarToggleImg" src="${ctxStatic}/assets/avatars/user.jpg" alt="${fns:getUser().name}" >
				</c:if>
				<c:if test="${not empty fns:getUser().photo}">
			    	<img id="navbarToggleImg"  src="${ctxUploads}/userAvatar/${fns:getUser().id}_large${fns:getUser().photo}" alt="${fns:getUser().name}"onerror="this.src='${ctxStatic}/assets/avatars/user.jpg'">
			    </c:if>
			<!-- 	<img src="${ctxStatic}/assets/avatars/user.jpg" alt="${fns:getUser().name}" />   -->
			</button>

			<div class="navbar-header pull-left"><div class="brand"><img alt="" src="${pageContext.request.contextPath}/static/images/logo.png"/></div></div>

			<div class="navbar-buttons navbar-header pull-right collapse navbar-collapse " role="navigation">
					<ul class="nav ace-nav">
						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue user-min">
							<a data-toggle="dropdown" href="javascript:;" class="">
								<c:if test="${empty fns:getUser().photo}">
									<img class="nav-user-photo" src="${ctxStatic}/assets/avatars/user.jpg" alt="${fns:getUser().name}" >
								</c:if>
								<c:if test="${not empty fns:getUser().photo}">
							    	<img class="nav-user-photo" src="${ctxUploads}/userAvatar/${fns:getUser().id}_large${fns:getUser().photo}" alt="${fns:getUser().name}"onerror="this.src='${ctxStatic}/assets/avatars/user.jpg'">
							    </c:if>
								<span class="user-info">
									<small>Welcome,</small>
									${fns:getUser().name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#page/sys/user/info">
										<i class="ace-icon fa fa-user"></i>
										个人信息
									</a>
								</li>
								<li>
									<a href="#page/sys/user/modifyPwd">
										<i class="ace-icon fa fa-lock"></i>
										修改密码
									</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="${ctx}/logout" title="退出登录">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>


						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

			<nav role="navigation" class="navbar-menu pull-right collapse navbar-collapse">
					<!-- #section:basics/navbar.nav -->
					<ul id="menu" class="nav navbar-nav">
					<c:set var="menuList" value="${fns:getTreeMenuList(false)}"/>
					<c:set var="firstMenu" value="true"/>
					<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
						<c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
							<li class="${not empty firstMenu && firstMenu ? 'active' : ''}">
								<c:if test="${empty menu.href}">
									<a class="menu" href="javascript:;" data-href="${ctx}/sys/menu/treeData4ajax?parentId=${menu.id}" data-id="${menu.id}">
									<i class="ace-icon fa ${not empty menu.icon ? menu.icon : 'fa-envelope'}"></i>&nbsp;${menu.name}</a>
								</c:if>
								<c:if test="${not empty menu.href}">
									<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame">${menu.name}</a>
								</c:if>
							</li>
							<c:if test="${firstMenu}">
								<c:set var="firstMenuId" value="${menu.id}"/>
							</c:if>
							<c:set var="firstMenu" value="false"/>
						</c:if>
					</c:forEach>
					</ul>

					<!-- /section:basics/navbar.nav -->
				</nav>
		</div><!-- /.container -->
	</div>



	<div class="main-container" id="main-container">

		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>

		<div class="sidebar responsive" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
			<!--<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="ace-icon fa fa-signal"></i>
					</button>

					<button class="btn btn-info">
						<i class="ace-icon fa fa-pencil"></i>
					</button>-->

					<!-- #section:basics/sidebar.layout.shortcuts -->
					<!--<button class="btn btn-warning">
						<i class="ace-icon fa fa-users"></i>
					</button>

					<button class="btn btn-danger">
						<i class="ace-icon fa fa-cogs"></i>
					</button>-->

					<!-- /section:basics/sidebar.layout.shortcuts -->
				<!-- </div>-->

			<!--	<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span>

					<span class="btn btn-info"></span>

					<span class="btn btn-warning"></span>

					<span class="btn btn-danger"></span>
				</div>
			</div>--> <!-- /.sidebar-shortcuts -->
			<ul class="nav nav-list" id="sidebar-container">
				<c:forEach items="${menuList}" var="parent" varStatus="idxStatus">
					<c:if test="${parent.parent.id eq '1'&&  parent.isShow eq '1'}">
							<c:forEach items="${parent.subMenu}" var="child" varStatus="childStatus">
							<c:if test="${child.isShow eq '1'}">
							<li data-id="${child.parent.id}" style="display:none;">
								<c:choose>
								<c:when test="${fn:length(child.subMenu) eq 0}">
								<a id="menuitem_${child.id}" data-url="${fn:indexOf(child.href, '://') eq -1 ? 'page' : ''}${not empty child.href ? child.href : '/404'}"
								href="${fn:indexOf(child.href, '://') eq -1 ?'#page' : ''}${not empty child.href ? child.href : '/404'}" target="${child.target}">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
								<span class="menu-text">${child.name}</span>
								</a>
								<b class="arrow"></b>
								</c:when>
								<c:otherwise>
								<a id="menuitem_${child.id}" href="#" class="dropdown-toggle">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
								<span class="menu-text">${child.name}</span>
								<b class="arrow fa fa-angle-down"></b></a>
								<b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="${child.subMenu}" var="subchild" varStatus="subchildStatus">
									<c:if test="${subchild.isShow eq '1'}">
									<li>
										<a id="menuitem_${subchild.id}" data-url="${fn:indexOf(subchild.href, '://') eq -1 ? 'page' : ''}${not empty subchild.href ? subchild.href : '/404'}"
										href="${fn:indexOf(subchild.href, '://') eq -1 ? '#page' : ''}${not empty subchild.href ? subchild.href : '/404'}" target="${subchild.target}">
										<i class="menu-icon fa ${not empty subchild.icon ? subchild.icon : ''}"></i>
										${subchild.name}
										</a>
										<b class="arrow"></b>
									</li>
									</c:if>
									</c:forEach>
								</ul>
								</c:otherwise>
								</c:choose>
							</li>
							</c:if>
							</c:forEach>
					</c:if>
				</c:forEach>
		    </ul>

			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse" data-target="#sidebar">
	   			<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
			<!-- /section:basics/sidebar.layout.minimize -->
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'collapsed')
				} catch (e) {
				}
			</script>
		</div><!-- /.sidebar -->

		<div class="main-content">
			<div class="main-content-inner">
			    <div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
					<ul class="breadcrumb">
						<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<span>首页</span>
						</li>
					</ul>

					<!--<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon">
								<input placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" type="text">
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</form>
					</div>-->
				</div>

			    <div class="page-content l2m-no-header no-padding-bottom">
			    <!-- #section:settings.box -->
						<div class="ace-settings-container" id="ace-settings-container" style="z-index: 999">
							<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
								<i class="ace-icon fa fa-cog bigger-130"></i>
							</div>

							<div class="ace-settings-box clearfix" id="ace-settings-box">
								<div class="pull-left width-50">
									<!-- #section:settings.skins -->
									<div class="ace-settings-item">
										<div class="pull-left">
											<select id="skin-colorpicker" class="hide">
												<option data-skin="no-skin" value="#438EB9">#438EB9</option>
												<option data-skin="skin-1" value="#222A2D">#222A2D</option>
												<option data-skin="skin-2" value="#C6487E">#C6487E</option>
												<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
											</select>
										</div>
										<span>&nbsp; 主题选择</span>
									</div>

									<!-- /section:settings.skins -->

									<!-- #section:settings.navbar -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
										<label class="lbl" for="ace-settings-navbar"> 固定顶部导航</label>
									</div>

									<!-- /section:settings.navbar -->

									<!-- #section:settings.sidebar -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
										<label class="lbl" for="ace-settings-sidebar"> 固定左侧菜单</label>
									</div>

									<!-- /section:settings.sidebar -->

									<!-- #section:settings.breadcrumbs -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
										<label class="lbl" for="ace-settings-breadcrumbs"> 固定面包屑</label>
									</div>

									<!-- /section:settings.breadcrumbs -->

								</div><!-- /.pull-left -->

								<div class="pull-left width-50">
									<!-- #section:basics/sidebar.options -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
										<label class="lbl" for="ace-settings-hover"> 子菜单浮层显示</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
										<label class="lbl" for="ace-settings-compact"> 窄左侧菜单</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
										<label class="lbl" for="ace-settings-highlight"> 切换选中效果</label>
									</div>

									<!-- #section:settings.container -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
										<label class="lbl" for="ace-settings-add-container">
											切换窄屏
										</label>
									</div>

									<!-- /section:settings.container -->
									<!-- /section:basics/sidebar.options -->
								</div><!-- /.pull-left -->
							</div><!-- /.ace-settings-box -->
						</div><!-- /.ace-settings-container -->
						<div id="meetingQrCode" style="width:0px;">
							<div id="toggle" onclick="toggleQr()"><i class="ace-icon fa fa-pencil-square-o bigger-130 white"></i></div>
							<div id="qrContent">
								<div id="meetingTitle" style="text-align: center;"></div>
								<div id="qrcode"></div>
							</div>
						</div>
						<!-- /section:settings.box -->
						<div class="page-content-area" data-ajax-content="true">
							<!-- ajax content goes here -->
						</div><!-- /.page-content-area -->

			      <!-- <div class="row">
			        <div class="col-xs-12">
			          <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="600"></iframe>
			        </div>
			      </div> -->

			    </div><!-- /.page-content -->
		  	</div>
	  	</div><!-- /.main-content -->



		<!-- <div class="footer">
		    <div class="footer-inner">
		        <div class="footer-content">
					<span class="bigger-120">
						<span class="blue bolder">IMS</span>
						进销存管理系统 © 2018-2019
					</span>
		        </div>
		    </div>
		</div> -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>

  	<!--[if gte IE 9]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
	<!-- <![endif]-->

	<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
	<!-- <![endif]-->

	<!--[if lt IE 9]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
	<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${ctxStatic}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="${ctxStatic}/assets/js/bootstrap.js"></script>

		<!--[if lte IE 8]>
		  <script src="${ctxStatic}/assets/js/excanvas.js"></script>
		<![endif]-->


		<script src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js"></script>
		<script src="${ctxStatic}/assets/js/jqGrid/jquery.jqGrid.js"></script>
		<script src="${ctxStatic}/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>


<script src="${ctxStatic}/assets/js/select2.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/assets/js/bootstrapValidator.js"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<!-- <link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" /> -->
<script src="${ctxStatic}/common/jeesite.min.js" type="text/javascript"></script>
<%-- <script src="${ctxStatic}/modules/sys/js/plug-in.js" type="text/javascript"></script> --%>
	<!-- ace scripts -->
	<script src="${ctxStatic}/assets/js/ace/elements.scroller.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.colorpicker.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.fileinput.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.typeahead.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.wysiwyg.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.spinner.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.treeview.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.wizard.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.aside.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.ajax-content.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.touch-drag.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.submenu-hover.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.widget-box.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings-rtl.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings-skin.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.widget-on-reload.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.searchbox-autocomplete.js"></script>
	<script src="${ctxStatic}/layui-2.4.5/layui.js"></script>
	<script src="${ctxStatic}/assets/js/jquery-ui.custom.js"></script>
	<script src="${ctxStatic}/assets/js/jquery-ui.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.ui.touch-punch.js"></script>
	<script src="${ctxStatic}/assets/js/chosen.jquery.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.autosize.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.inputlimiter.1.3.1.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.maskedinput.js"></script>
	<script src="${ctxStatic}/assets/js/bootstrap-tag.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.easypiechart.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.sparkline.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.pie.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.resize.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.gritter.js"></script>
	<script src="${ctxStatic}/assets/js/bootbox.js"></script>
	<%-- <script src="${ctxStatic}/assets/js/ajaxfileupload.js"></script> --%>

	<script type="text/javascript">
		$(document).ready(function() {

			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li").removeClass("active");
				$(this).parent().addClass("active");
				var id = $(this).attr('data-id');
				if($("div#sidebar ul.nav li[data-id='"+id+"']").length !=0){
					$("div#sidebar ul.nav li[data-id]").hide();
					$("div#sidebar ul.nav li[data-id='"+id+"']").show();
				}
			});

		});


	</script>
</body>
</html>