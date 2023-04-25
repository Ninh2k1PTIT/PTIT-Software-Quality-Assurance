<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

    <head>
        <title>User Management Application</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">
    </head>

    <body>
        <div style="margin: 10% 30%">
            <div class="card">
                <div class="card-body">
                    <form action="login" method="post">
                        <h2 class="d-flex justify-content-center">
                            Đăng nhập
                        </h2>
                        <div class="m-2 text-center text-danger">${error}</div>
                        <fieldset class="form-group">
                            <label>Username</label> 
                            <input type="text" value="${username}" class="form-control" name="username" autocomplete="off" autofocus required="required" oninvalid="this.setCustomValidity('Trường này không được để trống.')" oninput="setCustomValidity('')">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Mật khẩu</label> 
                            <input type="password" value="${password}" class="form-control" name="password" autocomplete="off" required="required" oninvalid="this.setCustomValidity('Trường này không được để trống.')" oninput="setCustomValidity('')">
                        </fieldset>
                        <button type="submit" class="btn btn-info w-100 mb-1">Đăng nhập</button>
                        <a href="/BankManagement/signup.jsp"><button type="button" class="btn btn-light w-100">Đăng ký</button></a>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <% if (session.getAttribute("signupSuccess") != null) {%>
        <script>
            toastr.success("Thành công");
        </script>
        <%
                session.removeAttribute("signupSuccess");
            }%>
        <script>
            if (window.history.replaceState) {
                // Xóa các giá trị lưu trữ trong trình duyệt khi tải lại trang
                window.history.replaceState(null, null, window.location.href);
            }
        </script>
    </body>

</html>