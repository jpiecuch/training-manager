<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
    <!--<![endif]-->
    <head>
        <meta charset="utf-8"/>
        <title>Metronic | Admin Dashboard Template</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <meta content="" name="description"/>
        <meta content="" name="author"/>
        <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/select2/select2-metronic.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style-metronic.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style-responsive.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css" id="style_color"/>
        <link href="<c:url value="/resources/assets/css/pages/login.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
    </head>
    <body class="login">
        <div class="logo"><a href="index.html"></a></div>
        <div class="content">
            <form class="login-form" method="POST" action="<c:url value="/j_spring_security_check"/>">
                <h3 class="form-title">Login to your account</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span>Enter any username and password.</span>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <div class="input-icon"><i class="fa fa-user"></i><input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="j_username"/></div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <div class="input-icon"><i class="fa fa-lock"></i><input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="j_password"/></div>
                </div>
                <div class="form-actions">
                    <label class="checkbox"><input id="j_remember" name="_spring_security_remember_me" type="checkbox" value="1" /> Remember me </label>
                    <button type="submit" class="btn green pull-right">Login <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
                <div class="login-options">
                    <h4>Or login with</h4>
                    <ul class="social-icons">
                        <li><a class="facebook" data-original-title="facebook" href="#"></a></li>
                        <li><a class="twitter" data-original-title="Twitter" href="#"></a></li>
                        <li><a class="googleplus" data-original-title="Goole Plus" href="#"></a></li>
                        <li><a class="linkedin" data-original-title="Linkedin" href="#"></a></li>
                    </ul>
                </div>
                <div class="forget-password">
                    <h4>Forgot your password ?</h4>
                    <p>no worries, click <a href="javascript:;" id="forget-password">here</a> to reset your password.</p>
                </div>
                <div class="create-account"><p>Don't have an account yet ?&nbsp;<a href="javascript:;" id="register-btn">Create an account</a></p></div>
            </form>
            <form class="forget-form" action="index.html" method="post">
                <h3>Forget Password ?</h3>
                <p>Enter your e-mail address below to reset your password.</p>
                <div class="form-group">
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email"/>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" id="back-btn" class="btn"><i class="m-icon-swapleft"></i> Back </button>
                    <button type="submit" class="btn green pull-right">Submit <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
            </form>
            <form class="register-form" action="index.html" method="post">
                <h3>Sign Up</h3>
                <p>Enter your personal details below:</p>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Full Name</label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="Full Name" name="fullname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Email</label>
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="Email" name="email"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Address</label>
                    <div class="input-icon">
                        <i class="fa fa-check"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="Address" name="address"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">City/Town</label>
                    <div class="input-icon">
                        <i class="fa fa-location-arrow"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="City/Town" name="city"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Country</label>
                    <select name="country" id="select2_sample4" class="select2 form-control">
                        <option value=""></option>                       
                        <option value="PL">Poland</option>
                    </select>
                </div>
                <p>Enter your account details below:</p>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="Password" name="password"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Re-type Your Password</label>
                    <div class="controls">
                        <div class="input-icon">
                            <i class="fa fa-check"></i>
                            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Re-type Your Password" name="rpassword"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label><input type="checkbox" name="tnc"/> I agree to the <a href="#"> Terms of Service</a> and <a href="#"> Privacy Policy </a></label>
                    <div id="register_tnc_error"></div>
                </div>
                <div class="form-actions">
                    <button id="register-back-btn" type="button" class="btn"><i class="m-icon-swapleft"></i> Back </button>
                    <button type="submit" id="register-submit-btn" class="btn green pull-right"> Sign Up <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
            </form>
        </div>
        <div class="copyright">
            2014 &copy; Metronic. Admin Dashboard Template.
        </div>
        <!--[if lt IE 9]>
                <script src="assets/plugins/respond.min.js"></script>
                <script src="assets/plugins/excanvas.min.js"></script> 
                <![endif]-->
        <script src="<c:url value="/resources/assets/plugins/jquery-1.10.2.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery.cokie.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/uniform/jquery.uniform.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery-validation/dist/jquery.validate.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/select2/select2.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/scripts/core/app.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/scripts/custom/login.js"/>" type="text/javascript"></script>
        <script>
            jQuery(document).ready(function() {
                App.init();
                Login.init();
            });
        </script>
    </body>
</html>