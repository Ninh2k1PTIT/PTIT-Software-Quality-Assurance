<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<header>
    <nav class="navbar navbar-expand-md navbar-dark bg-info">
        <div>
            <a href="#" class="navbar-brand"> Ngân hàng </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/passbooks" class="nav-link">Sổ tiết kiệm</a></li>
            <li><a href="<%=request.getContextPath()%>/calculate-interest" class="nav-link">Tính lãi</a></li>
            <li><a href="<%=request.getContextPath()%>/myaccount" class="nav-link">Cá nhân</a></li>
            <li><a href="<%=request.getContextPath()%>/logout" class="nav-link">Đăng xuất</a></li>
        </ul>

    </nav>
</header>
