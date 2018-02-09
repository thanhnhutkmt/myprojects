<%@ page pageEncoding="utf-8"%>
	<%
		// Create cookies for first and last names.      
	   	//Cookie signedin_user = new Cookie("username", "abc@gmail.com");
	   	//Cookie admincome = new Cookie("username", "admin");
	   
	   	// Set expiry date after 24 Hrs for both the cookies.
	   	//signedin_user.setMaxAge(60*60*24); 
	   	//admincome.setMaxAge(60*60*24); 
	   
	   	// Add both the cookies in the response header.
	   	//response.addCookie( signedin_user );
	   	//response.addCookie( admincome );			
	
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("mra", "mr-0");
		httpSession.setAttribute("quanly", "");
    	httpSession.setAttribute("mrsr", "");
       	httpSession.setAttribute("user", "Đăng ký/đăng nhập");
		httpSession.setAttribute("user_menuitem1", "Đăng ký"); httpSession.setAttribute("user_menuitem1link", "register.jsp");
		httpSession.setAttribute("user_menuitem2", "Đăng nhập"); httpSession.setAttribute("user_menuitem2link", "signin.jsp");		
		httpSession.setAttribute("mi3v", "visibility: hidden");
		httpSession.setAttribute("mi4v", "visibility: hidden");
		
        if( cookies != null ) {
           for (int i = 0; i < cookies.length; i++) {
        	   System.out.println("have : " + cookies.length + " cookie");
              	cookie = cookies[i];
              	if (cookies[i].getName().equals("username")) {
              		System.out.println("Got username");
              		if (cookies[i].getValue().equals("admin")) {
              			System.out.println("Welcome admin");
              			httpSession.setAttribute("mra", "");
              			httpSession.setAttribute("quanly", "Quản lý");
              			httpSession.setAttribute("mrsr", "mr-0");
              			httpSession.setAttribute("user", "");       
              			break;
              		} else {
              			System.out.println("Welcome " + cookies[i].getValue());
              			httpSession.setAttribute("mra", "visibility: hidden");
              			httpSession.setAttribute("quanly", "");
		           		httpSession.setAttribute("user", cookies[i].getValue());
		           		httpSession.setAttribute("user_menuitem1", "Hồ sơ"); httpSession.setAttribute("user_menuitem1link", "learnerinfo.controller");
						httpSession.setAttribute("user_menuitem2", "Khóa học đã đăng ký"); httpSession.setAttribute("user_menuitem2link", "learnerregisteredcourse.controller");
						httpSession.setAttribute("user_menuitem3", "Khóa học yêu thích"); httpSession.setAttribute("user_menuitem3link", "listfavcourse.controller");
						httpSession.setAttribute("user_menuitem4", "Đăng xuất"); httpSession.setAttribute("user_menuitem4link", "logout.controller");
						httpSession.setAttribute("mi3v", "");
						httpSession.setAttribute("mi4v", "");
						break;
              		}
              	}						               						            	   
           	}
        } 	      
	%>

	<header class="main-header" id="main-header">
		<!-- Header Top -->
		<div class="header-top">
			<div class="auto-container clearfix">
				<!-- Top Left -->
				<!-- Top Right -->
				<div class="top-right">
					<ul class="clearfix">
						<li><a href=""><span class="fa fa-phone"></span>
								0948697739</a></li>
						<li><a href="mailto:seafec2014@gmail.com"><span
								class="fa fa-envelope-o"></span> seafec2014@gmail.com</a></li>
						<li><a href=""><span class="fa fa-map-marker"></span>thundertrainingcenter.tk</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- Header Top End -->
		<!-- Header Lower -->
		<div class="header-lower">
			<div class="auto-container clearfix">
				<!--Outer Box-->
				<div class="outer-box">
					<!-- Logo -->
					<div class="logo">
						<a href="./"><img src="images/logo.png" alt="Thunder Training"
							style="width: 50%"></a>
					</div>
					<!-- Main Menu -->
					<nav class="main-menu">
						<div class="navbar-header">
							<!-- Toggle Button -->
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
						</div>
						<div class="navbar-collapse collapse clearfix" id="menu">
							<ul class="navigation">
								<li class="current"><a href="index.jsp" class="current"">GIỚI THIỆU</a></li>
								<li class=""><a href="khoahoc.controller">KHÓA HỌC</a></li>													
								<li class="dropdown ${mra}"><a href="">${quanly}</a> 
									<ul>
										<li><a href="courses.controller">Quản lý Khóa học</a></li>
										<li><a href="learners.controller">Quản lý học viên</a></li>
										<li><a href="classes.controller">Quản lý lớp</a></li>
										<li><a href="registries.controller">Quản lý sổ đăng ký</a></li>
										<li><a href="exams.controller">Quản lý thi cử</a></li>
										<li><a href="feedbacks.controller">Quản lý phản hồi</a></li>
										<li><a href="users.controller">Quản lý người dùng</a></li>
										<li><a href="favcourses.controller">Quản lý khóa học yêu thích</a></li>
										<li><a href="sendmessage.controller">Gửi thông báo</a></li>
										<li><a href="logout.controller">Đăng xuất</a></li>
									</ul>
								</li>
																
								<li class="dropdown ${mrsr}"><a href="">${user}</a>
									<ul>								
										<li><a href="${user_menuitem1link}">${user_menuitem1}</a></li>
										<li><a href="${user_menuitem2link}">${user_menuitem2}</a></li>
										<li class="${mi3v}"><a href="${user_menuitem3link}">${user_menuitem3}</a></li>
										<li class="${mi4v}"><a href="${user_menuitem4link}">${user_menuitem4}</a></li>										
									</ul>
								</li>
								<li class=""><a href="lienhe.controller">LIÊN HỆ</a></li>
							</ul>
						</div>
					</nav>
					<!-- Main Menu End-->
				</div>
			</div>
		</div>
		<!-- Header Lower End-->
	</header>
